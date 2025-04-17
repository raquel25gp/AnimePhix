package com.animephix.backend.repository;

import com.animephix.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("Select u FROM Usuario u WHERE u.rol.id_rol = ?1")
    boolean comprobarRolPorId(Long idRol);
}
