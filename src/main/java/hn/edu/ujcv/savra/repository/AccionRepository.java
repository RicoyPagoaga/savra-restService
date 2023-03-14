package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.Accion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccionRepository extends JpaRepository<Accion, Long> {
    Optional<Accion> findAccionByNombre(String nombre);
}
