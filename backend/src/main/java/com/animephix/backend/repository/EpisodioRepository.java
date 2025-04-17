package com.animephix.backend.repository;

import com.animephix.backend.dto.UltimosEpisodiosRequestDTO;
import com.animephix.backend.model.Episodio;
import com.animephix.backend.model.compositePK.EpisodioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EpisodioRepository extends JpaRepository<Episodio, EpisodioId> {
    @Query("Select e FROM Episodio e WHERE e.anime.id_anime = ?1")
    List<Episodio> buscarEpisodiosPorAnime(Long idAnime);

    Optional<Episodio> findById(EpisodioId id);

    void deleteById(EpisodioId id);

    //Obtener los ultimos 9 episodios agregados
    @Query("Select new com.animephix.backend.dto.UltimosEpisodiosRequestDTO(a.nombre, e.num_episodio, e.urlPoster) FROM Episodio e JOIN Anime a ON a.id_anime = e.anime.id_anime WHERE a.estado.id_estado = 1 AND e.fechaLanzamiento = (SELECT MAX(e2.fechaLanzamiento) FROM Episodio e2 WHERE e2.anime.id_anime = a.id_anime) ORDER BY e.fechaLanzamiento DESC LIMIT 9")
    List<UltimosEpisodiosRequestDTO> obtenerUltimosEpisodios();

    //Obtener un episodio por anime y numero de episodio
    @Query("Select e FROM Episodio e WHERE e.anime.id_anime = ?1 AND e.num_episodio = ?2")
    Episodio buscarEpisodioByIdAnimeAndNumEpisodio(Long idAnime, Long numEpisodio);

    //Calcular total de episodios por anime
    @Query("Select COUNT(e) FROM Episodio e WHERE e.anime.nombre = ?1")
    Long calcularTotalEpisodios(String nombre);
}
