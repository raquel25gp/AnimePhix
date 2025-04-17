package com.animephix.backend.model.compositePK;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class VistoEpisodioUsuarioId {
    private Long usuario_id;
    private Long anime_id;
    private Long num_episodio;
}
