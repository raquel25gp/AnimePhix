package com.animephix.backend.repository;

import com.animephix.backend.model.VistoEpisodioUsuario;
import com.animephix.backend.model.compositePK.VistoEpisodioUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VistoEpisodioUsuarioRepository extends JpaRepository<VistoEpisodioUsuario, VistoEpisodioUsuarioId> {
    @Query("Select v FROM VistoEpisodioUsuario v WHERE v.usuario.id_usuario = ?1")
    List<VistoEpisodioUsuario> buscarTodosPorUsuario(Long idUsuario);

    @Query("Select v FROM VistoEpisodioUsuario v WHERE v.usuario.id_usuario = ?1 AND v.episodio.anime.id_anime = ?2")
    List<VistoEpisodioUsuario> buscarTodosPorUsuarioYAnime(Long idUsuario, Long idAnime);

    Optional<VistoEpisodioUsuario> findById(VistoEpisodioUsuarioId vistoId);

    void deleteById(VistoEpisodioUsuarioId vistoId);
}
