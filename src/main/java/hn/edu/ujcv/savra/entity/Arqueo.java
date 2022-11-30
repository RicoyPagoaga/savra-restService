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
@Table(name = "arqueo")
public class Arqueo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idArqueo;
    private LocalDate fecha=null;
    private long idEmpleado;
    private double totalRecuento;
    private String observacion;
}
