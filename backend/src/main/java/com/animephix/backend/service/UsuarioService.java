package com.animephix.backend.service;

import com.animephix.backend.model.Usuario;
import com.animephix.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService (UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //Operaciones CRUD
    @Transactional
    public Usuario crear (Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario listarPorId(Long idUsuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if(usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            return usuario;
        } else {
            throw new RuntimeException("Ese usuario no existe");
        }
    }

    @Transactional
    public Usuario actualizar (Usuario usuario, Long idUsuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if(usuarioOptional.isPresent()) {
            Usuario usuarioDB = usuarioOptional.get();
            if (Objects.nonNull(usuario.getNombre()) && !"".equalsIgnoreCase(usuario.getNombre())) {
                usuarioDB.setNombre(usuario.getNombre());
            }
            if (Objects.nonNull(usuario.getEmail()) && !"".equalsIgnoreCase(usuario.getEmail())) {
                usuarioDB.setEmail(usuario.getEmail());
            }
            if (Objects.nonNull(usuario.getContrasenia()) && !"".equalsIgnoreCase(usuario.getContrasenia())) {
                usuarioDB.setContrasenia(usuario.getContrasenia());
            }
            if (usuario.isHabilitado()) {
                usuarioDB.setHabilitado(false);
            }
            if (usuarioRepository.comprobarRolPorId(usuario.getRol().getId_rol())) {
                usuarioDB.setRol(usuario.getRol());
            } else {
                throw new RuntimeException("Ese rol no existe");
            }
            usuarioRepository.save(usuarioDB);
            return usuarioDB;
        } else {
            throw new RuntimeException("Ese usuario no existe");
        }
    }

    @Transactional
    public String eliminarPorId(Long idUsuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if(usuarioOptional.isPresent()) {
            usuarioRepository.deleteById(idUsuario);
            return "Se ha eliminado al usuario: " + usuarioOptional.get().getNombre();
        } else {
            return "Ese usuario no existe";
        }
    }
}
