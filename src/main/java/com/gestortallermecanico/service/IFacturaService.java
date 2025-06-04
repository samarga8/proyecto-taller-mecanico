package com.gestortallermecanico.service;

import com.gestortallermecanico.model.Factura;
import com.gestortallermecanico.model.dao.FacturaRegistroDTO;

public interface IFacturaService {

    Factura crearFactura(String dni) throws Exception;
}
