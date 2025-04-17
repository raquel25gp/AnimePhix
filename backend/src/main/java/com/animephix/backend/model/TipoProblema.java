package com.animephix.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "tipoProblema")
public class TipoProblema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tipoProblema;
    @Column(nullable = false)
    @Size(min = 3, max = 100)
    private String nombre;

    @OneToMany(mappedBy = "tipoProblema")
    private Set<Reporte> reportes;
}
