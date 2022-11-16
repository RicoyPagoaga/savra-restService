package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.CategoriaRepuesto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepuestoRepository extends JpaRepository<CategoriaRepuesto, Long> {
    Optional<CategoriaRepuesto> findByNombre(String nombre);
}
