package com.animephix.backend.service;

import com.animephix.backend.model.Reporte;
import com.animephix.backend.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReporteService {
    private ReporteRepository reporteRepository;

    @Autowired
    public ReporteService (ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    //Operaciones CRUD
    @Transactional
    public Reporte crear (Reporte nuevoReporte) {
        return reporteRepository.save(nuevoReporte);
    }

    @Transactional(readOnly = true)
    public List<Reporte> listarAll() {
        return reporteRepository.findAll();
    }

    @Transactional
    public Reporte actualizar (Long idReporte, Reporte reporte) {
        Optional<Reporte> reporteOptional = reporteRepository.findById(idReporte);
        if (reporteOptional.isPresent()) {
            Reporte reporteDB = reporteOptional.get();
            if (Objects.nonNull(reporte.getDescripcion()) && !"".equalsIgnoreCase(reporte.getDescripcion())) {
                reporteDB.setDescripcion(reporte.getDescripcion());
            }
            if (!reporte.isCorregido()) {
                reporteDB.setCorregido(true);
            }
            if (reporteRepository.comprobarTipoProblemaPorId(reporte.getTipoProblema().getId_tipoProblema())) {
                reporteDB.setTipoProblema(reporte.getTipoProblema());
            }
            if (reporteRepository.comprobarUsuario(reporte.getUsuario().getId_usuario())) {
                reporteDB.setUsuario(reporte.getUsuario());
            }
            reporteRepository.save(reporteDB);
            return reporteDB;
        } else {
            throw new RuntimeException("Ese reporte no existe");
        }
    }

    @Transactional
    public String eliminarPorId (Long idReporte) {
        Optional<Reporte> reporteOptional = reporteRepository.findById(idReporte);
        if (reporteOptional.isPresent()) {
            reporteRepository.deleteById(idReporte);
            return "Se ha eliminado el reporte: " + reporteOptional.get().getTipoProblema().getNombre() + " del usuario: " +
                    reporteOptional.get().getUsuario().getNombre();
        } else {
            return "Ese reporte no existe";
        }
    }
}
