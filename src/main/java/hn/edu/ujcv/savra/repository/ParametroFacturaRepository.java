package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.ParametroFactura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParametroFacturaRepository extends JpaRepository<ParametroFactura,Long> {
    Optional<ParametroFactura> findFirstByCai(String cai);
}
