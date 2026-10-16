package com.app.cinemabackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sala")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
