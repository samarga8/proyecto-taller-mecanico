package com.gestortallermecanico.model.dao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ClienteRegistroDTO {
    @NotNull(message = "El DNI no puede ser nulo")
    @Pattern(regexp = "\\d{8}[A-HJ-NP-TV-Z]", message = "Formato de DNI inválido")
    private String dni;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Pattern(regexp = "[A-Za-zÁÉÍÓÚÑáéíóúñ\\s'-]+", message = "El nombre solo puede contener letras y espacios")
    private String nombre;

    @NotBlank(message = "Los apellidos no pueden estar vacíos")
    @Size(min = 2, max = 100, message = "Los apellidos deben tener entre 2 y 100 caracteres")
    @Pattern(regexp = "[A-Za-zÁÉÍÓÚÑáéíóúñ\\s'-]+", message = "Los apellidos solo pueden contener letras y espacios")
    private String apellidos;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Pattern(regexp = "\\d{9}", message = "El teléfono debe tener exactamente 9 dígitos")
    private String telefono;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}

