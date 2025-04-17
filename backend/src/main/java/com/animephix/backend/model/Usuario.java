package com.animephix.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;
    @Column(unique = true, nullable = false)
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 20)
    private String nombre;
    @Email
    private String email;
    @Column(nullable = false)
    @NotBlank(message = "Se debe introducir una contraseña")
    @Size(min = 6, max = 20)
    private String contrasenia;
    private boolean habilitado = true;

    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "id_rol")
    private Rol rol;

    @OneToMany(mappedBy = "usuario")
    private Set<Reporte> reportes;
}
