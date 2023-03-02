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
    @ManyToOne
    @JoinColumn(name = "idParametroFactura",referencedColumnName="idParametro")
    private ParametroFactura parametroFactura;
    private String noFactura;
    @ManyToOne
    @JoinColumn(name = "idCliente",referencedColumnName="idCliente")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "idEmpleado",referencedColumnName="idEmpleado")
    private Empleado empleado;
    private Timestamp fechaFactura= null;
    @ManyToOne
    @JoinColumn(name = "idMetodoPago",referencedColumnName="idMetodoPago")
    private MetodoPago metodoPago;
    private double efectivo;
    private String tarjeta = null;
    @ManyToOne
    @JoinColumn(name = "idCupon",referencedColumnName="idCupon")
    private Cupon cupon;
    @ManyToOne
    @JoinColumn(name = "idTipoEntrega",referencedColumnName="idTipoEntrega")
    private TipoEntrega tipoEntrega;
    @ManyToOne
    @JoinColumn(name = "idShipper",referencedColumnName="idShipper")
    private Shipper shipper;
    private double costoEnvio;
    private LocalDate fechaDespacho = null;
    private LocalDate fechaEntrega= null;
}
