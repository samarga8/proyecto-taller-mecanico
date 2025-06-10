package com.gestortallermecanico.service;

import com.gestortallermecanico.model.Cliente;
import com.gestortallermecanico.model.Factura;

import java.util.Set;

public interface IFacturaService {

    void crearFactura(Factura factura);
    String crearNumeroFactura();
    Set<Factura> listaFacturasCliente(String dni);
    Factura obtenerFacturaCliente(String dni);

    Factura obtenerFacturaPorNumFact(String numerofact);


    Factura actualizarfactura(Factura factura);

    Set<Factura> listarFacturas();
}
