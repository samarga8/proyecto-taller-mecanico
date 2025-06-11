package com.gestortallermecanico.repository;

import com.gestortallermecanico.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita,Long> {
    List<Cita> findAllByFechaBetween(LocalDate start, LocalDate end);
    List<Cita> findByFecha(LocalDate fecha);


}
