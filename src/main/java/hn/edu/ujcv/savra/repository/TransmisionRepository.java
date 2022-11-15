package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.Transmision;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransmisionRepository extends JpaRepository<Transmision, Long> {
    Optional<Transmision> findByNombre(String nombre);
}
