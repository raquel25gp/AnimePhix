package com.animephix.backend.repository;

import com.animephix.backend.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    @Query("Select r FROM Reporte r WHERE r.tipoProblema.id_tipoProblema = ?1")
    boolean comprobarTipoProblemaPorId(Long idTipoProblema);

    @Query("Select r FROM Reporte r WHERE r.usuario.id_usuario = ?1")
    boolean comprobarUsuario(Long idUsuario);
}
