package com.animephix.backend.model;

import com.animephix.backend.model.compositePK.ComentaEpisodioUsuarioId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "comentaEpisodioUsuario")
public class ComentaEpisodioUsuario {
    @EmbeddedId
    private ComentaEpisodioUsuarioId id;

    @Column(nullable = false)
    private boolean habilitado = true;

    @Size(min = 3, max = 500)
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false, insertable=false, updatable=false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "anime_id", referencedColumnName = "anime_id", insertable=false, updatable=false),
            @JoinColumn(name = "num_episodio", referencedColumnName = "num_episodio", insertable=false, updatable=false)
    })
    private Episodio episodio;
}
