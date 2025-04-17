package com.animephix.backend.repository;

import com.animephix.backend.model.FavoritoAnimeUsuario;
import com.animephix.backend.model.compositePK.FavoritoAnimeUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritoAnimeUsuarioRepository extends JpaRepository<FavoritoAnimeUsuario, FavoritoAnimeUsuarioId> {
    @Query("Select f FROM FavoritoAnimeUsuario f WHERE f.usuario.id_usuario = ?1")
    List<FavoritoAnimeUsuario> buscarAnimesPorUsuario(Long idUsuario);

    Optional<FavoritoAnimeUsuario> findById(FavoritoAnimeUsuarioId favoritoId);

    void deleteById(FavoritoAnimeUsuarioId favoritoId);
}
