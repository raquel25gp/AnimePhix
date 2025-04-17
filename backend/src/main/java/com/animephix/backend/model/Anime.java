package com.animephix.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "anime")
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_anime;
    @Column(nullable = false)
    @NotBlank(message = "El nombre debe estar cumplimentado")
    @Size(min = 3, max = 60)
    private String nombre;
    @Column(nullable = false)
    @Size(min = 3, max = 5000)
    private String descripcion;
    @Column(nullable = false)
    private LocalDate fechaInicio;
    @Column(nullable = false)
    private LocalDate fechaFin;
    @Column(nullable = false)
    @Min(0)
    @Max(6)
    private int diaSemana;
    @Column(nullable = false)
    private boolean visible = false;
    @Size(min = 3, max = 200)
    private String urlImagen;

    @ManyToOne
    @JoinColumn(name = "genero_id", referencedColumnName = "id_genero")
    private Genero genero;

    @ManyToOne
    @JoinColumn(name = "estado_id", referencedColumnName = "id_estado")
    private Estado estado;
}
