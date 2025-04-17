package com.animephix.backend.service;

import com.animephix.backend.model.FavoritoAnimeUsuario;
import com.animephix.backend.model.Usuario;
import com.animephix.backend.model.compositePK.FavoritoAnimeUsuarioId;
import com.animephix.backend.repository.FavoritoAnimeUsuarioRepository;
import com.animephix.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FavoritoService {
    private FavoritoAnimeUsuarioRepository favoritoAnimeUsuarioRepository;
    private UsuarioRepository usuarioRepository;

    @Autowired
    public FavoritoService (FavoritoAnimeUsuarioRepository favoritoAnimeUsuarioRepository, UsuarioRepository usuarioRepository) {
        this.favoritoAnimeUsuarioRepository = favoritoAnimeUsuarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    //Operaciones CRUD
    @Transactional
    public FavoritoAnimeUsuario crear (FavoritoAnimeUsuario favorito) {
        return favoritoAnimeUsuarioRepository.save(favorito);
    }

    @Transactional(readOnly = true)
    public List<FavoritoAnimeUsuario> listarTodos() {
        return favoritoAnimeUsuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<FavoritoAnimeUsuario> listarPorUsuarioId(Long idUsuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if (usuarioOptional.isPresent()) {
            return favoritoAnimeUsuarioRepository.buscarAnimesPorUsuario(idUsuario);
        } else {
            throw new RuntimeException("Ese usuario no existe");
        }
    }

    @Transactional
    public FavoritoAnimeUsuario actualizar (FavoritoAnimeUsuario favorito, Long idUsuario, Long idAnime) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if (usuarioOptional.isPresent()) {
            FavoritoAnimeUsuarioId favoritoId = new FavoritoAnimeUsuarioId(idUsuario, idAnime);
            Optional<FavoritoAnimeUsuario> favoritoOptional = favoritoAnimeUsuarioRepository.findById(favoritoId);
            if (favoritoOptional.isPresent()) {
                FavoritoAnimeUsuario favoritoDB = favoritoOptional.get();
                if (favorito.isHabilitado()) {
                    favoritoDB.setHabilitado(true);
                }
                favoritoAnimeUsuarioRepository.save(favoritoDB);
                return favoritoDB;
            } else {
                throw new RuntimeException("Este usuario no tiene ese anime como favorito");
            }
        } else {
            throw new RuntimeException("Ese usuario no existe");
        }
    }

    @Transactional
    public String eliminarFavoritoPorId (Long idUsuario, Long idAnime) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if (usuarioOptional.isPresent()) {
            FavoritoAnimeUsuarioId favoritoId = new FavoritoAnimeUsuarioId(idUsuario, idAnime);
            Optional<FavoritoAnimeUsuario> favoritoOptional = favoritoAnimeUsuarioRepository.findById(favoritoId);
            if (favoritoOptional.isPresent()) {
                favoritoAnimeUsuarioRepository.deleteById(favoritoId);
                return "Se ha removido de favoritos el anime " + favoritoOptional.get().getAnime().getNombre();
            } else {
                return "Este usuario no tiene ese anime como favorito";
            }
        } else {
            return "Ese usuario no existe";
        }
    }
}
