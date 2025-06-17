package com.gestortallermecanico.controller;

import com.gestortallermecanico.model.*;
import com.gestortallermecanico.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/reparaciones")
public class ReparacionController {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private ReparacionService reparacionService;

    @Autowired
    private IFacturaService facturaService;

    @Autowired
    private IVehiculoService vehiculoService;

    @Autowired
    private EmpleadoServiceImpl empleadoService;

    @GetMapping("/nuevaReparacion")
    public String mostrarFormularioNuevaReparacion(@RequestParam Long vehiculoId, Model model) {

        Vehiculo vehiculo = vehiculoService.obtenerVehiculoPorId(vehiculoId);
        if (vehiculo == null) {
            // Redirigir con mensaje de error si no existe vehículo
            model.addAttribute("error", "Vehículo no encontrado.");
            return "redirect:/vehiculos/obtenerVehiculo";  // o la página que tengas para listar vehículos
        }

        String formatoSimple = horaActual();

        // Crear nueva reparación vacía y setear el vehículo
        Reparacion reparacion = new Reparacion();
        reparacion.setVehiculo(vehiculo);
        reparacion.setEstado(Estado.PENDIENTE);
        reparacion.setFechaInicio(LocalDateTime.now());
        List<Empleado> empleados = empleadoService.listarEmpleados();

        // Pasar datos al modelo
        model.addAttribute("formatoSimple",formatoSimple);
        model.addAttribute("reparacion", reparacion);
        model.addAttribute("empleados", empleados);

        return "formularioReparacion";
    }

    @PostMapping("/guardar")
    public String guardarReparacion(
            @ModelAttribute Reparacion reparacion,
            @RequestParam("horaInicio") String horaInicioStr,
            RedirectAttributes redirectAttributes,
            Model model) {

        Vehiculo vehiculo = vehiculoService.obtenerVehiculoPorId(reparacion.getVehiculo().getId());
        reparacion.setVehiculo(vehiculo);
        try {

            LocalTime hora = LocalTime.parse(horaInicioStr);

            reparacion.setFechaInicio(LocalDateTime.of(LocalDate.now(), hora));

            reparacionService.guardar(reparacion);

            redirectAttributes.addFlashAttribute("success", "Reparación guardada correctamente.");
            return "redirect:/reparaciones/lista";

        } catch (Exception e) {
            Vehiculo vehiculoCompleto = vehiculoService.obtenerVehiculoPorId(reparacion.getVehiculo().getId());
            reparacion.setVehiculo(vehiculoCompleto);

            List<Empleado> empleados = empleadoService.listarEmpleados();

            model.addAttribute("reparacion", reparacion);
            model.addAttribute("empleados", empleados);
            model.addAttribute("formatoSimple", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            model.addAttribute("error", "Error al guardar la reparación.");

            return "formularioReparacion";
        }

    }

    @GetMapping("/lista")
    public String mostrarLista(Model model){
        List<Reparacion> reparaciones = reparacionService.listarReparacionesActivas();

        model.addAttribute("reparaciones",reparaciones);

        return "listaReparaciones";
    }

    @GetMapping("/listaFinalizada")
    public String mostrarListaFinalizada(Model model){
        List<Reparacion> reparaciones = reparacionService.listarReparacionesFinalizadas();

        model.addAttribute("reparaciones",reparaciones);
        return "listaReparacionesFinalizadas";
    }

    @GetMapping("/detalle/{id}")
    public String verDetalles(@PathVariable long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Reparacion> reparacionOpt = reparacionService.obtenerPorId(id);
        if (reparacionOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Reparación no encontrada.");
            return "redirect:/reparaciones/lista";
        }

        Reparacion reparacion = reparacionOpt.get();
        if (reparacion.getEstado().equals(Estado.PENDIENTE)){
            reparacion.setEstado(Estado.EN_CURSO);
        }

        LocalDateTime fechaActual = LocalDateTime.now();
        Duration duracion = Duration.between(reparacion.getFechaInicio().toLocalDate().atStartOfDay(),LocalDate.now().atStartOfDay());
        long horas = duracion.toHours();
        long minutos = duracion.toMinutes() % 60;
        reparacion.setDuracion(duracion);
        String tiempo = horas + ":" + minutos;

        Set<Inventario> listaPiezas = inventarioService.listarProductos();

       // model.addAttribute("estadoFinalizado", Estado.FINALIZADO.name());
        model.addAttribute("listaPiezas",listaPiezas);
        model.addAttribute("fechaActual",fechaActual);
        model.addAttribute("tiempo",tiempo);
        model.addAttribute("reparacion", reparacion);
        model.addAttribute("piezaUtilizada", new PiezaUtilizada());
        return "detalleReparacion";
    }



    @PostMapping("/finalizar")
    public String finalizarReparacion(@RequestParam Long reparacionId,
                                      RedirectAttributes redirectAttributes) {

        Reparacion reparacion = reparacionService.obtenerPorId(reparacionId)
                .orElseThrow(() -> new RuntimeException("Reparación no encontrada"));

        if (reparacion.getEstado() == Estado.FINALIZADO) {
            redirectAttributes.addFlashAttribute("error", "La reparación ya fue finalizada.");
            return "redirect:/reparaciones/detalle/" + reparacionId;
        }

        reparacion.setEstado(Estado.FINALIZADO);
        reparacion.setFechaFin(LocalDateTime.now());

        // Crear la factura
        Factura factura = new Factura();
        factura.setNumeroFact(facturaService.crearNumeroFactura());
        factura.setFecha(LocalDate.now());
        factura.setCliente(reparacion.getVehiculo().getCliente());
        factura.setReparacion(reparacion);
        factura.setPagada(false);

        Set<LineaFactura> lineas = new HashSet<>();
        double total = 0.0;

        for (PiezaUtilizada pieza : reparacion.getPiezasUtilizadas()) {
            LineaFactura linea = new LineaFactura();
            linea.setConcepto(pieza.getNombre());
            linea.setCantidad(pieza.getCantidadUsada());
            linea.setPrecio(pieza.getInventario().getPrecio()); // Ajusta esto según tu modelo
            double subtotal = round(pieza.getCantidadUsada() * pieza.getInventario().getPrecio(), 2);
            linea.setTotal(subtotal);

            linea.setFactura(factura);
            lineas.add(linea);
            total += subtotal;
        }

        factura.setLineas(lineas);

        double iva = total * 0.21;
        factura.setTotalFactura(total);
        factura.setIva(iva);
        factura.setTotalFacturaDefinitivo(total + iva);

        reparacion.setFactura(factura);

        facturaService.crearFactura(factura);
        redirectAttributes.addFlashAttribute("success", "Reparación finalizada y factura generada.");

        return "redirect:/index" ;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }



    private String horaActual(){
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        return fechaHoraActual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
