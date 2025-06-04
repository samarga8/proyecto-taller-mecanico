package com.gestortallermecanico.controller;

import com.gestortallermecanico.model.dao.LineaFacturaRegistroDTO;
import com.gestortallermecanico.service.LineaFacturaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lineas")
public class LineaFacturaController {

    @Autowired
    private LineaFacturaServiceImpl service;


    @GetMapping("/nuevaLinea")
    public String mostrarFormularioNuevaLinea(Model model){
        model.addAttribute("linea",new LineaFacturaRegistroDTO());
        model.addAttribute("accion","");
        return "formularioCliente";
    }
}
