package hn.edu.ujcv.savra.entity.Repuesto;

import hn.edu.ujcv.savra.entity.CategoriaRepuesto;
import hn.edu.ujcv.savra.entity.Impuesto.Impuesto;
import hn.edu.ujcv.savra.entity.Modelo;
import hn.edu.ujcv.savra.entity.Proveedor;
import hn.edu.ujcv.savra.entity.Transmision;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "repuesto")

public class Repuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long   idRepuesto;
    private String nombre;
    private int    anio_referenciaInicio;
    private int    anio_referenciaFinal;
    @ManyToOne
    @JoinColumn(name = "idCategoria", referencedColumnName = "idCategoria")
    private CategoriaRepuesto categoria;
    private int    stockActual;
    private int    stockMinimo;
    private int    stockMaximo;
    @ManyToOne
    @JoinColumn(name = "idProveedor", referencedColumnName = "idProveedor")
    private Proveedor proveedor;
    @ManyToOne
    @JoinColumn(name = "idModelo", referencedColumnName = "idModelo")
    private Modelo modelo;
    @ManyToOne
    @JoinColumn(name = "idTransmision", referencedColumnName = "idTransmision")
    private Transmision transmision;
    @ManyToOne
    @JoinColumn(name = "idImpuesto", referencedColumnName = "idImpuesto")
    private Impuesto impuesto;
}
