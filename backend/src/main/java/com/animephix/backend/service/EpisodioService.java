package com.animephix.backend.service;

import com.animephix.backend.dto.EpisodioVideoRequestDTO;
import com.animephix.backend.dto.UltimosEpisodiosRequestDTO;
import com.animephix.backend.model.Anime;
import com.animephix.backend.model.Episodio;
import com.animephix.backend.model.compositePK.EpisodioId;
import com.animephix.backend.repository.AnimeRepository;
import com.animephix.backend.repository.EpisodioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EpisodioService {
    private EpisodioRepository episodioRepository;
    private AnimeRepository animeRepository;

    @Autowired
    public EpisodioService (EpisodioRepository episodioRepository, AnimeRepository animeRepository) {
        this.episodioRepository = episodioRepository;
        this.animeRepository = animeRepository;
    }

    //Endpoints específicos
    @Transactional(readOnly = true)
    public List<UltimosEpisodiosRequestDTO> buscarUltimosEpisodiosPublicados() {
        return episodioRepository.obtenerUltimosEpisodios();
    }

    @Transactional(readOnly = true)
    public EpisodioVideoRequestDTO devolverDatosEpisodio(String nombreAnime, Long numEpisodio) {
        Long idAnime = animeRepository.buscarIdByNombre(nombreAnime);
        Episodio episodio = episodioRepository.buscarEpisodioByIdAnimeAndNumEpisodio(idAnime, numEpisodio);
        return EpisodioVideoRequestDTO.builder()
                .urlPoster(episodio.getUrlPoster())
                .urlVideo(episodio.getUrlVideo())
                .build();
    }

    @Transactional(readOnly = true)
    public Long calcularTotalEpisodiosPorAnime (String nombreAnime) {
        return episodioRepository.calcularTotalEpisodios(nombreAnime);
    }

    //Operaciones CRUD
    @Transactional
    public Episodio crear (Episodio episodio) {
        return episodioRepository.save(episodio);
    }

    @Transactional(readOnly = true)
    public List<Episodio> listarTodos() {
        return episodioRepository.findAll();
    }

    @Transactional
    public Episodio actualizar (Episodio episodio, Long idAnime, Long idEpisodio) {
        Optional<Anime> animeOptional = animeRepository.findById(idAnime);
        if (animeOptional.isPresent()) {
            EpisodioId episodioId = new EpisodioId(idAnime, idEpisodio);
            Optional<Episodio> episodioOptional = episodioRepository.findById(episodioId);
            if (episodioOptional.isPresent()) {
                Episodio episodioDB = episodioOptional.get();
                if (Objects.nonNull(episodio.getUrlVideo()) && !"".equalsIgnoreCase(episodio.getUrlVideo())) {
                    episodioDB.setUrlVideo(episodio.getUrlVideo());
                }
                if (Objects.nonNull(episodio.getFechaLanzamiento())) {
                    episodioDB.setFechaLanzamiento(episodio.getFechaLanzamiento());
                }
                if (Objects.nonNull(episodio.getUrlPoster()) && !"".equalsIgnoreCase(episodio.getUrlPoster())) {
                    episodioDB.setUrlPoster(episodio.getUrlPoster());
                }
                episodioRepository.save(episodioDB);
                return episodioDB;
            } else {
                throw new RuntimeException("Ese episodio no existe para este anime");
            }
        } else {
            throw new RuntimeException("Ese anime no existe");
        }
    }

    @Transactional
    public String eliminarUnEpisodioPorId(Long idAnime, Long idEpisodio) {
        Optional<Anime> animeOptional = animeRepository.findById(idAnime);
        if (animeOptional.isPresent()) {
            EpisodioId episodioId = new EpisodioId(idAnime, idEpisodio);
            Optional<Episodio> episodioOptional = episodioRepository.findById(episodioId);
            if (episodioOptional.isPresent()) {
                episodioRepository.deleteById(episodioId);
                return "Se ha eliminado el episodio " + idEpisodio + " del anime " + animeOptional.get().getNombre();
            } else {
                return "Ese episodio no existe para este anime";
            }
        } else {
            return "Ese anime no existe";
        }
    }
}
