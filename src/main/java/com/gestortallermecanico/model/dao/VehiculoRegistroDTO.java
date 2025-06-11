package com.gestortallermecanico.model.dao;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public class VehiculoRegistroDTO {

    @NotNull
    private String marca;

    @NotNull
    private String modelo;


    @NotNull
    private String matricula;

    @NotNull
    private Integer anio;


    @NotNull(message = "El DNI no puede ser nulo")
    @Pattern(regexp = "\\d{8}[A-HJ-NP-TV-Z]", message = "Formato de DNI inválido")
    private String dni;

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
