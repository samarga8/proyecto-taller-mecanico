package com.gestortallermecanico.service;

import com.gestortallermecanico.model.Cliente;
import com.gestortallermecanico.model.dao.ClienteRegistroDTO;

import java.util.List;

public interface IClienteService {

    Cliente registrarCliente(ClienteRegistroDTO dto) throws Exception;
    List<Cliente> listarClientes();
    Cliente obtenerCliente(String dni) throws Exception;

    Cliente obtenerClientePorFactura(String numFact);

    Cliente actualizarCliente(Cliente cliente);

    void eliminarCliente(Long id);

    Cliente findById(long id);
}
