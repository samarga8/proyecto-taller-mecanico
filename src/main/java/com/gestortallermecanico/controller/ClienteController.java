package com.gestortallermecanico.controller;

import com.gestortallermecanico.model.Cliente;
import com.gestortallermecanico.model.dao.ClienteRegistroDTO;
import com.gestortallermecanico.service.IClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private IClienteService service;

    @GetMapping("/nuevoCliente")
    public String formularioNuevoCliente(Model model) {
        model.addAttribute("cliente", new ClienteRegistroDTO());
        model.addAttribute("accion", "/clientes/nuevoCliente");
        return "formularioCliente";
    }

    @PostMapping("/nuevoCliente")
    public String guardarNuevaPersona(@ModelAttribute("cliente") @Valid ClienteRegistroDTO dto,
                                      BindingResult result,
                                      Model model) {
        if (result.hasErrors()) {
            model.addAttribute("accion", "/clientes/nuevoCliente");
            return "formularioCliente";
        }

        try {
            service.registrarCliente(dto);
            return "formularioCliente";
        } catch (Exception e) {
            model.addAttribute("accion", "/clientes/nuevoCliente");
            model.addAttribute("error", e.getMessage());
            return "formularioCliente";
        }
    }

    @GetMapping("/listar")
    public String listarClientes(Model model) {
        List<Cliente> lista = service.listarClientes();
        model.addAttribute("listaClientes", lista);
        return "listarCliente";

    }

    @GetMapping("/obtenerCliente")
    public String mostrarFormularioObtenerCliente(Model model) {
        // Crear un objeto Cliente vacío con solo el campo DNI
        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);
        model.addAttribute("accion", "/clientes/obtenerCliente");
        return "formularioObtenerCliente";
    }

    @PostMapping("/obtenerCliente")
    public String obtenerCliente(@ModelAttribute("cliente") Cliente cliente,
                                 Model model) {
        try {
            Cliente resultado = service.obtenerCliente(cliente.getDni());
            model.addAttribute("cliente", resultado);
            return "formularioObtenerCliente";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("accion", "/clientes/obtenerCliente");
            return "formularioObtenerCliente";
        }
    }
}


