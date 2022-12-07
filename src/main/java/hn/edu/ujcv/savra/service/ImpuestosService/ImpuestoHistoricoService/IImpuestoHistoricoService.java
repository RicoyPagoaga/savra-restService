package hn.edu.ujcv.savra.service.ImpuestosService.ImpuestoHistoricoService;

import hn.edu.ujcv.savra.entity.Impuesto.ImpuestoHistorico.ImpuestoHistorico;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface IImpuestoHistoricoService {
    ImpuestoHistorico       saveImpuestoHistorico(ImpuestoHistorico impuestoHistorico) throws BusinessException;
    List<ImpuestoHistorico> getImpuestosHistorico() throws BusinessException;
    ImpuestoHistorico       updateImpuestoHistorico(ImpuestoHistorico impuestoHistorico) throws BusinessException, NotFoundException;
}
