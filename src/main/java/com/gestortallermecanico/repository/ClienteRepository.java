package com.gestortallermecanico.repository;

import com.gestortallermecanico.model.Cliente;
import com.gestortallermecanico.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    Optional<Cliente> findFirstByDni(String dni);
    Set<Cliente> findAllByFacturas(Factura factura);

}
