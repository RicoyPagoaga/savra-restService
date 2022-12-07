package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.FacturaDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacturaDetalleRepository extends JpaRepository<FacturaDetalle,Long> {
    List<FacturaDetalle> findByIdFactura(long idFactura);
}
