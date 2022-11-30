package hn.edu.ujcv.savra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "repuestoTemporal")

public class RepuestoTemporal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long   idRepuesto;
    private String nombre;
    private int    anio_referenciaInicio;
    private int    anio_referenciaFinal;
    private long   idCategoria;
    private int    stockActual;
    private int    stockMinimo;
    private int    stockMaximo;
    private long   idProveedor;
    private long   idModelo;
    private long   idTransmision;
    private double precio;
}
