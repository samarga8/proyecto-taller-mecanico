package com.gestortallermecanico.controller;


import com.gestortallermecanico.model.Cliente;
import com.gestortallermecanico.model.Vehiculo;
import com.gestortallermecanico.model.dao.VehiculoRegistroDTO;
import com.gestortallermecanico.repository.VehiculoRepository;
import com.gestortallermecanico.service.IClienteService;
import com.gestortallermecanico.service.VehiculoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vehiculos")
public class VehiculoControler {

    @Autowired
    private VehiculoServiceImpl service;


    @GetMapping("/nuevoVehiculo")
    public String formularioVehiculo(Model model){
        model.addAttribute("vehiculo",new VehiculoRegistroDTO());
        model.addAttribute("accion","vehiculos/nuevoVehiculo");
        return "formularioVehiculo";

    }

    @PostMapping("/nuevoVehiculo")
    public String guardarNuevoVehiculo(@ModelAttribute("vehiculo") @Valid VehiculoRegistroDTO dto,
                                      BindingResult result,
                                      Model model) {

        if (result.hasErrors()) {
            model.addAttribute("accion", "/vehiculos/nuevoVehiculo");
            return "formularioVehiculo";
        }

        try {
            service.guardarVehiculo(dto,dto.getDni());
            return "index";
        } catch (Exception e) {
            model.addAttribute("accion", "/vehiculos/nuevoVehiculo");
            model.addAttribute("error", e.getMessage());
            return "formularioVehiculo";
        }
    }
}
