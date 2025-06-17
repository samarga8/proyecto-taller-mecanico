package com.gestortallermecanico.service;

import com.gestortallermecanico.model.Inventario;
import com.gestortallermecanico.model.dao.InventarioDTO;
import com.gestortallermecanico.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InventarioServiceImpl implements InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;


    @Override
    public InventarioDTO obtenerProductoPorId(Long id) {
        Optional<Inventario> opt = inventarioRepository.findById(id);
        if (opt.isPresent()) {
            Inventario p = opt.get();
            InventarioDTO dto = new InventarioDTO();
            dto.setId(p.getId());
            dto.setNombre(p.getNombre());
            dto.setCategoria(p.getCategoria());
            dto.setCantidad(p.getCantidad());
            dto.setPrecio(p.getPrecio());
            dto.setDescripcion(p.getDescripcion());
            return dto;
        }
        return null;
    }

    @Override
    public Set<Inventario> listarProductos() {
        return new HashSet<>(inventarioRepository.findAll());
    }

    public Inventario agregarProducto(InventarioDTO dto) {
        Inventario p;
        if (dto.getId() != null) {
            p = inventarioRepository.findById(dto.getId()).orElse(new Inventario());
        } else {
            p = new Inventario();
        }
        p.setNombre(dto.getNombre());
        p.setCategoria(dto.getCategoria());
        p.setCantidad(dto.getCantidad());
        p.setPrecio(dto.getPrecio());
        p.setDescripcion(dto.getDescripcion());
        return inventarioRepository.save(p);
    }

    public void eliminarProducto(Long id) {
        inventarioRepository.deleteById(id);
    }

}
