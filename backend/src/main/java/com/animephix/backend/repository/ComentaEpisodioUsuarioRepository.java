package com.animephix.backend.repository;

import com.animephix.backend.model.ComentaEpisodioUsuario;
import com.animephix.backend.model.compositePK.ComentaEpisodioUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComentaEpisodioUsuarioRepository extends JpaRepository<ComentaEpisodioUsuario, Long> {

    @Query("Select c FROM ComentaEpisodioUsuario c WHERE c.usuario.id_usuario = ?1")
    List<ComentaEpisodioUsuario> buscarTodosPorUsuario(Long idUsuario);

    List<ComentaEpisodioUsuario> findAllById(ComentaEpisodioUsuarioId comentaId);

    @Query("Select c FROM ComentaEpisodioUsuario c WHERE c.episodio.anime.id_anime = ?1 AND c.episodio.num_episodio = ?2")
    List<ComentaEpisodioUsuario> buscarTodosPorAnimeYEpisodio(Long idAnime, Long numEpisodio);

    Optional<ComentaEpisodioUsuario> findById(ComentaEpisodioUsuarioId comentaId);

    void deleteById(ComentaEpisodioUsuarioId comentaId);
}
