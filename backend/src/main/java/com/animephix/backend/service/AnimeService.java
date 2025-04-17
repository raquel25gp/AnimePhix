package com.animephix.backend.service;

import com.animephix.backend.dto.*;
import com.animephix.backend.model.Anime;
import com.animephix.backend.model.Episodio;
import com.animephix.backend.repository.AnimeRepository;
import com.animephix.backend.repository.EpisodioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnimeService {
    private AnimeRepository animeRepository;
    private EpisodioRepository episodioRepository;

    @Autowired
    public AnimeService (AnimeRepository animeRepository, EpisodioRepository episodioRepository) {
        this.animeRepository = animeRepository;
        this.episodioRepository = episodioRepository;
    }

    //Métodos privados
    private AnimeNombreRequestDTO nombreMapToRequestDTO(String nombre) {
        return AnimeNombreRequestDTO.builder()
                .nombre(nombre)
                .build();
    }

    private TarjetaAnimeRequestDTO tarjetaAnimeMapToRequestDTO(Anime anime) {
        return TarjetaAnimeRequestDTO.builder()
                .nombre(anime.getNombre())
                .urlImagen(anime.getUrlImagen())
                .build();
    }

    private FiltrosAnimeRequestDTO filtroMapToRequestDTO(Anime anime) {
        return FiltrosAnimeRequestDTO.builder()
                .genero(anime.getGenero().getNombre())
                .anio(anime.getFechaInicio().getYear())
                .estado(anime.getEstado().getNombre())
                .fechaInicio(anime.getFechaInicio())
                .build();
    }

    private List<TarjetaAnimeRequestDTO> convertirAnimeADTO(List<Anime> listado) {
        return listado.stream()
                .map(this::tarjetaAnimeMapToRequestDTO)
                .collect(Collectors.toList());
    }

    //Metodo privado para implementar el orden pasado como filtro
    private List<TarjetaAnimeRequestDTO> ordenarPorFiltro (List<Anime> listado, String tipoOrden) {
        switch (tipoOrden) {
            case "Ascendente":
                listado.sort(Comparator.comparing(Anime::getNombre));
                return convertirAnimeADTO(listado);
            case "Descendente":
                listado.sort(Comparator.comparing(Anime::getNombre).reversed());
                return convertirAnimeADTO(listado);
            case "Añadidos recientemente":
                listado.sort(Comparator.comparing(Anime::getFechaInicio).reversed());
                return convertirAnimeADTO(listado);
            default:
                return convertirAnimeADTO(listado);
        }
    }

    private AnimePaginaRequestDTO animePaginaMapToRequestDTO (Anime anime, List<EpisodiolistaRequestDTO> listado) {
        return AnimePaginaRequestDTO.builder()
                .nombre(anime.getNombre())
                .descripcion(anime.getDescripcion())
                .urlImagen(anime.getUrlImagen())
                .genero(anime.getGenero().getNombre())
                .estado(anime.getEstado().getNombre())
                .listadoEpisodios(listado)
                .build();
    }

    //Endpoints específicos
    @Transactional(readOnly = true)
    public List<AnimeNombreRequestDTO> listarAnimesRecientes() {
        List<String> nombresAnimes = animeRepository.buscarNombresAnimesEnEmisión();
        List<AnimeNombreRequestDTO> nombresAnimesMapeados = new ArrayList<>();
        for (String nombre: nombresAnimes) {
            nombresAnimesMapeados.add((nombreMapToRequestDTO(nombre)));
        }
        return nombresAnimesMapeados;
    }

    @Transactional(readOnly = true)
    public List<TarjetaAnimeRequestDTO> enviarNombreImagenAnimes() {
        List<Anime> animes = animeRepository.findAll();
        return animes.stream()
                .map(this::tarjetaAnimeMapToRequestDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FiltrosAnimeRequestDTO> enviarDatosFiltros() {
        List<Anime> animes = animeRepository.findAll();
        return animes.stream()
                .map(this::filtroMapToRequestDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TarjetaAnimeRequestDTO> gestionarFiltros(String genero, String anio, String estado, String orden) {
        Integer anioNum = null;

        //Paso el año a número solo si tiene valor
        if (anio != null && !anio.isBlank()) {
            try {
                anioNum = Integer.parseInt(anio);
            } catch (NumberFormatException e) {
                //Si no es un número válido, lo ignoro como si no se hubiera aplicado el filtro
                anioNum = null;
            }
        }

        List<Anime> listadoFiltrado = new ArrayList<>();

        //Variables auxiliares para comprobar los filtros
        boolean tieneGenero = genero != null && !genero.isBlank();
        boolean tieneAnio = anioNum != null;
        boolean tieneEstado = estado != null && !estado.isBlank();
        boolean tieneOrden = orden != null && !orden.equals("Por defecto") && !orden.isBlank();

        //Filtros múltiples
        if (tieneGenero && tieneAnio && tieneEstado) {
            listadoFiltrado = animeRepository.buscarByGeneroAndAnioAndEstado(genero, anioNum, estado);
        } else if (tieneGenero && tieneAnio) {
            listadoFiltrado = animeRepository.buscarByGeneroAndAnio(genero, anioNum);
        } else if (tieneGenero && tieneEstado) {
            listadoFiltrado = animeRepository.findByGeneroNombreAndEstadoNombre(genero, estado);
        } else if (tieneAnio && tieneEstado) {
            listadoFiltrado = animeRepository.buscarByAnioAndEstado(anioNum, estado);
        } else if (tieneGenero) {
            listadoFiltrado = animeRepository.findByGeneroNombre(genero);
        } else if (tieneAnio) {
            listadoFiltrado = animeRepository.buscarByAnio(anioNum);
        } else if (tieneEstado) {
            listadoFiltrado = animeRepository.findByEstadoNombre(estado);
        } else {
            listadoFiltrado = animeRepository.findAll(); //si no hay filtros
        }

        //Aplico orden si se pasa como parametro
        return tieneOrden ? ordenarPorFiltro(listadoFiltrado, orden) : convertirAnimeADTO(listadoFiltrado);
    }

    @Transactional(readOnly = true)
    public List<TarjetaAnimeRequestDTO> buscarEnBarraBusqueda(String nombre) {
        List<Anime> animes = animeRepository.findByNombreContainingIgnoreCase(nombre);
        return convertirAnimeADTO(animes);
    }

    @Transactional(readOnly = true)
    public AnimePaginaRequestDTO devolverDatosAnime(String nombre) {
        Anime anime = animeRepository.findByNombre(nombre);
        List<Episodio> episodiosAnime = episodioRepository.buscarEpisodiosPorAnime(anime.getId_anime());

        //Ordeno los episodios de manera que el primero será el último que se ha añadido
        episodiosAnime.sort(Comparator.comparing(Episodio::getFechaLanzamiento).reversed());

        //Generación del listado con el nombre y el episodio
        List<EpisodiolistaRequestDTO> listadoEpisodios = new ArrayList<>();
        for (Episodio episodio: episodiosAnime) {
            listadoEpisodios.add(
                    EpisodiolistaRequestDTO.builder()
                            .anime(anime.getNombre())
                            .numEpisodio(episodio.getNum_episodio())
                            .build()
            );
        }

        return animePaginaMapToRequestDTO(anime, listadoEpisodios);
    }

    //Operaciones CRUD
    @Transactional
    public Anime crear (Anime nuevoAnime) {
        return animeRepository.save(nuevoAnime);
    }

    @Transactional(readOnly = true)
    public List<Anime> listarTodos() {
        return animeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Anime listarPorId (Long idAnime) {
        Optional<Anime> animeOptional = animeRepository.findById(idAnime);
        if (animeOptional.isPresent()) {
            Anime anime = animeOptional.get();
            return anime;
        } else {
            throw new RuntimeException("Ese anime no existe");
        }
    }

    @Transactional
    public Anime actualizar(Anime anime, Long idAnime) {
        Optional<Anime> animeOptional = animeRepository.findById(idAnime);
        if (animeOptional.isPresent()) {
            Anime animeDB = animeOptional.get();

            if (Objects.nonNull(anime.getNombre()) && !"".equalsIgnoreCase(anime.getNombre())) {
                animeDB.setNombre(anime.getNombre());
            }
            if (Objects.nonNull(anime.getDescripcion()) && !"".equalsIgnoreCase(anime.getDescripcion())) {
                animeDB.setDescripcion(anime.getDescripcion());
            }
            if (Objects.nonNull(anime.getFechaInicio())) {
                animeDB.setFechaInicio(anime.getFechaInicio());
            }
            if (Objects.nonNull(anime.getFechaFin())) {
                animeDB.setFechaFin(anime.getFechaFin());
            }
            if (Objects.nonNull(anime.getDiaSemana())) {
                animeDB.setDiaSemana(anime.getDiaSemana());
            }
            if (Objects.nonNull(anime.getUrlImagen()) && !"".equalsIgnoreCase(anime.getUrlImagen())) {
                animeDB.setUrlImagen(anime.getUrlImagen());
            }
            if (!anime.isVisible()) {
                animeDB.setVisible(true);
            }

            if (animeRepository.buscarGeneroPorId(anime.getGenero().getId_genero())) {
                animeDB.setGenero(anime.getGenero());
            } else {
                throw new RuntimeException("Ese género no existe");
            }

            if (animeRepository.buscarEstadoPorId(anime.getEstado().getId_estado())) {
                animeDB.setEstado(anime.getEstado());
            } else {
                throw new RuntimeException("Ese género no existe");
            }

            animeRepository.save(animeDB);
            return animeDB;
        } else {
            throw new RuntimeException("Ese anime no existe");
        }
    }

    @Transactional
    public String eliminarPorId(Long idAnime) {
        Optional<Anime> animeOptional = animeRepository.findById(idAnime);
        if (animeOptional.isPresent()) {
            animeRepository.deleteById(idAnime);
            return "Se ha eliminado: " + animeOptional.get().getNombre();
        } else {
            return "El anime indicado no existe.";
        }
    }
}
