package com.animephix.backend.controller;

import com.animephix.backend.dto.TipoProblemaNombreRequestDTO;
import com.animephix.backend.service.TipoProblemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tipos-problemas")
@CrossOrigin("http://localhost:4200/")
public class TipoProblemaController {
    private TipoProblemaService tipoProblemaService;

    @Autowired
    public TipoProblemaController (TipoProblemaService tipoProblemaService) {
        this.tipoProblemaService = tipoProblemaService;
    }

    //Endpoints específicos
    @GetMapping("/listado-nombres")
    public List<TipoProblemaNombreRequestDTO> getNombreTipos() {
        return tipoProblemaService.listarNombresTipos();
    }

    //Operaciones CRUD
}
