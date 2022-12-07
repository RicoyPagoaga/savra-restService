package hn.edu.ujcv.savra.entity.Compra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "compradetalle")
public class CompraDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long   idCompraDetalle;
    private long   idCompra;
    private long   idRepuesto;
    private int    cantidad;
}
