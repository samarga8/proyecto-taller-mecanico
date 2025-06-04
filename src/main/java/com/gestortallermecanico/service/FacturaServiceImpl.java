package com.gestortallermecanico.service;

import com.gestortallermecanico.model.Cliente;
import com.gestortallermecanico.model.Factura;
import com.gestortallermecanico.model.dao.FacturaRegistroDTO;
import com.gestortallermecanico.repository.ClienteRepository;
import com.gestortallermecanico.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class FacturaServiceImpl implements IFacturaService{

    @Autowired
    private ClienteRepository repoCliente;

    @Autowired
    private FacturaRepository repoFactura;

    @Override
    public Factura crearFactura(String dni) throws Exception {
        Optional<Cliente> cliente = repoCliente.findFirstByDni(dni);

       if (cliente.isPresent()){
           LocalDate hoy = LocalDate.now();
           int year = hoy.getYear();

           LocalDate startOfYear = LocalDate.of(year, 1, 1);
           LocalDate endOfYear = LocalDate.of(year, 12, 31);
           long count = repoFactura.countByFechaBetween(startOfYear, endOfYear);
           long siguienteNumero = count + 1;

           String numeroFact = String.format("FAC-%d%03d", year, siguienteNumero);

           Factura factura = new Factura();
           factura.setFecha(hoy);
           factura.setCliente(cliente.get());
           factura.setNumeroFact(numeroFact);
           factura.setTotalFactura(0.0);
           factura.setPagada(false);

           return repoFactura.save(factura);
       }else{
           throw new Exception("El cliente no existe");
       }

    }
}
