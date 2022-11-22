package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CargoRepository extends JpaRepository<Cargo ,Long> {
    Optional<Cargo> findCargoByNombre (String nombre);
}