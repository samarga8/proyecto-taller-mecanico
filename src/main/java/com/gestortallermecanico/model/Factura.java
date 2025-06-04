package com.gestortallermecanico.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Factura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String numeroFact;

    @NotNull
    private LocalDate fecha;

    @NotNull
    private Double totalFactura;

    private Boolean pagada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Cliente cliente;

    @OneToMany(mappedBy = "factura",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<LineaFactura> lineas = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(Double totalFactura) {
        this.totalFactura = totalFactura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<LineaFactura> getLineas() {
        return lineas;
    }

    public void setLineas(Set<LineaFactura> lineas) {
        this.lineas = lineas;
    }

    public String getNumeroFact() {
        return numeroFact;
    }

    public void setNumeroFact(String numeroFact) {
        this.numeroFact = numeroFact;
    }

    public Boolean getPagada() {
        return pagada;
    }

    public void setPagada(Boolean pagada) {
        this.pagada = pagada;
    }
}
