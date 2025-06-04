package com.gestortallermecanico.repository;

import com.gestortallermecanico.model.Cliente;
import com.gestortallermecanico.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface FacturaRepository extends JpaRepository<Factura,Long> {

    Optional<Factura> findFacturaByCliente(Cliente cliente);
    long countByFechaBetween(LocalDate start, LocalDate end);
}
