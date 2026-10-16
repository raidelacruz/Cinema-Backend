package com.app.cinemabackend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "sala")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sala")
    private Long idSala;

    @Column(name = "nombre_numero", length = 50)
    private String nombreNumero;

    @Column(name = "tipo_sala", length = 50)
    private String tipoSala;

    @Column(length = 20)
    private String estado;

    public Sala() {}


    // getter and setter
    public Long getIdSala() {
        return idSala;
    }

    public void setIdSala(Long idSala) {
        this.idSala = idSala;
    }

    public String getNombreNumero() {
        return nombreNumero;
    }

    public void setNombreNumero(String nombreNumero) {
        this.nombreNumero = nombreNumero;
    }

    public String getTipoSala() {
        return tipoSala;
    }

    public void setTipoSala(String tipoSala) {
        this.tipoSala = tipoSala;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
 
}
