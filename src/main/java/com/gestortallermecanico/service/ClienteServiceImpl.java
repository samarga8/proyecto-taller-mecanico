package com.gestortallermecanico.service;

import com.gestortallermecanico.model.Cliente;
import com.gestortallermecanico.model.Factura;
import com.gestortallermecanico.model.dao.ClienteRegistroDTO;
import com.gestortallermecanico.repository.ClienteRepository;
import com.gestortallermecanico.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private ClienteRepository repoCliente;

    @Autowired
    private FacturaRepository repofactura;

    @Override
    public Cliente registrarCliente(ClienteRegistroDTO dto) throws Exception {
        Optional<Cliente> cliente = repoCliente.findFirstByDni(dto.getDni());
        if (cliente.isPresent()) {
            throw new Exception("El cliente ya existe");
        } else {
            Cliente nuevoCliente = new Cliente();
            nuevoCliente.setDni(dto.getDni());
            nuevoCliente.setNombre(dto.getNombre());
            nuevoCliente.setApellidos(dto.getApellidos());
            nuevoCliente.setTelefono(dto.getTelefono());
            nuevoCliente.setEmail(dto.getEmail());
            nuevoCliente.setDireccion(dto.getDireccion());
            return repoCliente.save(nuevoCliente);
        }
    }

    @Override
    public List<Cliente> listarClientes() {
        return repoCliente.findAll().stream().sorted(Comparator.comparing(Cliente::getNombre)).toList();
    }

    @Override
    public Cliente obtenerCliente(String dni) throws Exception {
        Optional<Cliente> cliente = repoCliente.findFirstByDni(dni);
        if (cliente.isPresent()) {
            return cliente.get();
        } else {
            throw new Exception("El cliente no existe");
        }
    }

    @Override
    public Cliente obtenerClientePorFactura(String numFact) {
        List<Factura> facturas = repofactura.findAll().stream().filter(f->f.getNumeroFact().equals(numFact)).toList();
        return facturas.get(0).getCliente();
    }


    @Override
    public Cliente actualizarCliente(Cliente cliente) {
        return repoCliente.save(cliente);
    }

    @Override
    public void eliminarCliente(Long id) {
        repoCliente.deleteById(id);
    }

    @Override
    public Cliente findById(long id) {
        return repoCliente.findById(id).get();

    }
}
