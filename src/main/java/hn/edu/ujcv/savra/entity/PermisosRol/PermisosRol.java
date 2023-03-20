package hn.edu.ujcv.savra.entity.PermisosRol;

import hn.edu.ujcv.savra.entity.ModuloAccion;
import hn.edu.ujcv.savra.entity.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permisos_rol")
@IdClass(PermisosRolPK.class)
public class PermisosRol {
    @Id
    @Column(name = "idModuloAccion")
    private long idModuloAccion;

    @Id
    @Column(name = "idRol")
    private long idRol;

    @ManyToOne
    @JoinColumn(name = "idModuloAccion", referencedColumnName = "idModuloAccion",
            insertable = false, updatable = false)
    private ModuloAccion moduloAccion;

    @ManyToOne
    @JoinColumn(name = "idRol", referencedColumnName = "idRol",
            insertable = false, updatable = false)
    private Rol rol;
}
