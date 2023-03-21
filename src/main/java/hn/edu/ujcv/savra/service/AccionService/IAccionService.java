package hn.edu.ujcv.savra.service.AccionService;

import hn.edu.ujcv.savra.entity.Accion;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface IAccionService {
    Accion saveAccion(Accion accion) throws BusinessException;
    List <Accion> saveAccions(List<Accion> accions) throws BusinessException;
    List <Accion> getAccion() throws BusinessException;
    Accion getAccionById(long id) throws BusinessException,NotFoundException;
    Accion getAccionByNombre(String nombre) throws BusinessException,NotFoundException;
    void deleteAccion(long id) throws BusinessException,NotFoundException;
    Accion updateAccion(Accion accion) throws BusinessException,NotFoundException;
    List <Accion> accionesByRolModulo(long idRol,String modulo)throws BusinessException;
}
