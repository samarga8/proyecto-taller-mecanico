package com.gestortallermecanico.service;

import com.gestortallermecanico.model.Empleado;
import com.gestortallermecanico.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoServiceImpl {

    @Autowired
    private EmpleadoRepository repoEmpleado;

    public List<Empleado> listarEmpleados(){
        return repoEmpleado.findAll();
    }
    public Empleado obtenerEmpleadoPorId(long id){
        return repoEmpleado.findById(id).get();
    }

}
