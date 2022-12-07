package hn.edu.ujcv.savra.repository.Impuesto;

import hn.edu.ujcv.savra.entity.Impuesto.ImpuestoHistorico.ImpuestoHistorico;
import hn.edu.ujcv.savra.entity.Impuesto.ImpuestoHistorico.ImpuestoHistoricoPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImpuestoHistoricoRepository extends JpaRepository<ImpuestoHistorico, ImpuestoHistoricoPK> {
}
