package hn.edu.ujcv.savra.entity.Repuesto.PrecioHistoricoRepuesto;

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
public class PrecioHistoricoRepuestoPK implements Serializable {
    private long      idRepuesto;
    private LocalDate fechaInicio;
}
