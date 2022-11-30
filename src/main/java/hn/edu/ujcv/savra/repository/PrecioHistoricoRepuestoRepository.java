package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.PrecioHistoricoRepuesto.PrecioHistoricoRepuesto;
import hn.edu.ujcv.savra.entity.PrecioHistoricoRepuesto.PrecioHistoricoRepuestoPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrecioHistoricoRepuestoRepository extends JpaRepository<PrecioHistoricoRepuesto, PrecioHistoricoRepuestoPK> {
    //
}
