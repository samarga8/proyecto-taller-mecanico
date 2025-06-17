package com.gestortallermecanico.controller;

import com.gestortallermecanico.model.Inventario;
import com.gestortallermecanico.model.PiezaUtilizada;
import com.gestortallermecanico.model.dao.InventarioDTO;
import com.gestortallermecanico.service.InventarioService;
import com.gestortallermecanico.service.PiezaUtilizadaServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/inventario")
public class InventarioController {


    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private PiezaUtilizadaServiceImpl piezaUtilizadaService;

    @GetMapping("/nuevoProducto")
    public String formularioNuevoProducto(Model model){
        model.addAttribute("producto", new InventarioDTO());
        return "agregarProductoInventario";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@Valid @ModelAttribute("producto") InventarioDTO producto,
                                  BindingResult result,
                                  Model model) {
        if (result.hasErrors()) {
            return "inventario/nuevo";
        }

        inventarioService.agregarProducto(producto);
        return "redirect:/inventario/nuevoProducto";
    }

    @GetMapping("/lista")
    public String listarProductos(Model model) {
        Set<Inventario> lista = inventarioService.listarProductos();
        model.addAttribute("listaProductos", lista);
        return "listaProductos";
    }


    @GetMapping("/editar/{id}")
    public String mostrarEditarProducto(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        InventarioDTO productoDTO = inventarioService.obtenerProductoPorId(id);
        if (productoDTO == null) {
            redirectAttributes.addFlashAttribute("error", "Producto no encontrado.");
            return "redirect:/inventario/lista";
        }
        model.addAttribute("producto", productoDTO);
        return "agregarProductoInventario";
    }

    // Eliminar producto
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        InventarioDTO producto = inventarioService.obtenerProductoPorId(id);
        if (producto == null) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar. Producto no encontrado.");
            return "redirect:/inventario/lista";
        }

        inventarioService.eliminarProducto(id);
        redirectAttributes.addFlashAttribute("success", "Producto eliminado correctamente.");
        return "redirect:/inventario/lista";
    }

    @PostMapping("/piezas/agregar")
    public String agregarPieza(@ModelAttribute PiezaUtilizada piezaUtilizada, RedirectAttributes redirectAttributes) {
        try {
            piezaUtilizadaService.guardar(piezaUtilizada);
            redirectAttributes.addFlashAttribute("success", "Pieza añadida correctamente.");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/reparaciones/detalle/" + piezaUtilizada.getReparacion().getId();
    }



}
