package hn.edu.ujcv.savra.entity.ModuloPermiso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ModuloPermisoPK implements Serializable {
    private long idUsuario;
    private long idModulo;
}
