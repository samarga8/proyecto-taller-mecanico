package com.gestortallermecanico.repository;

import com.gestortallermecanico.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Vehiculo,Long> {
}
