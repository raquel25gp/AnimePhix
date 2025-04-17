package com.animephix.backend.controller;

import com.animephix.backend.dto.EpisodioVideoRequestDTO;
import com.animephix.backend.dto.UltimosEpisodiosRequestDTO;
import com.animephix.backend.service.EpisodioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/episodios")
@CrossOrigin("http://localhost:4200/")
public class EpisodioController {
    private EpisodioService episodioService;

    @Autowired
    public EpisodioController (EpisodioService episodioService) {
        this.episodioService = episodioService;
    }

    @GetMapping("/recientes")
    public ResponseEntity<List<UltimosEpisodiosRequestDTO>> mostrarEpisodiosRecientes() {
        List<UltimosEpisodiosRequestDTO> episodios = episodioService.buscarUltimosEpisodiosPublicados();
        return ResponseEntity.ok(episodios);
    }

    @GetMapping("/especifico")
    public ResponseEntity<EpisodioVideoRequestDTO> devolverEpisodioEspecifico(@RequestParam String nombre,
                                                                              @RequestParam Long numero) {
        EpisodioVideoRequestDTO episodio = episodioService.devolverDatosEpisodio(nombre, numero);
        return ResponseEntity.ok(episodio);
    }

    @GetMapping("/total-anime")
    public ResponseEntity<Long> devolverTotalEpisodiosPorAnime(@RequestParam String nombre) {
        Long total = episodioService.calcularTotalEpisodiosPorAnime(nombre);
        return ResponseEntity.ok(total);
    }
}
