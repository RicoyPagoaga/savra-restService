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
@Table(name = "compra")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long      idCompra;
    private long      idEmpleado;
    private LocalDate fechaCompra;
    private LocalDate fechaDespacho;
    private LocalDate fechaRecibido;
    private String    noComprobante;
}
