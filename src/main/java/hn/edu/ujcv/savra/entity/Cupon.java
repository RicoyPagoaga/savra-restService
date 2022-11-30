package hn.edu.ujcv.savra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cupon")
public class Cupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCupon;
    private String codigo;
    private LocalDate fechaEmision = null;
    private LocalDate fechaCaducidad = null;
    private int activo;
    private int porcentajeDescuento ;
}
