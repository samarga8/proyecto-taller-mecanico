package com.gestortallermecanico.service;

import com.gestortallermecanico.model.Cliente;
import com.gestortallermecanico.model.Factura;
import com.gestortallermecanico.model.LineaFactura;
import com.gestortallermecanico.model.dao.LineaFacturaRegistroDTO;

import java.util.List;

public interface ILineaFacturaService {



    List<Factura> listarFacturas(Cliente cliente);

    List<LineaFactura> obtenerLineasPorNumeroFactura(String numFact);
}
