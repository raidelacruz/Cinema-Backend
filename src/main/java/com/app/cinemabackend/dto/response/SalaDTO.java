package com.app.cinemabackend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaDTO {

    private Long idSala;
    private String nombreNumero;
    private String tipoSala;
    private String estado;

    public SalaDTO() {
    }

    public SalaDTO(Long idSala, String nombreNumero, String tipoSala, String estado) {
        this.idSala = idSala;
        this.nombreNumero = nombreNumero;
        this.tipoSala = tipoSala;
        this.estado = estado;
    }

    public static SalaDTOBuilder builder() {
        return new SalaDTOBuilder();
    }

    public static class SalaDTOBuilder {
        private Long idSala;
        private String nombreNumero;
        private String tipoSala;
        private String estado;

        public SalaDTOBuilder idSala(Long idSala) {
            this.idSala = idSala;
            return this;
        }

        public SalaDTOBuilder nombreNumero(String nombreNumero) {
            this.nombreNumero = nombreNumero;
            return this;
        }

        public SalaDTOBuilder tipoSala(String tipoSala) {
            this.tipoSala = tipoSala;
            return this;
        }

        public SalaDTOBuilder estado(String estado) {
            this.estado = estado;
            return this;
        }

        public SalaDTO build() {
            return new SalaDTO(idSala, nombreNumero, tipoSala, estado);
        }
    }
}
