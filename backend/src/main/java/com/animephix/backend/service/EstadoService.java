package com.animephix.backend.service;

import com.animephix.backend.model.Estado;
import com.animephix.backend.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EstadoService {
    private EstadoRepository estadoRepository;

    @Autowired
    public EstadoService (EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    //Operaciones CRUD
    @Transactional
    public Estado crear (Estado estado) {
        return estadoRepository.save(estado);
    }

    @Transactional(readOnly = true)
    public List<Estado> listarTodos() {
        return estadoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Estado listarPorId(Long idEstado) {
        Optional<Estado> estadoOptional = estadoRepository.findById(idEstado);
        if (estadoOptional.isPresent()) {
            Estado estado = estadoOptional.get();
            return estado;
        } else {
            throw new RuntimeException("Ese estado no existe");
        }
    }

    @Transactional
    public Estado actualizar(Estado estado, Long idEstado) {
        Optional<Estado> estadoOptional = estadoRepository.findById(idEstado);
        if (estadoOptional.isPresent()) {
            Estado estadoDB = estadoOptional.get();
            if (Objects.nonNull(estado.getNombre()) && !"".equalsIgnoreCase(estado.getNombre())) {
                estadoDB.setNombre(estado.getNombre());
            }
            estadoRepository.save(estadoDB);
            return estadoDB;
        } else {
            throw new RuntimeException("Ese estado no existe");
        }
    }

    @Transactional
    public String eliminarPorId(Long idEstado) {
        Optional<Estado> estadoOptional = estadoRepository.findById(idEstado);
        if (estadoOptional.isPresent()) {
            estadoRepository.deleteById(idEstado);
            return "Se ha eliminado el estado: " + estadoOptional.get().getNombre();
        } else {
            return "Ese estado no existe";
        }
    }
}
