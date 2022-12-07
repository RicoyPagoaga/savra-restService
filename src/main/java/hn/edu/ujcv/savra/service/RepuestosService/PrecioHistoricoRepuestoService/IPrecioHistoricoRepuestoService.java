package hn.edu.ujcv.savra.service.RepuestosService.PrecioHistoricoRepuestoService;

import hn.edu.ujcv.savra.entity.Repuesto.PrecioHistoricoRepuesto.PrecioHistoricoRepuesto;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface IPrecioHistoricoRepuestoService {
    PrecioHistoricoRepuesto       savePrecioRepuesto(PrecioHistoricoRepuesto precioRepuesto) throws BusinessException; //
    List<PrecioHistoricoRepuesto> savePreciosRepuesto(List<PrecioHistoricoRepuesto> preciosRepuesto) throws BusinessException;
    List<PrecioHistoricoRepuesto> getPreciosRepuesto() throws BusinessException; //
    PrecioHistoricoRepuesto       getPrecioRepuestoById(long id, LocalDate fecha) throws BusinessException, NotFoundException;
    void                          deletePrecioRepuesto(long id, LocalDate fecha) throws BusinessException, NotFoundException;
    PrecioHistoricoRepuesto       updatePrecioRepuesto(PrecioHistoricoRepuesto precioRepuesto) throws BusinessException, NotFoundException; //
}
