package hn.edu.ujcv.savra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "categoriaRepuesto")
public class CategoriaRepuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long   idCategoria;
    String nombre;
    String descripcion;
}
