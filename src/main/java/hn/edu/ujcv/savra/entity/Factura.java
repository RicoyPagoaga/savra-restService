package hn.edu.ujcv.savra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "factura")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idFactura;
    private long idParametroFactura;
    private long idCliente;
    private long idEmpleado;
    private LocalDate fechaFactura;
    private long idMetodoPago;
    private long idImpuesto;
    private long idTipoEntrega;
    private long idShipper;
    private LocalDate fechaDespacho;
    private LocalDate fechaEntrega;
    private long idCupon;
}
