package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {
    Optional<Permiso> findPermisoByNombre (String nombre);
}
