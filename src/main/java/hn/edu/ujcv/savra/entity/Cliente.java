package hn.edu.ujcv.savra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCliente;
    private String nombre;
    private String documento;
    @ManyToOne
    @JoinColumn(name = "idTipoDocumento",referencedColumnName = "idTipoDocumento")
    private TipoDocumento tipoDocumento;
    private String telefono;
    private String  direccion;
    @ManyToOne
    @JoinColumn(name = "idCategoria",referencedColumnName = "idCategoria")
    private CategoriaCliente categoria;
}
