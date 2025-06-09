package com.gestortallermecanico.service;

import com.gestortallermecanico.model.Cliente;
import com.gestortallermecanico.model.Factura;
import com.gestortallermecanico.model.LineaFactura;
import com.gestortallermecanico.model.dao.LineaFacturaRegistroDTO;
import com.gestortallermecanico.repository.FacturaRepository;
import com.gestortallermecanico.repository.LineaFacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LineaFacturaServiceImpl implements ILineaFacturaService{

    @Autowired
    private FacturaRepository repoFactura;

    @Autowired
    private LineaFacturaRepository repoLinea;


    @Override
    public List<Factura> listarFacturas(Cliente cliente) {
        return repoFactura.findAll().stream().filter(f->f.getCliente().equals(cliente)).toList();
    }

    @Override
    public List<LineaFactura> obtenerLineasPorNumeroFactura(String numFact) {
        return repoLinea.findAll().stream().filter(l->l.getFactura().getNumeroFact().equals(numFact)).toList();

    }
}
