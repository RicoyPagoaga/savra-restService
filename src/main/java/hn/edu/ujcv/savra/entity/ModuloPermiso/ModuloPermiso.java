package hn.edu.ujcv.savra.entity.ModuloPermiso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "modulo_permiso")
@IdClass(ModuloPermisoPK.class)
public class ModuloPermiso {
    @Id
    private long idModulo;
    @Id
    private long idPermiso;
}
