package hn.edu.ujcv.savra.service.ModuloAccionService;

import hn.edu.ujcv.savra.entity.ModuloAccion;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface IModuloAccionService {
    ModuloAccion saveModuloAccion(ModuloAccion moduloAccion) throws BusinessException;
    List<ModuloAccion> saveModulosAccion(List<ModuloAccion> modulosAccion) throws BusinessException;
    List<ModuloAccion> getModulosAccion() throws BusinessException;
    ModuloAccion getModuloAccionById(long id) throws BusinessException, NotFoundException;
    void deleteModuloAccion(long id) throws BusinessException,NotFoundException;
    ModuloAccion updateModuloAccion(ModuloAccion moduloAccion) throws BusinessException,NotFoundException;
}
