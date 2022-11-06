package hn.edu.ujcv.savra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "empleado")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEmpleado;
    private String nombre;
    private String documento;
    private long   idTipoDocumento;
    private Date   fechaNacimiento;
    private String telefono;
    private Date   fechaIngreso;
    private String correo;
    private String direccion;
}

