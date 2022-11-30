package hn.edu.ujcv.savra.entity.PrecioHistoricoRepuesto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "precioHistoricoRepuesto")
@IdClass(PrecioHistoricoRepuestoPK.class)

public class PrecioHistoricoRepuesto {
    @Id
    private long      idRepuesto;
    @Id
    private LocalDate fechaInicio;
    private LocalDate fechaFinal;
    private double    precio;
}
