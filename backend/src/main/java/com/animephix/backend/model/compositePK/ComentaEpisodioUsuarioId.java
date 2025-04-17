package com.animephix.backend.model.compositePK;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ComentaEpisodioUsuarioId {
    private Long usuario_id;
    private Long anime_id;
    private Long num_episodio;
}
