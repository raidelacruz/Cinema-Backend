package com.app.cinemabackend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "butaca")
public class Butaca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_butaca")
    private Long idButaca;

    @ManyToOne
    @JoinColumn(name = "id_sala", nullable = false)
    private Sala sala;

    @Column(nullable = false, length = 5)
    private String fila;

    @Column(nullable = false)
    private Integer numero;

    @Column(name = "tipo_butaca", length = 20)
    private String tipoButaca;

    @Column(name = "estado_operativo", length = 20)
    private String estadoOperativo;

    public Butaca() {}


    // getter and setter
    public Long getIdButaca() {
        return idButaca;
    }

    public void setIdButaca(Long idButaca) {
        this.idButaca = idButaca;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getTipoButaca() {
        return tipoButaca;
    }

    public void setTipoButaca(String tipoButaca) {
        this.tipoButaca = tipoButaca;
    }

    public String getEstadoOperativo() {
        return estadoOperativo;
    }

    public void setEstadoOperativo(String estadoOperativo) {
        this.estadoOperativo = estadoOperativo;
    }
    
}
