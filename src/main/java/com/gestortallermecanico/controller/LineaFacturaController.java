package com.gestortallermecanico.controller;

import com.gestortallermecanico.model.Factura;
import com.gestortallermecanico.model.LineaFactura;
import com.gestortallermecanico.model.dao.LineaFacturaRegistroDTO;
import com.gestortallermecanico.service.LineaFacturaServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/lineas")
public class LineaFacturaController {

    @Autowired
    private LineaFacturaServiceImpl service;


    @GetMapping("/nuevaLinea/{numeroFact}")
    public String mostrarFormularioNuevaLinea(Model model, @PathVariable String numeroFact){
        LineaFacturaRegistroDTO linea = new LineaFacturaRegistroDTO();
        linea.setNumFact(numeroFact);
        model.addAttribute("linea",linea);
        model.addAttribute("accion","lineas/nuevaLinea");
        return "formularioLinea";
    }

    @PostMapping("/guardar")
    public String guardarLinea(@ModelAttribute("linea") LineaFacturaRegistroDTO dto,
                               HttpSession session, RedirectAttributes redirectAttributes) {
        Factura factura = (Factura) session.getAttribute("facturaEnProceso");
        if (factura == null) {
            redirectAttributes.addFlashAttribute("error", "No hay factura activa.");
            return "redirect:/facturas";
        }


        LineaFactura linea = new LineaFactura();
        linea.setConcepto(dto.getConcepto());
        linea.setPrecio(dto.getPrecio());
        linea.setCantidad(dto.getCantidad());
        linea.setTotal(dto.getCantidad() * dto.getPrecio());
        linea.setFactura(factura);

        factura.getLineas().add(linea);
        session.setAttribute("facturaEnProceso", factura);

        return "redirect:/facturas/nuevaFactura/continuar";
    }
}


