package com.app.cinemabackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "funcion_horario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuncionHorario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcion")
    private Long idFuncion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pelicula", nullable = false)
    private Pelicula pelicula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala", nullable = false)
    private Sala sala;

    @Column(name = "fecha_hora_inicio", nullable = false)
    private OffsetDateTime fechaHoraInicio;

    @Column(name = "fecha_hora_fin")
    private OffsetDateTime fechaHoraFin;

    @Column(name = "formato")
    private String formato;

    @Column(name = "version")
    private String version;

    @Column(name = "precio_base", precision = 10, scale = 2)
    private BigDecimal precioBase;

    @Column(name = "estado")
    private String estado;
}
