package com.animephix.backend.service;

import com.animephix.backend.model.Genero;
import com.animephix.backend.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GeneroService {
    private GeneroRepository generoRepository;

    @Autowired
    public GeneroService (GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    //Operaciones CRUD
    @Transactional
    public Genero crear (Genero genero) {
        return generoRepository.save(genero);
    }

    @Transactional(readOnly = true)
    public List<Genero> listarTodos() {
        return generoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Genero listarPorId (Long idGenero) {
        Optional<Genero> generoOptional = generoRepository.findById(idGenero);
        if(generoOptional.isPresent()) {
            Genero genero = generoOptional.get();
            return genero;
        } else {
            throw new RuntimeException("Ese género no existe");
        }
    }

    @Transactional
    public Genero actualizar(Genero genero, Long idGenero) {
        Optional<Genero> generoOptional = generoRepository.findById(idGenero);
        if(generoOptional.isPresent()) {
            Genero generoDB = generoOptional.get();
            if (Objects.nonNull(genero.getNombre()) && !"".equalsIgnoreCase(genero.getNombre())) {
                generoDB.setNombre(genero.getNombre());
            }
            if (Objects.nonNull(genero.getDescripcion()) && !"".equalsIgnoreCase(genero.getDescripcion())) {
                generoDB.setDescripcion(genero.getDescripcion());
            }
            generoRepository.save(generoDB);
            return generoDB;
        } else {
            throw new RuntimeException("Ese género no existe");
        }
    }

    @Transactional
    public String eliminarPorId (Long idGenero) {
        Optional<Genero> generoOptional = generoRepository.findById(idGenero);
        if(generoOptional.isPresent()) {
            generoRepository.deleteById(idGenero);
            return "Se ha eliminado el género: " + generoOptional.get().getNombre();
        } else {
            return "Ese género no existe";
        }
    }
}
