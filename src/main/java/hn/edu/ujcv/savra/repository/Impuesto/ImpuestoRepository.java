package hn.edu.ujcv.savra.repository.Impuesto;

import hn.edu.ujcv.savra.entity.Impuesto.Impuesto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImpuestoRepository extends JpaRepository<Impuesto, Long> {
    Optional<Impuesto> findFirstByNombre(String nombre);
}
