package com.animephix.backend.service;

import com.animephix.backend.model.Anime;
import com.animephix.backend.model.Usuario;
import com.animephix.backend.model.VistoEpisodioUsuario;
import com.animephix.backend.model.compositePK.VistoEpisodioUsuarioId;
import com.animephix.backend.repository.AnimeRepository;
import com.animephix.backend.repository.UsuarioRepository;
import com.animephix.backend.repository.VistoEpisodioUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VistoService {
    private VistoEpisodioUsuarioRepository vistoEpisodioUsuarioRepository;
    private UsuarioRepository usuarioRepository;
    private AnimeRepository animeRepository;

    @Autowired
    public VistoService (VistoEpisodioUsuarioRepository vistoEpisodioUsuarioRepository,
                         UsuarioRepository usuarioRepository,
                         AnimeRepository animeRepository) {
        this.vistoEpisodioUsuarioRepository = vistoEpisodioUsuarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.animeRepository = animeRepository;
    }

    //Operaciones CRUD
    @Transactional
    public VistoEpisodioUsuario crear (VistoEpisodioUsuario episodioVisto) {
        return vistoEpisodioUsuarioRepository.save(episodioVisto);
    }

    @Transactional(readOnly = true)
    public List<VistoEpisodioUsuario> listarTodosPorUsuario(Long idUsuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if (usuarioOptional.isPresent()) {
            return vistoEpisodioUsuarioRepository.buscarTodosPorUsuario(idUsuario);
        } else {
            throw new RuntimeException("Ese usuario no existe");
        }
    }

    @Transactional(readOnly = true)
    public List<VistoEpisodioUsuario> listarEpisodiosPorAnimeYUsuario(Long idUsuario, Long idAnime) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if (usuarioOptional.isPresent()) {
            Optional<Anime> animeOptional = animeRepository.findById(idAnime);
            if (animeOptional.isPresent()) {
                return vistoEpisodioUsuarioRepository.buscarTodosPorUsuarioYAnime(idUsuario, idAnime);
            } else {
                throw new RuntimeException("Ese anime no existe");
            }
        } else {
            throw new RuntimeException("Ese usuario no existe");
        }
    }

    @Transactional
    public VistoEpisodioUsuario actualizar (VistoEpisodioUsuario episodioVisto, Long idUsuario, Long idAnime, Long numEpisodio) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if (usuarioOptional.isPresent()) {
            Optional<Anime> animeOptional = animeRepository.findById(idAnime);
            if (animeOptional.isPresent()) {
                VistoEpisodioUsuarioId vistoId = new VistoEpisodioUsuarioId(idUsuario, idAnime, numEpisodio);
                Optional<VistoEpisodioUsuario> vistoOptional = vistoEpisodioUsuarioRepository.findById(vistoId);
                if(vistoOptional.isPresent()) {
                    VistoEpisodioUsuario vistoDB = vistoOptional.get();
                    if (episodioVisto.isHabilitado()) {
                        vistoDB.setHabilitado(true);
                    }
                    vistoEpisodioUsuarioRepository.save(vistoDB);
                    return vistoDB;
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
                VistoEpisodioUsuarioId vistoId = new VistoEpisodioUsuarioId(idUsuario, idAnime, numEpisodio);
                Optional<VistoEpisodioUsuario> vistoOptional = vistoEpisodioUsuarioRepository.findById(vistoId);
                if(vistoOptional.isPresent()) {
                    vistoEpisodioUsuarioRepository.deleteById(vistoId);
                    return "Episodio " + numEpisodio + " removido como visto del anime " + animeOptional.get().getNombre();
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
