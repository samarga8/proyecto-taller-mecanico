package com.gestortallermecanico.repository;

import com.gestortallermecanico.model.Estado;
import com.gestortallermecanico.model.Reparacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReparacionRepository extends JpaRepository<Reparacion,Long> {

    // En ReparacionRepository
    List<Reparacion> findByEstadoNot(Estado estado);
    List<Reparacion> findByEstado(Estado estado);

}
