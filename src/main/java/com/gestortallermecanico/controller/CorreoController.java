package com.gestortallermecanico.controller;

import com.gestortallermecanico.model.dao.EmailFacturaDTO;
import com.gestortallermecanico.service.CorreoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CorreoController {

    @Autowired
    private CorreoServiceImpl correoService;

    @PostMapping("/enviar-factura")
    public ResponseEntity<Void> enviarFacturaPorEmail(@RequestBody EmailFacturaDTO dto) {
        correoService.enviarCorreo(dto.getEmail(), dto.getHtml());
        return ResponseEntity.ok().build();
    }
}
