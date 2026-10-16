package com.app.cinemabackend.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class FuncionButacaDTO {

    private Long idFuncionButaca;
    private Long idButaca;
    private String fila;
    private Integer numero;
    private String codigoButaca;
    private String tipoButaca;
    private String estadoDisponibilidad;
    private OffsetDateTime bloqueadoHasta;
    private boolean bloqueadaExpirada;

    public FuncionButacaDTO() {
    }

    public FuncionButacaDTO(Long idFuncionButaca, Long idButaca, String fila, Integer numero, String codigoButaca, String tipoButaca, String estadoDisponibilidad, OffsetDateTime bloqueadoHasta, boolean bloqueadaExpirada) {
        this.idFuncionButaca = idFuncionButaca;
        this.idButaca = idButaca;
        this.fila = fila;
        this.numero = numero;
        this.codigoButaca = codigoButaca;
        this.tipoButaca = tipoButaca;
        this.estadoDisponibilidad = estadoDisponibilidad;
        this.bloqueadoHasta = bloqueadoHasta;
        this.bloqueadaExpirada = bloqueadaExpirada;
    }

    public static FuncionButacaDTOBuilder builder() {
        return new FuncionButacaDTOBuilder();
    }

    public static class FuncionButacaDTOBuilder {
        private Long idFuncionButaca;
        private Long idButaca;
        private String fila;
        private Integer numero;
        private String codigoButaca;
        private String tipoButaca;
        private String estadoDisponibilidad;
        private OffsetDateTime bloqueadoHasta;
        private boolean bloqueadaExpirada;

        public FuncionButacaDTOBuilder idFuncionButaca(Long idFuncionButaca) {
            this.idFuncionButaca = idFuncionButaca;
            return this;
        }

        public FuncionButacaDTOBuilder idButaca(Long idButaca) {
            this.idButaca = idButaca;
            return this;
        }

        public FuncionButacaDTOBuilder fila(String fila) {
            this.fila = fila;
            return this;
        }

        public FuncionButacaDTOBuilder numero(Integer numero) {
            this.numero = numero;
            return this;
        }

        public FuncionButacaDTOBuilder codigoButaca(String codigoButaca) {
            this.codigoButaca = codigoButaca;
            return this;
        }

        public FuncionButacaDTOBuilder tipoButaca(String tipoButaca) {
            this.tipoButaca = tipoButaca;
            return this;
        }

        public FuncionButacaDTOBuilder estadoDisponibilidad(String estadoDisponibilidad) {
            this.estadoDisponibilidad = estadoDisponibilidad;
            return this;
        }

        public FuncionButacaDTOBuilder bloqueadoHasta(OffsetDateTime bloqueadoHasta) {
            this.bloqueadoHasta = bloqueadoHasta;
            return this;
        }

        public FuncionButacaDTOBuilder bloqueadaExpirada(boolean bloqueadaExpirada) {
            this.bloqueadaExpirada = bloqueadaExpirada;
            return this;
        }

        public FuncionButacaDTO build() {
            return new FuncionButacaDTO(idFuncionButaca, idButaca, fila, numero, codigoButaca, tipoButaca, estadoDisponibilidad, bloqueadoHasta, bloqueadaExpirada);
        }
    }
}
