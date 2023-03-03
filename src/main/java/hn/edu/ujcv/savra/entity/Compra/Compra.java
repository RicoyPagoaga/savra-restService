package hn.edu.ujcv.savra.entity.Compra;

import hn.edu.ujcv.savra.entity.Empleado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "compra")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long      idCompra;
    @ManyToOne
    @JoinColumn(name = "idEmpleado", referencedColumnName = "idEmpleado")
    private Empleado  empleado;
    private LocalDate fechaCompra;
    private LocalDate fechaDespacho;
    private LocalDate fechaRecibido;
    private String    noComprobante;
}
