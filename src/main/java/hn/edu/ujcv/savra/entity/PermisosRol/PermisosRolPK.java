package hn.edu.ujcv.savra.entity.PermisosRol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PermisosRolPK implements Serializable {
    private long idModuloAccion;
    private long idRol;
}
