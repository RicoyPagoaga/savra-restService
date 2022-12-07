package hn.edu.ujcv.savra.repository.Repuesto;

import hn.edu.ujcv.savra.entity.Repuesto.Repuesto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepuestoRepository extends JpaRepository<Repuesto, Long> {
    Optional<Repuesto> findFirstByNombre(String nombre);
}
