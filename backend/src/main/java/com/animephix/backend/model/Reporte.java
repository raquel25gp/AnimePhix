package com.animephix.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "reporte")
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_reporte;
    @Column(nullable = false)
    @Size(min = 3, max = 1500)
    private String descripcion;
    private boolean corregido = false;

    @ManyToOne
    @JoinColumn(name = "tipoProblema_id", referencedColumnName = "id_tipoProblema")
    private TipoProblema tipoProblema;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario")
    private Usuario usuario;

}
