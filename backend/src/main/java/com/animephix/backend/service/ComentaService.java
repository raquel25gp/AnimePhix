package com.animephix.backend.service;

import com.animephix.backend.model.Anime;
import com.animephix.backend.model.ComentaEpisodioUsuario;
import com.animephix.backend.model.Usuario;
import com.animephix.backend.model.compositePK.ComentaEpisodioUsuarioId;
import com.animephix.backend.repository.AnimeRepository;
import com.animephix.backend.repository.ComentaEpisodioUsuarioRepository;
import com.animephix.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ComentaService {
    private ComentaEpisodioUsuarioRepository comentaRepository;
    private UsuarioRepository usuarioRepository;
    private AnimeRepository animeRepository;

    @Autowired
    public ComentaService (ComentaEpisodioUsuarioRepository comentaRepository,
                           UsuarioRepository usuarioRepository,
                           AnimeRepository animeRepository) {
        this.comentaRepository = comentaRepository;
        this.usuarioRepository = usuarioRepository;
        this.animeRepository = animeRepository;
    }

    //Operaciones CRUD
    @Transactional
    public ComentaEpisodioUsuario crear (ComentaEpisodioUsuario episodioComentado) {
        return comentaRepository.save(episodioComentado);
    }

    @Transactional(readOnly = true)
    public List<ComentaEpisodioUsuario> listarTodosPorUsuario(Long idUsuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if (usuarioOptional.isPresent()) {
            return comentaRepository.buscarTodosPorUsuario(idUsuario);
        } else {
            throw new RuntimeException("Ese usuario no existe");
        }
    }

    @Transactional(readOnly = true)
    public List<ComentaEpisodioUsuario> listarTodosPorUsuarioYEpisodio(Long idUsuario, Long idAnime, Long numEpisodio) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if (usuarioOptional.isPresent()) {
            Optional<Anime> animeOptional = animeRepository.findById(idAnime);
            if (animeOptional.isPresent()) {
                ComentaEpisodioUsuarioId comentaId = new ComentaEpisodioUsuarioId(idUsuario, idAnime, numEpisodio);
                return comentaRepository.findAllById(comentaId);
            } else {
                throw new RuntimeException("Ese anime no existe");
            }
        } else {
            throw new RuntimeException("Ese usuario no existe");
        }
    }

    @Transactional(readOnly = true)
    public List<ComentaEpisodioUsuario> listarTodosPorEpisodio(Long idAnime, Long numEpisodio) {
        Optional<Anime> animeOptional = animeRepository.findById(idAnime);
        if (animeOptional.isPresent()) {
            return comentaRepository.buscarTodosPorAnimeYEpisodio(idAnime, numEpisodio);
        } else {
            throw new RuntimeException("Ese anime no existe");
        }
    }

    @Transactional
    public ComentaEpisodioUsuario actualizar (ComentaEpisodioUsuario episodioComentado, Long idUsuario, Long idAnime, Long numEpisodio) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if (usuarioOptional.isPresent()) {
            Optional<Anime> animeOptional = animeRepository.findById(idAnime);
            if (animeOptional.isPresent()) {
                ComentaEpisodioUsuarioId comentaId = new ComentaEpisodioUsuarioId(idUsuario, idAnime, numEpisodio);
                Optional<ComentaEpisodioUsuario> comentaOptional = comentaRepository.findById(comentaId);
                if(comentaOptional.isPresent()) {
                    ComentaEpisodioUsuario comentaDB = comentaOptional.get();
                    if (episodioComentado.isHabilitado()) {
                        comentaDB.setHabilitado(true);
                    }
                    if (!"".equalsIgnoreCase(episodioComentado.getComentario())) {
                        comentaDB.setComentario(episodioComentado.getComentario());
                    }
                    comentaRepository.save(comentaDB);
                    return comentaDB;
                } else {
                    throw new RuntimeException("No existe ese episodio en ese anime para ese usuario");
                }
            } else {
                throw new RuntimeException("Ese anime no existe");
            }
        } else {
            throw new RuntimeException("Ese usuario no existe");
        }
    }

    @Transactional
    public String eliminarVistoPorId(Long idUsuario, Long idAnime, Long numEpisodio) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if (usuarioOptional.isPresent()) {
            Optional<Anime> animeOptional = animeRepository.findById(idAnime);
            if (animeOptional.isPresent()) {
                ComentaEpisodioUsuarioId comentaId = new ComentaEpisodioUsuarioId(idUsuario, idAnime, numEpisodio);
                Optional<ComentaEpisodioUsuario> comentaOptional = comentaRepository.findById(comentaId);
                if(comentaOptional.isPresent()) {
                    comentaRepository.deleteById(comentaId);
                    return "Comentario elimnado del episodio " + numEpisodio + " del anime " + animeOptional.get().getNombre();
                } else {
                    return "No existe ese episodio en ese anime para ese usuario";
                }
            } else {
                return "Ese anime no existe";
            }
        } else {
            return "Ese usuario no existe";
        }
    }
}
