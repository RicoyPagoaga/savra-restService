package hn.edu.ujcv.savra.service.ParametroFacturaService;

import hn.edu.ujcv.savra.entity.ParametroFactura;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface IParametroFacturaService {
    ParametroFactura saveParametro(ParametroFactura pParametro)throws BusinessException;
    ParametroFactura updateParametro(ParametroFactura pParametro)throws BusinessException, NotFoundException;
    List<ParametroFactura> getParametrosFactura()throws BusinessException;
    void deleteParametro(long idParametro)throws BusinessException,NotFoundException;
    ParametroFactura getParametroByCai(String pCai)throws BusinessException,NotFoundException;
}
