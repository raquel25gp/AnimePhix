package com.animephix.backend.controller;

import com.animephix.backend.dto.AnimeNombreRequestDTO;
import com.animephix.backend.dto.AnimePaginaRequestDTO;
import com.animephix.backend.dto.FiltrosAnimeRequestDTO;
import com.animephix.backend.dto.TarjetaAnimeRequestDTO;
import com.animephix.backend.model.Anime;
import com.animephix.backend.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animes")
@CrossOrigin("http://localhost:4200/")
public class AnimeController {
    private AnimeService animeService;

    @Autowired
    public AnimeController (AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping("/en-emision")
    public ResponseEntity<List<AnimeNombreRequestDTO>> getAnimesEnEmision() {
        List<AnimeNombreRequestDTO> animesRecientes = animeService.listarAnimesRecientes();
        return ResponseEntity.ok(animesRecientes);
    }

    @GetMapping("/tarjeta-directorio")
    public ResponseEntity<List<TarjetaAnimeRequestDTO>> getTarjetaAnimes() {
        List<TarjetaAnimeRequestDTO> datosAnimes = animeService.enviarNombreImagenAnimes();
        return ResponseEntity.ok(datosAnimes);
    }

    @GetMapping("/filtros-directorio")
    public ResponseEntity<List<FiltrosAnimeRequestDTO>> getFiltrosAnimes() {
        List<FiltrosAnimeRequestDTO> filtros = animeService.enviarDatosFiltros();
        return ResponseEntity.ok(filtros);
    }

    @GetMapping("/establecer-filtros")
    public ResponseEntity<List<TarjetaAnimeRequestDTO>> gestionarFiltros (@RequestParam (required = false) String genero,
                                                          @RequestParam (required = false) String anio,
                                                          @RequestParam (required = false) String estado,
                                                          @RequestParam (required = false) String orden) {
        List<TarjetaAnimeRequestDTO> animes = animeService.gestionarFiltros(genero, anio, estado, orden);
        return ResponseEntity.ok(animes);
    }

    @GetMapping("/buscar-anime")
    public ResponseEntity<List<TarjetaAnimeRequestDTO>> buscarAnimePorNombre(@RequestParam String nombre) {
        List<TarjetaAnimeRequestDTO> animes = animeService.buscarEnBarraBusqueda(nombre);
        return ResponseEntity.ok(animes);
    }

    @GetMapping("/anime-datos")
    public ResponseEntity<AnimePaginaRequestDTO> devolverDatosAnime(@RequestParam String nombre) {
        AnimePaginaRequestDTO anime = animeService.devolverDatosAnime(nombre);
        return ResponseEntity.ok(anime);
    }
}
