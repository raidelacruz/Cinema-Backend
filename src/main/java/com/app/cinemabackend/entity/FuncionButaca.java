package com.app.cinemabackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "funcion_butaca")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuncionButaca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcion_butaca")
    private Long idFuncionButaca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcion", nullable = false)
    private FuncionHorario funcionHorario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_butaca", nullable = false)
    private Butaca butaca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala")
    private Sala sala;

    @Column(name = "estado_disponibilidad", nullable = false)
    private String estadoDisponibilidad;

    @Column(name = "bloqueado_hasta")
    private OffsetDateTime bloqueadoHasta;
}
