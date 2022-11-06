package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    Optional<Proveedor> findFirstByNombre(String nombre);
}
