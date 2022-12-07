package hn.edu.ujcv.savra.entity.Impuesto.ImpuestoHistorico;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "impuestoHistorico")
@IdClass(ImpuestoHistoricoPK.class)

public class ImpuestoHistorico {
    @Id
    private long      idImpuesto;
    @Id
    private LocalDate fechaInicio;
    private LocalDate fechaFinal;
    private int       valor;
}
