package com.gestortallermecanico.service;

import com.gestortallermecanico.model.Cliente;
import com.gestortallermecanico.model.Factura;
import com.gestortallermecanico.model.LineaFactura;
import com.gestortallermecanico.model.dao.FacturaRegistroDTO;
import com.gestortallermecanico.model.dao.LineaFacturaRegistroDTO;

import java.util.List;

public interface ILineaFacturaService {

    LineaFactura crearLineaFactura(LineaFacturaRegistroDTO dto);

    List<Factura> listarFacturas(Cliente cliente);


}
