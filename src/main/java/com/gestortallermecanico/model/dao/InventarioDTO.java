package com.gestortallermecanico.model.dao;

import jakarta.validation.constraints.*;

public class InventarioDTO {

    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100)
    private String nombre;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private int cantidad;

    @DecimalMin(value = "0.0", inclusive = true, message = "El precio debe ser mayor o igual a 0")
    private double precio;

    @Size(max = 300)
    private String descripcion;

    // Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
