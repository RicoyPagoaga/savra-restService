package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.ModuloPermiso.ModuloPermiso;
import hn.edu.ujcv.savra.entity.ModuloPermiso.ModuloPermisoPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModuloPermisoRepository extends JpaRepository<ModuloPermiso, ModuloPermisoPK> {
    Optional<ModuloPermiso> findByAndIdPermiso (Long aLong);
}
