package com.gestortallermecanico.service;

import com.gestortallermecanico.model.Cliente;
import com.gestortallermecanico.model.dao.ClienteRegistroDTO;
import com.gestortallermecanico.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements IClienteService{

    @Autowired
    private ClienteRepository repoCliente;

    @Override
    public Cliente registrarCliente(ClienteRegistroDTO dto) throws Exception {
        Optional<Cliente> cliente = repoCliente.findFirstByDni(dto.getDni());
        if (cliente.isPresent()){
            throw new Exception("El cliente ya existe");
        }else{
            Cliente nuevoCliente = new Cliente();
            nuevoCliente.setDni(dto.getDni());
            nuevoCliente.setNombre(dto.getNombre());
            nuevoCliente.setApellidos(dto.getApellidos());
            nuevoCliente.setTelefono(dto.getTelefono());

            return repoCliente.save(nuevoCliente);
        }
    }

    @Override
    public List<Cliente> listarClientes() {
        return repoCliente.findAll();
    }

    @Override
    public Cliente obtenerCliente(String dni) throws Exception {
        Optional<Cliente> cliente = repoCliente.findFirstByDni(dni);
        if (cliente.isPresent()){
            return cliente.get();
        } else {
            throw new Exception("El cliente no existe");
        }
    }

    @Override
    public Cliente actualizarCliente(Cliente cliente) {
        return null;
    }

    @Override
    public void eliminarCliente(Long id) {

    }
}
