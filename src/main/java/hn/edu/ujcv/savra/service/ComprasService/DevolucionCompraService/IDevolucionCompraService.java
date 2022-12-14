package hn.edu.ujcv.savra.service.ComprasService.DevolucionCompraService;

import hn.edu.ujcv.savra.entity.Compra.DevolucionCompra;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface IDevolucionCompraService {
    DevolucionCompra        saveDevolucionCompra(DevolucionCompra devolucionCompra) throws BusinessException;
    List<DevolucionCompra>  saveDevolucionesCompra (List<DevolucionCompra> devolucionesCompra) throws BusinessException;
    List <DevolucionCompra> getDevolucionesCompra() throws BusinessException;
    DevolucionCompra        getDevolucionCompraById(long id)throws BusinessException, NotFoundException;
    void                    deleteDevolucionCompra(long id) throws BusinessException,NotFoundException;
    DevolucionCompra        updateDevolucionCompra(DevolucionCompra devolucionCompra) throws BusinessException,NotFoundException;
}
