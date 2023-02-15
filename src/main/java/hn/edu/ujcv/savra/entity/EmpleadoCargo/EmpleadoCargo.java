package hn.edu.ujcv.savra.entity.EmpleadoCargo;


import hn.edu.ujcv.savra.entity.Cargo;
import hn.edu.ujcv.savra.entity.Empleado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "empleado_cargo")
@IdClass(EmpleadoCargoPK.class)

public class EmpleadoCargo {
    @Id
    private Timestamp fechaInicio = null;
    @Id
    private long idEmpleado;
    private LocalDate fechaFinal = null;
    private long idCargo;
}
