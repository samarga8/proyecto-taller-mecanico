package com.gestortallermecanico.controller;

import com.gestortallermecanico.model.dao.UsuarioRegistroDTO;
import com.gestortallermecanico.service.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registro")
public class UsuarioController {

    @Autowired
    private IUsuarioService service;

    @ModelAttribute("usuario")
    public UsuarioRegistroDTO retornarNuevoUsuarioDTO(){
        return new UsuarioRegistroDTO();
    }

    @GetMapping
    public String mostrarFormularioRegistro(){
        return "registro";
    }

    @PostMapping
    public String registrarCuentaUsuario(@Valid @ModelAttribute("usuario") UsuarioRegistroDTO dto){
        try {
            service.registrarUsuario(dto);
            return "redirect:/registro?exito";
        } catch (Exception e) {
            return "redirect:/registro?nulo";
        }
    }
}
