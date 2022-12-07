package hn.edu.ujcv.savra.service.ImpuestosService.ImpuestoService;

import hn.edu.ujcv.savra.entity.Impuesto.Impuesto;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface IImpuestoService {
    Impuesto        saveImpuesto(Impuesto impuesto, int valor, boolean nulo) throws BusinessException;
    List<Impuesto>  saveImpuestos (List<Impuesto> impuestos) throws BusinessException;
    List <Impuesto> getImpuestos() throws BusinessException;
    Impuesto        getImpuestoById(long id)throws BusinessException, NotFoundException;
    Impuesto        getImpuestoByNombre(String nombre) throws BusinessException, NotFoundException;
    void            deleteImpuesto(long id) throws BusinessException,NotFoundException;
    Impuesto        updateImpuesto(Impuesto impuesto, int valor, boolean nulo) throws BusinessException,NotFoundException;
}
