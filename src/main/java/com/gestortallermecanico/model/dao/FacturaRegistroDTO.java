package com.gestortallermecanico.model.dao;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class FacturaRegistroDTO {

    @NotBlank
    private String dni;

    @NotBlank
    private String numFactura;


    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }


}
