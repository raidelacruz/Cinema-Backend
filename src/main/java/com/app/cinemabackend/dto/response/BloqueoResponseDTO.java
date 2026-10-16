package com.app.cinemabackend.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class BloqueoResponseDTO {

    private boolean bloqueadoExitoso;
    private String mensaje;
    private Long idFuncion;
    private List<Long> butacasBloqueadas;
    private OffsetDateTime expiraEn;
    private long segundosRestantes;

    public BloqueoResponseDTO() {
    }

    public BloqueoResponseDTO(boolean bloqueadoExitoso, String mensaje, Long idFuncion, List<Long> butacasBloqueadas, OffsetDateTime expiraEn, long segundosRestantes) {
        this.bloqueadoExitoso = bloqueadoExitoso;
        this.mensaje = mensaje;
        this.idFuncion = idFuncion;
        this.butacasBloqueadas = butacasBloqueadas;
        this.expiraEn = expiraEn;
        this.segundosRestantes = segundosRestantes;
    }

    public static BloqueoResponseDTOBuilder builder() {
        return new BloqueoResponseDTOBuilder();
    }

    public static class BloqueoResponseDTOBuilder {
        private boolean bloqueadoExitoso;
        private String mensaje;
        private Long idFuncion;
        private List<Long> butacasBloqueadas;
        private OffsetDateTime expiraEn;
        private long segundosRestantes;

        public BloqueoResponseDTOBuilder bloqueadoExitoso(boolean bloqueadoExitoso) {
            this.bloqueadoExitoso = bloqueadoExitoso;
            return this;
        }

        public BloqueoResponseDTOBuilder mensaje(String mensaje) {
            this.mensaje = mensaje;
            return this;
        }

        public BloqueoResponseDTOBuilder idFuncion(Long idFuncion) {
            this.idFuncion = idFuncion;
            return this;
        }

        public BloqueoResponseDTOBuilder butacasBloqueadas(List<Long> butacasBloqueadas) {
            this.butacasBloqueadas = butacasBloqueadas;
            return this;
        }

        public BloqueoResponseDTOBuilder expiraEn(OffsetDateTime expiraEn) {
            this.expiraEn = expiraEn;
            return this;
        }

        public BloqueoResponseDTOBuilder segundosRestantes(long segundosRestantes) {
            this.segundosRestantes = segundosRestantes;
            return this;
        }

        public BloqueoResponseDTO build() {
            return new BloqueoResponseDTO(bloqueadoExitoso, mensaje, idFuncion, butacasBloqueadas, expiraEn, segundosRestantes);
        }
    }
}
