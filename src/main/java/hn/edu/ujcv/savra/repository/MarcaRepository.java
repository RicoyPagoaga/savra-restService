package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
    Optional<Marca> findFirstByNombre(String nombre);
}
