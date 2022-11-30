package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long> {
    Optional<MetodoPago> findFirstByNombre(String nombre);
}
