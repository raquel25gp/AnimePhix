package com.animephix.backend.service;

import com.animephix.backend.model.Rol;
import com.animephix.backend.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RolService {
    private RolRepository rolRepository;

    @Autowired
    public RolService (RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    //Operaciones CRUD
    @Transactional
    public Rol crear (Rol nuevoRol) {
        return rolRepository.save(nuevoRol);
    }

    @Transactional(readOnly = true)
    public List<Rol> listarTodos () {
        return rolRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Rol listarPorId (Long idRol) {
        Optional<Rol> rolOptional = rolRepository.findById(idRol);
        if (rolOptional.isPresent()) {
            Rol rol = rolOptional.get();
            return rol;
        } else {
            throw new RuntimeException("Ese rol no existe");
        }
    }

    @Transactional
    public Rol actualizar (Rol rol, Long idRol) {
        Optional<Rol> rolOptional = rolRepository.findById(idRol);
        if (rolOptional.isPresent()) {
            Rol rolDB = rolOptional.get();
            if (Objects.nonNull(rol.getNombre()) && !"".equalsIgnoreCase(rol.getNombre())) {
                rolDB.setNombre(rol.getNombre());
            }
            rolRepository.save(rolDB);
            return rolDB;
        } else {
            throw new RuntimeException("Ese rol no existe");
        }
    }

    @Transactional
    public String eliminarPorId (Long idRol) {
        Optional<Rol> rolOptional = rolRepository.findById(idRol);
        if (rolOptional.isPresent()) {
            rolRepository.deleteById(idRol);
            return "Se ha eliminado: " + rolOptional.get().getNombre();
        } else {
            return "Ese rol no existe";
        }
    }
}
