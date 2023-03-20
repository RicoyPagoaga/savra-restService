package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.PermisosRol.PermisosRol;
import hn.edu.ujcv.savra.entity.PermisosRol.PermisosRolPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermisosRolRepository extends JpaRepository<PermisosRol, PermisosRolPK> {
    //Optional<PermisosRol> findByAndIdPermiso (Long aLong);
}
