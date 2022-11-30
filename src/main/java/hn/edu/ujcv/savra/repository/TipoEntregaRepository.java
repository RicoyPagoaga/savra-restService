package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.TipoEntrega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoEntregaRepository extends JpaRepository<TipoEntrega, Long> {
    Optional<TipoEntrega> findFirstByNombre(String nombre);
}
