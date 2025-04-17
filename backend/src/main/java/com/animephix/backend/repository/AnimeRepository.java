package com.animephix.backend.repository;

import com.animephix.backend.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {
    @Query("Select a FROM Anime a WHERE a.genero.id_genero = ?1")
    boolean buscarGeneroPorId(Long idGenero);

    @Query("Select a FROM Anime a WHERE a.estado.id_estado = ?1")
    boolean buscarEstadoPorId(Long idEstado);

    //Buscar animes en emisión
    @Query("Select a.nombre from Anime a WHERE a.estado.id_estado=1 order by a.fechaInicio")
    List<String> buscarNombresAnimesEnEmisión();

    //Filtros del directorio
    List<Anime> findByGeneroNombre (String genero);

    @Query("Select a FROM Anime a WHERE YEAR(a.fechaInicio) = ?1")
    List<Anime> buscarByAnio(int anio);

    List<Anime> findByEstadoNombre (String estado);

    @Query("Select a FROM Anime a WHERE a.genero.nombre = ?1 AND YEAR(a.fechaInicio) = ?2")
    List<Anime> buscarByGeneroAndAnio(String genero, int anio);

    List<Anime> findByGeneroNombreAndEstadoNombre(String genero, String estado);

    @Query("Select a FROM Anime a WHERE YEAR(a.fechaInicio) = ?1 AND a.estado.nombre = ?2")
    List<Anime> buscarByAnioAndEstado(int anio, String estado);

    @Query("Select a FROM Anime a WHERE a.genero.nombre = ?1 AND YEAR(a.fechaInicio) = ?2 AND a.estado.nombre = ?3")
    List<Anime> buscarByGeneroAndAnioAndEstado(String genero, int anio, String estado);

    //Búsqueda por nombre en la barra de búsqueda
    List<Anime> findByNombreContainingIgnoreCase(String nombre);

    //Buscar un anime por su nombre completo
    Anime findByNombre(String nombre);

    //Buscar id del anime por su nombre
    @Query("Select a.id_anime FROM Anime a WHERE a.nombre = ?1")
    Long buscarIdByNombre(String nombre);

}
