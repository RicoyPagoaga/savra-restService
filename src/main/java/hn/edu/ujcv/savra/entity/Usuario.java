package hn.edu.ujcv.savra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long   idUsuario;
    private String username;
    private String password;
    private String nombre;
    private String apellido;
    private int    activo;
    private int    bloqueado;
    @ManyToOne
    @JoinColumn(name = "idRol",referencedColumnName="idRol")
    private Rol   rol;
    private Integer    clientesVista;
    private Timestamp ultimaVisita;
    private Integer    ventasVista;
    private Integer repuestosVista;
}
