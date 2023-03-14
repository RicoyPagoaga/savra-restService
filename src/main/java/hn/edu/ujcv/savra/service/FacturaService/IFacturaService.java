package hn.edu.ujcv.savra.service.FacturaService;

import hn.edu.ujcv.savra.entity.Factura;
import hn.edu.ujcv.savra.entity.FacturaDetalle;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface IFacturaService {
    Factura guardarFactura(Factura pFactura, int pDetalles, double total)throws BusinessException;
    List<Factura> obtenerFacturas()throws BusinessException;
    Factura obtenerFacturaByNoFactura(String idFactura)throws BusinessException, NotFoundException;
    Object obtenerRecibo(long idFactura) throws BusinessException, NotFoundException;
}
