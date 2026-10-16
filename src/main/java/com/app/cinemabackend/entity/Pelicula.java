package com.app.cinemabackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pelicula")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula")
    private Long idPelicula;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "sinopsis", columnDefinition = "TEXT")
    private String sinopsis;

    @Column(name = "duracion_minutos")
    private Integer duracionMinutos;

    @Column(name = "clasificacion")
    private String clasificacion;

    @Column(name = "genero")
    private String genero;

    @Column(name = "poster_url", columnDefinition = "TEXT")
    private String posterUrl;

    @Column(name = "estado")
    private String estado;
}
