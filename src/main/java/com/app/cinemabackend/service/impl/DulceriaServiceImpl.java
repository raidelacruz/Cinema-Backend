package com.app.cinemabackend.service.impl;

import com.app.cinemabackend.dto.response.DulceriaDTO;
import com.app.cinemabackend.entity.DulceriaProducto;
import com.app.cinemabackend.repository.DulceriaProductoRepository;
import com.app.cinemabackend.service.DulceriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DulceriaServiceImpl implements DulceriaService {

    private final DulceriaProductoRepository dulceriaProductoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DulceriaDTO> obtenerCatalogo() {
        try {
            List<DulceriaProducto> productos = dulceriaProductoRepository.findByDisponibleTrue();
            if (!productos.isEmpty()) {
                return productos.stream().map(this::mapToDTO).collect(Collectors.toList());
            }
        } catch (Exception ignored) {
            // Fallback si la tabla aun no ha sido creada en la base de datos
        }
        return obtenerCatalogoMock();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DulceriaDTO> obtenerPorCategoria(String categoria) {
        return obtenerCatalogo().stream()
                .filter(d -> d.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }

    private DulceriaDTO mapToDTO(DulceriaProducto p) {
        return DulceriaDTO.builder()
                .idProducto(p.getIdProducto())
                .nombre(p.getNombre())
                .descripcion(p.getDescripcion())
                .precio(p.getPrecio())
                .categoria(p.getCategoria())
                .imagenUrl(p.getImagenUrl())
                .disponible(p.getDisponible())
                .build();
    }

    private List<DulceriaDTO> obtenerCatalogoMock() {
        List<DulceriaDTO> lista = new ArrayList<>();
        lista.add(DulceriaDTO.builder()
                .idProducto(1L)
                .nombre("Combo Pareja Clásico")
                .descripcion("2 Popcorns Grandes salados + 2 Bebidas Grandes 32oz + 1 Nachos con Queso")
                .precio(new BigDecimal("38.50"))
                .categoria("COMBOS")
                .imagenUrl("https://images.unsplash.com/photo-1572177812156-58036aae439c?w=500")
                .disponible(true)
                .build());
        lista.add(DulceriaDTO.builder()
                .idProducto(2L)
                .nombre("Combo Individual Fiesta")
                .descripcion("1 Popcorn Mediano dulce + 1 Bebida Mediana + 1 Chocolate M&M")
                .precio(new BigDecimal("22.00"))
                .categoria("COMBOS")
                .imagenUrl("https://images.unsplash.com/photo-1505686994434-e3cc5abf1330?w=500")
                .disponible(true)
                .build());
        lista.add(DulceriaDTO.builder()
                .idProducto(3L)
                .nombre("Popcorn Gigante Mantequilla")
                .descripcion("Canchita recién hecha bañada con deliciosa mantequilla")
                .precio(new BigDecimal("16.00"))
                .categoria("PALOMITAS")
                .imagenUrl("https://images.unsplash.com/photo-1585647347483-22b66260dfff?w=500")
                .disponible(true)
                .build());
        lista.add(DulceriaDTO.builder()
                .idProducto(4L)
                .nombre("Gaseosa Grande 32oz")
                .descripcion("Coca Cola, Inca Kola o Sprite helada a elección")
                .precio(new BigDecimal("11.50"))
                .categoria("BEBIDAS")
                .imagenUrl("https://images.unsplash.com/photo-1622483767028-3f66f32aef97?w=500")
                .disponible(true)
                .build());
        lista.add(DulceriaDTO.builder()
                .idProducto(5L)
                .nombre("Nachos Especiales con Queso y Jalapeño")
                .descripcion("Totopos crujientes de maíz con queso cheddar caliente")
                .precio(new BigDecimal("14.00"))
                .categoria("SNACKS")
                .imagenUrl("https://images.unsplash.com/photo-1513456852971-30c0b8199d4d?w=500")
                .disponible(true)
                .build());
        return lista;
    }
}
