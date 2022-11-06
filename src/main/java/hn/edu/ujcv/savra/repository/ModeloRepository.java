package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    Optional<Modelo> findFirstByNombre(String nombre);
}
