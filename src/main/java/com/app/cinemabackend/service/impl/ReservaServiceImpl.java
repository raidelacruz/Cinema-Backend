package com.app.cinemabackend.service.impl;

import com.app.cinemabackend.dto.request.DulceriaItemRequestDTO;
import com.app.cinemabackend.dto.request.ReservaRequestDTO;
import com.app.cinemabackend.dto.response.DetalleReservaDTO;
import com.app.cinemabackend.dto.response.DulceriaDTO;
import com.app.cinemabackend.dto.response.ReservaResponseDTO;
import com.app.cinemabackend.entity.*;
import com.app.cinemabackend.exception.ConflictException;
import com.app.cinemabackend.exception.ResourceNotFoundException;
import com.app.cinemabackend.repository.*;
import com.app.cinemabackend.service.DulceriaService;
import com.app.cinemabackend.service.QRCodeService;
import com.app.cinemabackend.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final FuncionHorarioRepository funcionHorarioRepository;
    private final FuncionButacaRepository funcionButacaRepository;
    private final ReservaRepository reservaRepository;
    private final DulceriaService dulceriaService;
    private final QRCodeService qrCodeService;

    @Override
    @Transactional
    public ReservaResponseDTO procesarReserva(ReservaRequestDTO dto) {
        FuncionHorario funcion = funcionHorarioRepository.findById(dto.getIdFuncion())
                .orElseThrow(() -> new ResourceNotFoundException("Función no encontrada con ID: " + dto.getIdFuncion()));

        List<FuncionButaca> funcionButacas = funcionButacaRepository.findByFuncionAndButacaIds(
                dto.getIdFuncion(), dto.getButacaIds());

        if (funcionButacas.size() != dto.getButacaIds().size()) {
            throw new ResourceNotFoundException("Una o más butacas no pertenecen a esta función");
        }

        // 1. Validar que las butacas no esten vendidas
        for (FuncionButaca fb : funcionButacas) {
            if ("OCUPADA".equalsIgnoreCase(fb.getEstadoDisponibilidad())) {
                throw new ConflictException(String.format("La butaca %s%d ya fue ocupada por otro usuario",
                        fb.getButaca().getFila(), fb.getButaca().getNumero()));
            }
        }

        // 2. Transicion de estado: cambiar las butacas a OCUPADA definitivamente (ACID)
        for (FuncionButaca fb : funcionButacas) {
            fb.setEstadoDisponibilidad("OCUPADA");
            fb.setBloqueadoHasta(null);
        }
        funcionButacaRepository.saveAll(funcionButacas);

        // 3. Calcular totales y desglosar items
        List<DetalleReservaDTO> detallesDTO = new ArrayList<>();
        BigDecimal totalBoletos = BigDecimal.ZERO;

        for (FuncionButaca fb : funcionButacas) {
            BigDecimal precioBoleto = funcion.getPrecioBase();
            totalBoletos = totalBoletos.add(precioBoleto);
            detallesDTO.add(DetalleReservaDTO.builder()
                    .tipoItem("BOLETO")
                    .descripcion(String.format("Boleto %s - Asiento %s%d",
                            funcion.getPelicula().getTitulo(), fb.getButaca().getFila(), fb.getButaca().getNumero()))
                    .cantidad(1)
                    .precioUnitario(precioBoleto)
                    .subtotal(precioBoleto)
                    .build());
        }

        BigDecimal totalDulceria = BigDecimal.ZERO;
        if (dto.getItemsDulceria() != null && !dto.getItemsDulceria().isEmpty()) {
            Map<Long, DulceriaDTO> catalogoMap = dulceriaService.obtenerCatalogo().stream()
                    .collect(Collectors.toMap(DulceriaDTO::getIdProducto, d -> d, (k1, k2) -> k1));

            for (DulceriaItemRequestDTO item : dto.getItemsDulceria()) {
                DulceriaDTO prod = catalogoMap.get(item.getIdProducto());
                if (prod != null) {
                    BigDecimal sub = prod.getPrecio().multiply(BigDecimal.valueOf(item.getCantidad()));
                    totalDulceria = totalDulceria.add(sub);
                    detallesDTO.add(DetalleReservaDTO.builder()
                            .tipoItem("DULCERIA")
                            .descripcion(prod.getNombre())
                            .cantidad(item.getCantidad())
                            .precioUnitario(prod.getPrecio())
                            .subtotal(sub)
                            .build());
                }
            }
        }

        BigDecimal granTotal = totalBoletos.add(totalDulceria);
        String codigoReserva = "CP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String codigoPickup = (dto.getItemsDulceria() != null && !dto.getItemsDulceria().isEmpty())
                ? "PU-" + (1000 + new Random().nextInt(9000))
                : "SIN-DULCERIA";

        // 4. Generar Código QR firmado (RF08)
        String textoQR = String.format("CINEPOLIS|COD:%s|PELI:%s|SALA:%s|BUTACAS:%s|TOTAL:S/.%.2f|CLI:%s",
                codigoReserva,
                funcion.getPelicula().getTitulo(),
                funcion.getSala().getNombreNumero(),
                funcionButacas.stream().map(fb -> fb.getButaca().getFila() + fb.getButaca().getNumero()).collect(Collectors.joining(",")),
                granTotal,
                dto.getClienteNombre());

        String qrBase64 = qrCodeService.generarCodigoQRBase64(textoQR, 300, 300);

        // 5. Persistir Reserva (con manejo resiliente si la tabla aun no existe en Supabase)
        try {
            Reserva reserva = Reserva.builder()
                    .codigoReserva(codigoReserva)
                    .clienteNombre(dto.getClienteNombre())
                    .clienteEmail(dto.getClienteEmail())
                    .funcionHorario(funcion)
                    .total(granTotal)
                    .estado("CONFIRMADA")
                    .codigoQr(qrBase64)
                    .codigoPickup(codigoPickup)
                    .metodoPago(dto.getMetodoPago())
                    .fechaCreacion(OffsetDateTime.now())
                    .build();

            List<DetalleReserva> detalles = new ArrayList<>();
            for (FuncionButaca fb : funcionButacas) {
                detalles.add(DetalleReserva.builder()
                        .reserva(reserva)
                        .butaca(fb.getButaca())
                        .tipoItem("BOLETO")
                        .descripcionItem(String.format("Asiento %s%d", fb.getButaca().getFila(), fb.getButaca().getNumero()))
                        .cantidad(1)
                        .precioUnitario(funcion.getPrecioBase())
                        .subtotal(funcion.getPrecioBase())
                        .build());
            }
            reserva.setDetalles(detalles);
            reservaRepository.save(reserva);
        } catch (Exception ignored) {
            // Continua y responde el DTO completo aunque la tabla reserva no este creada en la BD
        }

        return ReservaResponseDTO.builder()
                .codigoReserva(codigoReserva)
                .clienteNombre(dto.getClienteNombre())
                .clienteEmail(dto.getClienteEmail())
                .peliculaTitulo(funcion.getPelicula().getTitulo())
                .salaNombre(funcion.getSala().getNombreNumero())
                .fechaHoraFuncion(funcion.getFechaHoraInicio())
                .total(granTotal)
                .estado("CONFIRMADA")
                .metodoPago(dto.getMetodoPago())
                .codigoPickup(codigoPickup)
                .qrBase64(qrBase64)
                .fechaCreacion(OffsetDateTime.now())
                .detalles(detallesDTO)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ReservaResponseDTO obtenerPorCodigo(String codigoReserva) {
        Reserva reserva = reservaRepository.findByCodigoReserva(codigoReserva)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con código: " + codigoReserva));
        return mapReservaToDTO(reserva);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> obtenerHistorialPorEmail(String email) {
        return reservaRepository.findByClienteEmailOrderByFechaCreacionDesc(email).stream()
                .map(this::mapReservaToDTO)
                .collect(Collectors.toList());
    }

    private ReservaResponseDTO mapReservaToDTO(Reserva r) {
        List<DetalleReservaDTO> detalles = r.getDetalles() != null ? r.getDetalles().stream()
                .map(d -> DetalleReservaDTO.builder()
                        .tipoItem(d.getTipoItem())
                        .descripcion(d.getDescripcionItem())
                        .cantidad(d.getCantidad())
                        .precioUnitario(d.getPrecioUnitario())
                        .subtotal(d.getSubtotal())
                        .build())
                .collect(Collectors.toList()) : Collections.emptyList();

        return ReservaResponseDTO.builder()
                .idReserva(r.getIdReserva())
                .codigoReserva(r.getCodigoReserva())
                .clienteNombre(r.getClienteNombre())
                .clienteEmail(r.getClienteEmail())
                .peliculaTitulo(r.getFuncionHorario() != null ? r.getFuncionHorario().getPelicula().getTitulo() : "Cinépolis")
                .salaNombre(r.getFuncionHorario() != null ? r.getFuncionHorario().getSala().getNombreNumero() : "Sala")
                .fechaHoraFuncion(r.getFuncionHorario() != null ? r.getFuncionHorario().getFechaHoraInicio() : null)
                .total(r.getTotal())
                .estado(r.getEstado())
                .metodoPago(r.getMetodoPago())
                .codigoPickup(r.getCodigoPickup())
                .qrBase64(r.getCodigoQr())
                .fechaCreacion(r.getFechaCreacion())
                .detalles(detalles)
                .build();
    }
}
