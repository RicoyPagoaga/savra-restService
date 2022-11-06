package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpleadosRepository extends JpaRepository<Empleado ,Long> {
    Optional<Empleado> findByNombre(String nombre);
}
