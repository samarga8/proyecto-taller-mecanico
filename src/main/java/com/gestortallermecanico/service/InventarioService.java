package com.gestortallermecanico.service;

import com.gestortallermecanico.model.Inventario;
import com.gestortallermecanico.model.dao.InventarioDTO;

import java.util.List;
import java.util.Set;

public interface InventarioService {
    Inventario agregarProducto(InventarioDTO dto);
    void eliminarProducto(Long id);
    InventarioDTO obtenerProductoPorId(Long id);
    Set<Inventario> listarProductos();
}
