package com.gestortallermecanico.service;

import com.gestortallermecanico.model.*;
import com.gestortallermecanico.model.dao.PiezaUtilizadaDTO;
import com.gestortallermecanico.repository.InventarioRepository;
import com.gestortallermecanico.repository.PiezaUtilizadaRepository;
import com.gestortallermecanico.repository.ReparacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReparacionService {
    @Autowired
    private ReparacionRepository reparacionRepository;

    @Autowired
    private  IVehiculoService vehiculoService;

    @Autowired
    private EmpleadoServiceImpl empleadoService;


    public List<Reparacion> listarReparacionesActivas() {
        return reparacionRepository.findByEstadoNot(Estado.FINALIZADO);
    }

    public List<Reparacion> listarReparacionesFinalizadas(){
        List<Reparacion> canceladas = reparacionRepository.findByEstado(Estado.CANCELADO);
        List<Reparacion> finalizadas = reparacionRepository.findByEstado(Estado.FINALIZADO);
        finalizadas.addAll(canceladas);

        return finalizadas;
    }

    public Optional<Reparacion> obtenerPorId(Long id) {
        return reparacionRepository.findById(id);
    }


    public Reparacion guardar(Reparacion reparacion) {
        return reparacionRepository.save(reparacion);
    }



    public void eliminarReparacion(Long id) {

        reparacionRepository.deleteById(id);
    }
}
