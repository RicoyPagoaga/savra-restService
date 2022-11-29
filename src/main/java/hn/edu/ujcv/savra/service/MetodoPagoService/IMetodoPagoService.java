package hn.edu.ujcv.savra.service.MetodoPagoService;

import hn.edu.ujcv.savra.entity.MetodoPago;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface IMetodoPagoService {
    MetodoPago       saveMetodoPago(MetodoPago metodoPago) throws BusinessException;
    List<MetodoPago> saveMetodosPago(List<MetodoPago> metodosPago) throws BusinessException;
    List<MetodoPago> getMetodosPago() throws BusinessException;
    MetodoPago       getMetodoPagoById(long id) throws BusinessException, NotFoundException;
    MetodoPago       getMetodoPagoByNombre(String nombre) throws BusinessException, NotFoundException;
    void             deleteMetodoPago(long id) throws BusinessException, NotFoundException;
    MetodoPago       updateMetodoPago(MetodoPago metodoPago) throws BusinessException, NotFoundException;
}
