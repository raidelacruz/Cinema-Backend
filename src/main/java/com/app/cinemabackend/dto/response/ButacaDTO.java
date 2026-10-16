package com.app.cinemabackend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ButacaDTO {

    private Long idButaca;
    private Long idSala;
    private String fila;
    private Integer numero;
    private String tipoButaca;
    private String estadoOperativo;
    private String codigoButaca;

    public ButacaDTO() {
    }

    public ButacaDTO(Long idButaca, Long idSala, String fila, Integer numero, String tipoButaca, String estadoOperativo, String codigoButaca) {
        this.idButaca = idButaca;
        this.idSala = idSala;
        this.fila = fila;
        this.numero = numero;
        this.tipoButaca = tipoButaca;
        this.estadoOperativo = estadoOperativo;
        this.codigoButaca = codigoButaca;
    }

    public static ButacaDTOBuilder builder() {
        return new ButacaDTOBuilder();
    }

    public static class ButacaDTOBuilder {
        private Long idButaca;
        private Long idSala;
        private String fila;
        private Integer numero;
        private String tipoButaca;
        private String estadoOperativo;
        private String codigoButaca;

        public ButacaDTOBuilder idButaca(Long idButaca) {
            this.idButaca = idButaca;
            return this;
        }

        public ButacaDTOBuilder idSala(Long idSala) {
            this.idSala = idSala;
            return this;
        }

        public ButacaDTOBuilder fila(String fila) {
            this.fila = fila;
            return this;
        }

        public ButacaDTOBuilder numero(Integer numero) {
            this.numero = numero;
            return this;
        }

        public ButacaDTOBuilder tipoButaca(String tipoButaca) {
            this.tipoButaca = tipoButaca;
            return this;
        }

        public ButacaDTOBuilder estadoOperativo(String estadoOperativo) {
            this.estadoOperativo = estadoOperativo;
            return this;
        }

        public ButacaDTOBuilder codigoButaca(String codigoButaca) {
            this.codigoButaca = codigoButaca;
            return this;
        }

        public ButacaDTO build() {
            return new ButacaDTO(idButaca, idSala, fila, numero, tipoButaca, estadoOperativo, codigoButaca);
        }
    }
}
