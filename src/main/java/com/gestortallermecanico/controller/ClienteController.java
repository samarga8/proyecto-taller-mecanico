package com.gestortallermecanico.controller;

import com.gestortallermecanico.model.Cliente;
import com.gestortallermecanico.model.dao.ClienteRegistroDTO;
import com.gestortallermecanico.service.IClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/obtenerCliente/{origen}")
    public String mostrarFormularioObtenerCliente(@PathVariable String origen, Model model) {

        Cliente cliente = new Cliente();
        if (origen.equals("buscar")){
            model.addAttribute("cliente", cliente);
            model.addAttribute("accion", "/clientes/obtenerCliente");
            return "formularioObtenerCliente";
        }
        if (origen.equals("factura")){
            model.addAttribute("cliente", cliente);
            model.addAttribute("accion", "/clientes/obtenerCliente");
            return "formularioFactura";
        }
        model.addAttribute("cliente", cliente);
        model.addAttribute("accion", "/clientes/obtenerCliente");
        return "formularioObtenerCliente";

    }

    @PostMapping("/obtenerCliente/{origen}")
    public String obtenerCliente(@ModelAttribute("cliente") Cliente c,
                                 Model model,@PathVariable String origen) {
        try {
            Cliente cliente = service.obtenerCliente(c.getDni());
            if (origen.equals("factura")){
                model.addAttribute("cliente", cliente);
                return "redirect:/facturas/nuevaFactura/"+ cliente.getDni();
            }
            if (origen.equals("buscar")){
                model.addAttribute("cliente", cliente);
                return "formularioObtenerCliente";
            }
                model.addAttribute("cliente", cliente);
                return "formularioObtenerCliente";


        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("accion", "/clientes/obtenerCliente");
            return "formularioObtenerCliente";
        }
    }
}


