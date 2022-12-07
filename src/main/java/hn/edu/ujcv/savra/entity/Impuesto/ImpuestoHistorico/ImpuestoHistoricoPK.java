package hn.edu.ujcv.savra.entity.Impuesto.ImpuestoHistorico;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ImpuestoHistoricoPK implements Serializable {
    private long      idImpuesto;
    private LocalDate fechaInicio;
}
