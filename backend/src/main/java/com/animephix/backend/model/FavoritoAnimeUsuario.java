package com.animephix.backend.model;

import com.animephix.backend.model.compositePK.FavoritoAnimeUsuarioId;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "favoritoAnimeUsuario")
public class FavoritoAnimeUsuario {

    @EmbeddedId
    private FavoritoAnimeUsuarioId id;

    @Column(nullable = false)
    private boolean habilitado = true;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false, insertable=false, updatable=false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "anime_id", nullable = false, insertable=false, updatable=false)
    private Anime anime;

}
