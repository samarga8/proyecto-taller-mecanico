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

    @Version
    private Long version;


    @Column(unique = true)
    private String numeroFact;

    @NotNull
    private LocalDate fecha;


    private Double totalFactura;

    private Double iva;

    private Double totalFacturaDefinitivo;

    private Boolean pagada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Cliente cliente;

    @OneToMany(mappedBy = "factura",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<LineaFactura> lineas = new HashSet<>();

    public Factura() {
    }

    public Factura(Long id, String numeroFact, LocalDate fecha, Double totalFactura, Boolean pagada, Cliente cliente) {
        this.id = id;
        this.numeroFact = numeroFact;
        this.fecha = fecha;
        this.totalFactura = totalFactura;
        this.pagada = pagada;
        this.cliente = cliente;
    }

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

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public Double getTotalFacturaDefinitivo() {
        return totalFacturaDefinitivo;
    }

    public void setTotalFacturaDefinitivo(Double totalFacturaDefinitivo) {
        this.totalFacturaDefinitivo = totalFacturaDefinitivo;
    }
}
