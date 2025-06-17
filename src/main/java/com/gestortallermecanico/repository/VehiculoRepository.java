package com.gestortallermecanico.repository;

import com.gestortallermecanico.model.Cliente;
import com.gestortallermecanico.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<Vehiculo,Long> {

    Vehiculo findByCliente(Cliente cliente);
    Optional<Vehiculo> findVehiculoByMatricula(String matricula);
}
