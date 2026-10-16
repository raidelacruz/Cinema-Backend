package com.app.cinemabackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sala")
@Getter
@Setter
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sala")
    private Long idSala;

    @Column(name = "nombre_numero", nullable = false)
    private String nombreNumero;

    @Column(name = "tipo_sala")
    private String tipoSala;

    @Column(name = "estado")
    private String estado;

    public Sala() {
    }

    public Sala(Long idSala, String nombreNumero, String tipoSala, String estado) {
        this.idSala = idSala;
        this.nombreNumero = nombreNumero;
        this.tipoSala = tipoSala;
        this.estado = estado;
    }

    public static SalaBuilder builder() {
        return new SalaBuilder();
    }

    public static class SalaBuilder {
        private Long idSala;
        private String nombreNumero;
        private String tipoSala;
        private String estado;

        public SalaBuilder idSala(Long idSala) {
            this.idSala = idSala;
            return this;
        }

        public SalaBuilder nombreNumero(String nombreNumero) {
            this.nombreNumero = nombreNumero;
            return this;
        }

        public SalaBuilder tipoSala(String tipoSala) {
            this.tipoSala = tipoSala;
            return this;
        }

        public SalaBuilder estado(String estado) {
            this.estado = estado;
            return this;
        }

        public Sala build() {
            return new Sala(idSala, nombreNumero, tipoSala, estado);
        }
    }
}
