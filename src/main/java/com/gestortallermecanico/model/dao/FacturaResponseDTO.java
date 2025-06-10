package com.gestortallermecanico.model.dao;

import java.util.List;

public class FacturaResponseDTO {

    private String numero;
    private String fecha;
    private ClienteResponseDTO cliente;
    private List<LineaResponseDTO> lineas;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public ClienteResponseDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteResponseDTO cliente) {
        this.cliente = cliente;
    }

    public List<LineaResponseDTO> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaResponseDTO> lineas) {
        this.lineas = lineas;
    }
}
