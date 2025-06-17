package com.gestortallermecanico.controller;

import com.gestortallermecanico.model.Inventario;
import com.gestortallermecanico.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping("/")
    public String index(Model model) {
        Set<Inventario> productos = inventarioService.listarProductos();

        // Contamos cuántos productos tienen stock bajo
        long productosConStockBajo = productos.stream()
                .filter(p -> p.getCantidad() <= p.getStockMinimo())
                .count();

        model.addAttribute("listaProductos", productos);
        model.addAttribute("hayStockMinimo", productosConStockBajo > 0);
        model.addAttribute("cantidadStockBajo", productosConStockBajo);
        return "index";
    }

}