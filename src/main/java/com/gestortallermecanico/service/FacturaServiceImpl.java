package com.gestortallermecanico.service;

import com.gestortallermecanico.model.Cliente;
import com.gestortallermecanico.model.Factura;
import com.gestortallermecanico.model.LineaFactura;
import com.gestortallermecanico.repository.ClienteRepository;
import com.gestortallermecanico.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class FacturaServiceImpl implements IFacturaService{

    @Autowired
    private ClienteRepository repoCliente;

    @Autowired
    private FacturaRepository repoFactura;

    @Override
    public void crearFactura(Factura factura) {
        repoFactura.save(factura);

    }

    @Override
    public String crearNumeroFactura() {
        LocalDate hoy = LocalDate.now();
        int year = hoy.getYear();

        LocalDate startOfYear = LocalDate.of(year, 1, 1);
        LocalDate endOfYear = LocalDate.of(year, 12, 31);
        long count = repoFactura.countByFechaBetween(startOfYear, endOfYear);
        long siguienteNumero = count + 1;

        return String.format("FAC-%d%03d", year, siguienteNumero);
    }

    @Override
    public Set<Factura> listaFacturasCliente(String dni) {
        Cliente cliente = repoCliente.findFirstByDni(dni).get();
        return cliente.getFacturas();
    }

    @Override
    public Factura obtenerFacturaCliente(String dni) {
        return null;
    }

    @Override
    public Factura obtenerFacturaPorNumFact(String numerofact) {
        return repoFactura.findByNumeroFact(numerofact);
    }

    @Override
    public Double calcularTotalfactura(String numFact) {
        Factura factura = repoFactura.findByNumeroFact(numFact);
        return  factura.getLineas().stream().mapToDouble(LineaFactura::getTotal).sum();
    }

    @Override
    public Factura actualizarfactura(Factura factura) {
        return repoFactura.save(factura);
    }


}
