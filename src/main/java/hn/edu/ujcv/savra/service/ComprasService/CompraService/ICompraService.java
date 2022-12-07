package hn.edu.ujcv.savra.service.ComprasService.CompraService;

import hn.edu.ujcv.savra.entity.Compra.Compra;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface ICompraService {
    Compra        saveCompra(Compra compra, boolean detalle) throws BusinessException;
    List<Compra>  saveCompras (List<Compra> compras) throws BusinessException;
    List <Compra> getCompras() throws BusinessException;
    Compra        getCompraById(long id)throws BusinessException, NotFoundException;
    void          deleteCompra(long id) throws BusinessException,NotFoundException;
    Compra        updateCompra(Compra compra, boolean detalle) throws BusinessException,NotFoundException;
}
