package com.gestortallermecanico.repository;

import com.gestortallermecanico.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    Optional<Cliente> findFirstByDni(String dni);
}
