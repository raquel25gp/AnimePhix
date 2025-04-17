package com.animephix.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "genero")
public class Genero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_genero;
    @Column(nullable = false)
    @Size(min = 3, max = 60)
    private String nombre;
    @Column(nullable = false)
    @Size(min = 3, max = 100)
    private String descripcion;

    @OneToMany(mappedBy = "genero")
    private Set<Anime> animes;
}
