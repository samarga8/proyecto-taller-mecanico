package com.gestortallermecanico.model.dao;

import jakarta.validation.constraints.*;

public class LineaFacturaRegistroDTO {


    private String numFact;


    @NotBlank(message = "El concepto es obligatorio")
    @Size(max = 100, message = "El concepto no puede superar los 100 caracteres")
    private String concepto;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser un número positivo")
    private Double precio;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima debe ser 1")
    private Integer cantidad;


    private Double total;


    public String getNumFact() {
        return numFact;
    }

    public void setNumFact(String numFact) {
        this.numFact = numFact;
    }


    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
