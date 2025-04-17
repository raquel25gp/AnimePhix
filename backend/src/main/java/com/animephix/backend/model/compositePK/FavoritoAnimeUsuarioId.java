package com.animephix.backend.model.compositePK;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FavoritoAnimeUsuarioId {
    private Long usuario_id;
    private Long anime_id;
}
