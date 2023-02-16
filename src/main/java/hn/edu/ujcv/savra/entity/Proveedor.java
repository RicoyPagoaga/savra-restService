package hn.edu.ujcv.savra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "proveedor")

public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long   idProveedor;
    private String nombre;
    private String correo;
    private String telefono;
    @ManyToOne
    @JoinColumn(name = "idPais",referencedColumnName="idPais")
    private Pais pais;
    private String nombreContacto;
    private String sitioWeb;
}
