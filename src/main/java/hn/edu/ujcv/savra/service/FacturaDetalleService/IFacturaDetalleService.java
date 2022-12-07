package hn.edu.ujcv.savra.service.FacturaDetalleService;

import hn.edu.ujcv.savra.entity.FacturaDetalle;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface IFacturaDetalleService {
    FacturaDetalle guardarFacturaDetalle(FacturaDetalle pFacturaDetalle)throws BusinessException;
    List<FacturaDetalle> guardarFacturaDetalles(List<FacturaDetalle> pFacturaDetalles)throws BusinessException;
    List<FacturaDetalle> obtenerFacturaDetalles()throws BusinessException;
    List<FacturaDetalle> obtenerFacturaDetallesByIdFactura(long idFactura)throws BusinessException;
}
