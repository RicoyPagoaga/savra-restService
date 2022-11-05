package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.CategoriaCliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaClienteRepository extends JpaRepository<CategoriaCliente,Long> {
    Optional<CategoriaCliente> findByNombre(String nombre);
}
