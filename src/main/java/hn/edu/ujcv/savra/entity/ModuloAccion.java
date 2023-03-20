package hn.edu.ujcv.savra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "modulo_accion")
public class ModuloAccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idModuloAccion;
    @ManyToOne
    @JoinColumn(name = "idModulo", referencedColumnName = "idModulo")
    private Modulo modulo;
    @ManyToOne
    @JoinColumn(name = "idAccion", referencedColumnName = "idAccion")
    private Accion accion;
}
