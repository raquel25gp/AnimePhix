package com.animephix.backend.model;

import com.animephix.backend.model.compositePK.EpisodioId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@IdClass(EpisodioId.class)
@Table(name = "episodio")
public class Episodio {
    @Id
    @ManyToOne
    @JoinColumn(name = "anime_id", nullable = false)
    private Anime anime;
    @Id
    private Long num_episodio;
    @Size(min = 3, max = 200)
    private String urlVideo;
    private LocalDate fechaLanzamiento;
    @Size(min = 3, max = 200)
    private String urlPoster;
}
