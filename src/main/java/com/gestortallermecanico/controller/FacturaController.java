package com.gestortallermecanico.controller;

import com.gestortallermecanico.model.Cliente;
import com.gestortallermecanico.model.Factura;
import com.gestortallermecanico.model.LineaFactura;
import com.gestortallermecanico.repository.ClienteRepository;
import com.gestortallermecanico.repository.LineaFacturaRepository;
import com.gestortallermecanico.service.IClienteService;
import com.gestortallermecanico.service.IFacturaService;
import com.gestortallermecanico.service.ILineaFacturaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private IFacturaService facturaService;

    @Autowired
    private ClienteRepository repoCliente;

    @Autowired
    private ILineaFacturaService lineaService;

    @Autowired
    private LineaFacturaRepository repoLinea;

    @GetMapping("/nuevaFactura/{dni}")
    public String crearFormularioFactura(Model model, @PathVariable String dni, HttpSession session) {

        Cliente cliente = repoCliente.findFirstByDni(dni).get();

        Factura factura = new Factura();
        String numFact = facturaService.crearNumeroFactura();
        factura.setNumeroFact(numFact);
        factura.setFecha(LocalDate.now());
        factura.setCliente(cliente);
        factura.setPagada(false);

        // Guardar en sesión
        session.setAttribute("facturaEnProceso", factura);

        model.addAttribute("cliente", cliente);
        model.addAttribute("factura", factura);
        model.addAttribute("lineas", factura.getLineas());
        model.addAttribute("accion", "facturas/nuevaFactura");

        return "formularioNuevaFactura";
    }

    @GetMapping("/nuevaFactura/continuar")
    public String continuarFormularioFactura(Model model, HttpSession session) {
        Factura factura = (Factura) session.getAttribute("facturaEnProceso");

        if (factura == null) {
            return "redirect:/clientes/obtenerCliente";
        }

        model.addAttribute("cliente", factura.getCliente());
        model.addAttribute("factura", factura);
        model.addAttribute("lineas", factura.getLineas());
        model.addAttribute("accion", "facturas/nuevaFactura");

        return "formularioNuevaFactura";
    }

    @PostMapping("/nuevaFactura/{numeroFact}")
    public String guardarFactura(@PathVariable String numeroFact, HttpSession session) {
        Factura factura = (Factura) session.getAttribute("facturaEnProceso");

        if (factura == null || !factura.getNumeroFact().equals(numeroFact)) {
            return "redirect:/facturas";
        }

        double total = factura.getLineas().stream()
                .mapToDouble(LineaFactura::getTotal)
                .sum();
        factura.setTotalFactura(total);
        facturaService.crearFactura(factura);

        List<LineaFactura> lista = lineaService.obtenerLineasPorNumeroFactura(numeroFact);
        repoLinea.saveAll(lista);

        session.removeAttribute("facturaEnProceso");

        return "index";
    }

}
