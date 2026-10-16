package com.app.cinemabackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "butaca")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Butaca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_butaca")
    private Long idButaca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala", nullable = false)
    private Sala sala;

    @Column(name = "fila", nullable = false)
    private String fila;

    @Column(name = "numero", nullable = false)
    private Integer numero;

    @Column(name = "tipo_butaca")
    private String tipoButaca;

    @Column(name = "estado_operativo")
    private String estadoOperativo;
}
