package com.gestortallermecanico.service;


import com.gestortallermecanico.model.Cita;
import com.gestortallermecanico.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CitaServiceImpl implements ICitaService{

    @Autowired
    private CitaRepository citaRepo;

    @Override
    public List<Cita> obtenerCitasEntreFechas(LocalDate desde, LocalDate hasta) {
        return citaRepo.findAllByFechaBetween(desde, hasta);
    }


    @Override
    public Cita guardarCita(Cita cita) {
        return citaRepo.save(cita);
    }

    @Override
    public List<Cita> obtenerCitasEnFecha(LocalDate fecha) {
        return citaRepo.findByFecha(fecha);
    }

    @Override
    public boolean hayConflictoCita(LocalDate fecha, LocalTime inicio, LocalTime fin) {
        List<Cita> citasEseDia = citaRepo.findByFecha(fecha);

        for (Cita c : citasEseDia) {
            boolean seSolapan = !inicio.isAfter(c.getHoraFin()) && !fin.isBefore(c.getHoraInicio());
            if (seSolapan) {
                return true;
            }
        }

        return false;
    }

}
