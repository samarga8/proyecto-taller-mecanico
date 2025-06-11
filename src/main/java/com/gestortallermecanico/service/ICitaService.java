package com.gestortallermecanico.service;

import com.gestortallermecanico.model.Cita;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ICitaService {

    List<Cita> obtenerCitasEntreFechas(LocalDate desde, LocalDate hasta);
    Cita guardarCita(Cita cita);
    List<Cita> obtenerCitasEnFecha(LocalDate fecha);
    boolean hayConflictoCita(LocalDate fecha, LocalTime inicio, LocalTime fin);


}