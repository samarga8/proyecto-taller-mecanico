package com.gestortallermecanico.service;

import com.gestortallermecanico.model.Vehiculo;
import com.gestortallermecanico.model.dao.VehiculoRegistroDTO;

public interface IVehiculoService {

    void guardarVehiculo(VehiculoRegistroDTO dto, String dni) throws Exception;
    Vehiculo obtenerVehiculoPorMatricula(String matricula) throws Exception;

    Vehiculo obtenerVehiculoPorId(Long vehiculoId);
}
