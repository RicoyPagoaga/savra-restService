package hn.edu.ujcv.savra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "factura")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idFactura;
    private long idParametroFactura;
    private String noFactura;
    private long idCliente;
    private long idEmpleado;
    private Timestamp fechaFactura= null;
    private long idMetodoPago;
    private double efectivo;
    private String tarjeta = null;
    private Long idCupon;
    private long idTipoEntrega;
    private Long idShipper;
    private double costoEnvio;
    private LocalDate fechaDespacho = null;
    private LocalDate fechaEntrega= null;
}
