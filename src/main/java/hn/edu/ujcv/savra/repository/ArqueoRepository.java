package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.Arqueo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArqueoRepository extends JpaRepository<Arqueo ,Long> {
    Optional<Arqueo> findArqueoByObservacion(String observacion);
}

