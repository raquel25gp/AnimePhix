package com.animephix.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "estado")
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_estado;
    @Column(nullable = false)
    @Size(min = 3, max = 60)
    private String nombre;

    @OneToMany(mappedBy = "estado")
    private Set<Anime> animes;
}
