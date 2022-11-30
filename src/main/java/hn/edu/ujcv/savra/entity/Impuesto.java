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
@Table(name = "impuesto")
public class Impuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long      idImpuesto;
    private String    nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFinal;
    private double    valor;
}
