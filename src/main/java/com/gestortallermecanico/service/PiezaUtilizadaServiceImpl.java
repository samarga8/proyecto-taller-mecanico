package com.gestortallermecanico.service;

import com.gestortallermecanico.model.Estado;
import com.gestortallermecanico.model.Inventario;
import com.gestortallermecanico.model.PiezaUtilizada;
import com.gestortallermecanico.model.Reparacion;
import com.gestortallermecanico.model.dao.PiezaUtilizadaDTO;
import com.gestortallermecanico.repository.InventarioRepository;
import com.gestortallermecanico.repository.PiezaUtilizadaRepository;
import com.gestortallermecanico.repository.ReparacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PiezaUtilizadaServiceImpl {

    @Autowired
    private PiezaUtilizadaRepository piezaUtilizadaRepo;

    @Autowired
    private InventarioRepository inventarioRepo;


    public void guardar(PiezaUtilizada pieza) {
        Inventario inventario = inventarioRepo.findById(pieza.getInventario().getId())
                .orElseThrow(() -> new IllegalArgumentException("Pieza de inventario no encontrada"));

        // Validar stock suficiente
        if (inventario.getCantidad() < pieza.getCantidadUsada()) {
            throw new IllegalArgumentException("Stock insuficiente para la pieza: " + inventario.getNombre());
        }

        // Actualizar stock en inventario
        inventario.setCantidad(inventario.getCantidad() - pieza.getCantidadUsada());
        inventarioRepo.save(inventario);

        // Asociar inventario y nombre a la pieza utilizada
        pieza.setInventario(inventario);
        pieza.setNombre(inventario.getNombre());

        // Guardar pieza utilizada
        piezaUtilizadaRepo.save(pieza);


    }
}