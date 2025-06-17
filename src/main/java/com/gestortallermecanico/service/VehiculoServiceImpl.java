package com.gestortallermecanico.service;

import com.gestortallermecanico.model.Cliente;
import com.gestortallermecanico.model.Vehiculo;
import com.gestortallermecanico.model.dao.VehiculoRegistroDTO;
import com.gestortallermecanico.repository.ClienteRepository;
import com.gestortallermecanico.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehiculoServiceImpl implements IVehiculoService{

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Override
    public void guardarVehiculo(VehiculoRegistroDTO dto, String dni) throws Exception {

        Optional<Cliente> cliente = clienteRepository.findFirstByDni(dni);
        if (cliente.isPresent()){
            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setAnio(dto.getAnio());
            vehiculo.setCliente(cliente.get());
            vehiculo.setMarca(dto.getMarca());
            vehiculo.setModelo(dto.getModelo());
            vehiculo.setMatricula(dto.getMatricula());

            vehiculoRepository.save(vehiculo);
        } else {
            throw new Exception("El cliente no existe");
        }
    }

    @Override
    public Vehiculo obtenerVehiculoPorMatricula(String matricula) throws Exception {
        Optional<Vehiculo> vehiculo = vehiculoRepository.findVehiculoByMatricula(matricula);
        if (vehiculo.isPresent()){
            return vehiculo.get();
        } else {
            throw new Exception("El vehiculo no existe");
        }


    }

    @Override
    public Vehiculo obtenerVehiculoPorId(Long vehiculoId) {
        return vehiculoRepository.findById(vehiculoId).get();
    }
}
