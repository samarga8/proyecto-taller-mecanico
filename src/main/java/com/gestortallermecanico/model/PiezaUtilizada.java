package com.gestortallermecanico.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.io.Serializable;


@Entity
public class PiezaUtilizada implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Double precioUnitario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "inventario_id")
    private Inventario inventario;

    @Min(1)
    private int cantidadUsada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reparacion_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Reparacion reparacion;




    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Inventario getInventario() { return inventario; }

    public void setInventario(Inventario inventario) { this.inventario = inventario; }

    public Reparacion getReparacion() { return reparacion; }

    public void setReparacion(Reparacion reparacion) { this.reparacion = reparacion; }

    public int getCantidadUsada() { return cantidadUsada; }

    public void setCantidadUsada(int cantidadUsada) { this.cantidadUsada = cantidadUsada; }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
