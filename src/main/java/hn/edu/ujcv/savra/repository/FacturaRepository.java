package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacturaRepository extends JpaRepository<Factura,Long> {
    Optional<Factura> findByNoFactura(String noFactura);
}
