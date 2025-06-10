package com.gestortallermecanico.repository;

import com.gestortallermecanico.model.Cliente;
import com.gestortallermecanico.model.Factura;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface FacturaRepository extends JpaRepository<Factura,Long> {


    long countByFechaBetween(LocalDate start, LocalDate end);

    Factura findByNumeroFact(String numFact);

    @EntityGraph(attributePaths = {"cliente", "lineas"})
    Optional<Factura> findWithClienteAndLineasById(Long id);
}
