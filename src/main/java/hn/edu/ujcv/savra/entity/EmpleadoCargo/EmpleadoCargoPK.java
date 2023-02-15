package hn.edu.ujcv.savra.entity.EmpleadoCargo;

import hn.edu.ujcv.savra.entity.Empleado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EmpleadoCargoPK implements Serializable {
    private long idEmpleado;
    private Timestamp fechaInicio = null;
}