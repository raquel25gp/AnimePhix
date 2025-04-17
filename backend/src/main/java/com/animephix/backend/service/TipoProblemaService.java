package com.animephix.backend.service;

import com.animephix.backend.dto.TipoProblemaNombreRequestDTO;
import com.animephix.backend.model.TipoProblema;
import com.animephix.backend.repository.TipoProblemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TipoProblemaService {
    private TipoProblemaRepository tipoProblemaRepository;

    @Autowired
    public TipoProblemaService (TipoProblemaRepository tipoProblemaRepository) {
        this.tipoProblemaRepository = tipoProblemaRepository;
    }

    //Métodos privados
    public TipoProblemaNombreRequestDTO mapToNombreRequestDTO (TipoProblema tipo) {
        return TipoProblemaNombreRequestDTO.builder()
                .nombre(tipo.getNombre())
                .build();
    }

    //Endpoints específicos
    @Transactional(readOnly = true)
    public List<TipoProblemaNombreRequestDTO> listarNombresTipos() {
        List<TipoProblema> tipos = tipoProblemaRepository.findAll();
        List<TipoProblemaNombreRequestDTO> listadoNombres = new ArrayList<>();
        for (TipoProblema tipo: tipos) {
            listadoNombres.add(mapToNombreRequestDTO(tipo));
        }
        return listadoNombres;
    }

    //Operaciones CRUD
    @Transactional
    public TipoProblema crear (TipoProblema nuevoTipo) {
        return tipoProblemaRepository.save(nuevoTipo);
    }

    @Transactional(readOnly = true)
    public List<TipoProblema> listarTodos() {
        return tipoProblemaRepository.findAll();
    }

    @Transactional
    public TipoProblema actualizar (Long idTipo, TipoProblema tipo) {
        Optional<TipoProblema> tipoOptional = tipoProblemaRepository.findById(idTipo);
        if (tipoOptional.isPresent()) {
            TipoProblema tipoDB = tipoOptional.get();
            if (Objects.nonNull(tipo.getNombre()) && !"".equalsIgnoreCase(tipo.getNombre())) {
                tipoDB.setNombre(tipo.getNombre());
            }
            tipoProblemaRepository.save(tipoDB);
            return tipoDB;
        } else {
            throw new RuntimeException("Ese tipo de problema no se encuentra en la base de datos");
        }
    }

    @Transactional
    public String eliminarPorId(Long idTipo) {
        Optional<TipoProblema> tipoOptional = tipoProblemaRepository.findById(idTipo);
        if (tipoOptional.isPresent()) {
            tipoProblemaRepository.deleteById(idTipo);
            return "Se ha eliminado de la base de datos el tipo de problema: " + tipoOptional.get().getNombre();
        } else {
            return "Ese tipo de problema no se encuentra en la base de datos";
        }
    }
}
