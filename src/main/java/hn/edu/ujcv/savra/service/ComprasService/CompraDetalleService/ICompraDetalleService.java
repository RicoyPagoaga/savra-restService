package hn.edu.ujcv.savra.service.ComprasService.CompraDetalleService;

import hn.edu.ujcv.savra.entity.Compra.CompraDetalle;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface ICompraDetalleService {
    CompraDetalle        saveCompraDetalle(CompraDetalle compraDetalle) throws BusinessException;
    List<CompraDetalle>  saveComprasDetalle (List<CompraDetalle> comprasDetalle) throws BusinessException;
    List<CompraDetalle>  updateComprasDetalles (List<CompraDetalle> comprasDetalle) throws BusinessException;
    List <CompraDetalle> getComprasDetalle() throws BusinessException;
    CompraDetalle        getCompraDetalleById(long id)throws BusinessException, NotFoundException;
    void                 deleteCompraDetalle(long id) throws BusinessException,NotFoundException;
    CompraDetalle        updateCompraDetalle(CompraDetalle compraDetalle) throws BusinessException,NotFoundException;
}
