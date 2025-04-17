package com.animephix.backend.model;

import com.animephix.backend.model.compositePK.VistoEpisodioUsuarioId;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "vistoEpisodioUsuario")
public class VistoEpisodioUsuario {

    @EmbeddedId
    private VistoEpisodioUsuarioId id;

    @Column(nullable = false)
    private boolean habilitado = true;

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
