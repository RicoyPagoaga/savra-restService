package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.Repuesto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepuestoRepository extends JpaRepository<Repuesto, Long> {
    Optional<Repuesto> findFirstByNombre(String nombre);
}
