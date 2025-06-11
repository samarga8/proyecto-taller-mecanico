package com.gestortallermecanico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/citas")
public class VistaCitaController {

    @GetMapping
    public String mostrarCalendario() {

        return "agenda"; // Asumiendo que tienes un archivo calendario.html en templates
    }
}