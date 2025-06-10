package com.gestortallermecanico.controller;


import com.gestortallermecanico.model.Cliente;
import com.gestortallermecanico.model.Factura;
import com.gestortallermecanico.model.dao.ClienteResponseDTO;
import com.gestortallermecanico.model.dao.FacturaResponseDTO;
import com.gestortallermecanico.model.dao.LineaResponseDTO;
import com.gestortallermecanico.repository.FacturaRepository;
import com.gestortallermecanico.service.IClienteService;
import com.gestortallermecanico.service.IFacturaService;
import com.google.gson.JsonObject;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class StripeStatusController {

    @Autowired
    private FacturaRepository repoFact;

    @Autowired
    private IFacturaService service;

    @Autowired
    private IClienteService serviceCliente;

    @Value("${stripe.key.secret}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    @GetMapping("/session-status")
    public Map<String, String> getSessionStatus(
            @RequestParam("session_id") String sessionId,
            @RequestParam("facturaId") Long facturaId) throws Exception {

        Session session = Session.retrieve(sessionId);
        JsonObject raw = session.getRawJsonObject();
        String estadoPago = raw.getAsJsonPrimitive("status").getAsString();

        Map<String, String> response = new HashMap<>();
        response.put("status", estadoPago);

        if (raw.has("customer_details")) {
            JsonObject cd = raw.getAsJsonObject("customer_details");
            if (cd.has("email")) {
                response.put("customer_email", cd.get("email").getAsString());
            }
        }

        if ("complete".equalsIgnoreCase(estadoPago)) {
            Factura factura = repoFact.findById(facturaId)
                    .orElse(null);
            if (factura != null && !Boolean.TRUE.equals(factura.getPagada())) {
                factura.setPagada(true);
                repoFact.delete(factura);
                service.actualizarfactura(factura);
            }
        }

        return response;
    }

    @GetMapping("/factura/{id}")
    public ResponseEntity<FacturaResponseDTO> getFactura(@PathVariable Long id) {
        Optional<Factura> optionalFactura = repoFact.findById(id);
        if (optionalFactura.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Factura factura = optionalFactura.get();
        FacturaResponseDTO dto = new FacturaResponseDTO();

        dto.setNumero(factura.getNumeroFact());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = factura.getFecha().format(formatter);
        dto.setFecha(formattedDate);

        // Cliente
        Cliente cliente =serviceCliente.obtenerClientePorFactura(dto.getNumero());
        ClienteResponseDTO clienteDTO = new ClienteResponseDTO();

        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setDireccion(cliente.getDireccion());
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setTelefono(cliente.getTelefono());

        dto.setCliente(clienteDTO);

        // Líneas de factura
        List<LineaResponseDTO> lineas = factura.getLineas().stream().map(linea -> {
            LineaResponseDTO lineaDTO = new LineaResponseDTO();
            lineaDTO.setConcepto(linea.getConcepto());
            lineaDTO.setCantidad(linea.getCantidad());
            lineaDTO.setPrecio(linea.getPrecio());

            return lineaDTO;
        }).collect(Collectors.toList());
        dto.setLineas(lineas);

        return ResponseEntity.ok(dto);
    }
}