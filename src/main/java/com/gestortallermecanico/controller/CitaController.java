package com.gestortallermecanico.controller;


import com.gestortallermecanico.model.Cita;
import com.gestortallermecanico.model.Cliente;
import com.gestortallermecanico.model.Vehiculo;
import com.gestortallermecanico.model.dao.CitaDTO;
import com.gestortallermecanico.repository.ClienteRepository;
import com.gestortallermecanico.repository.VehiculoRepository;
import com.gestortallermecanico.service.ICitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private ICitaService citaService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;


    @GetMapping
    public List<Map<String, Object>> obtenerCitas(@RequestParam String start, @RequestParam String end) {
        try {
            LocalDate startDate = OffsetDateTime.parse(start).toLocalDate();
            LocalDate endDate = OffsetDateTime.parse(end).toLocalDate();

            List<Cita> citas = citaService.obtenerCitasEntreFechas(startDate, endDate);

            return citas.stream().map(cita -> {
                Map<String, Object> event = new HashMap<>();

                String nombreCliente = cita.getCliente().getNombre();
                String datosVehiculo = cita.getVehiculo().getMarca() + " " + cita.getVehiculo().getModelo();

                event.put("title", nombreCliente + " - " + datosVehiculo);
                event.put("start", cita.getFecha() + "T" + cita.getHoraInicio());
                event.put("end", cita.getFecha() + "T" + cita.getHoraFin());
                event.put("backgroundColor", "#ff9999"); // rojo claro
                event.put("display", "background");
                event.put("overlap", false);

                event.put("clienteId", cita.getCliente().getId());
                event.put("vehiculoId", cita.getVehiculo().getId());

                return event;
            }).collect(Collectors.toList());

        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de fecha inválido: " + e.getMessage());
        }
    }



    @PostMapping
    public ResponseEntity<?> crearCita(@RequestBody CitaDTO dto) {
        try {
           // Recibe campos ISO: "start": "2025-06-12T09:00", "end": "2025-06-12T10:00"
            LocalDateTime start = LocalDateTime.parse(dto.getStart());
            LocalDateTime end = LocalDateTime.parse(dto.getEnd());

            LocalDate fecha = start.toLocalDate();
            LocalTime horaInicio = start.toLocalTime();
            LocalTime horaFin = end.toLocalTime();

            DayOfWeek dia = fecha.getDayOfWeek();

            if (dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY) {
                return ResponseEntity.badRequest().body("Las citas solo se pueden crear de lunes a viernes.");
            }

            boolean enHorarioManana = !horaInicio.isBefore(LocalTime.of(9, 0)) && !horaFin.isAfter(LocalTime.of(14, 0));
            boolean enHorarioTarde  = !horaInicio.isBefore(LocalTime.of(16, 0)) && !horaFin.isAfter(LocalTime.of(19, 30));

            if (!enHorarioManana && !enHorarioTarde) {
                return ResponseEntity.badRequest().body("La hora no está dentro del horario laboral.");
            }

            if (!horaInicio.isBefore(horaFin)) {
                return ResponseEntity.badRequest().body("La hora de inicio debe ser anterior a la de fin.");
            }

            boolean hayConflicto = citaService.hayConflictoCita(fecha, horaInicio, horaFin);
            if (hayConflicto) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe una cita en ese horario.");
            }

            Cita cita = new Cita();
            cita.setFecha(fecha);
            cita.setHoraInicio(horaInicio);
            cita.setHoraFin(horaFin);

            Cliente cliente = clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            Vehiculo vehiculo = vehiculoRepository.findById(dto.getVehiculoId())
                    .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

            cita.setCliente(cliente);
            cita.setVehiculo(vehiculo);

            Cita citaGuardada = citaService.guardarCita(cita);
            return ResponseEntity.ok(citaGuardada);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear la cita: " + e.getMessage());
        }
    }
}

