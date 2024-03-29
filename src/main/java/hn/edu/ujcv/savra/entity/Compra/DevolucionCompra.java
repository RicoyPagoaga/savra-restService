package hn.edu.ujcv.savra.entity.Compra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "devolucionCompra")
public class DevolucionCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long      idDevolucion;
    @ManyToOne
    @JoinColumn(name = "idCompraDetalle", referencedColumnName = "idCompraDetalle")
    private CompraDetalle compraDetalle;
    private LocalDate     fecha;
    private int           cantidad;
    private String        motivo;
}
