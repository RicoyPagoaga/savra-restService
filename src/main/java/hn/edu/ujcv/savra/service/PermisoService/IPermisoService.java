package hn.edu.ujcv.savra.service.PermisoService;

import hn.edu.ujcv.savra.entity.Permiso;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface IPermisoService {
    Permiso        savePermiso(Permiso permiso) throws BusinessException;
    List <Permiso> savePermisos(List<Permiso> permisos) throws BusinessException;
    List <Permiso> getPermisos() throws BusinessException;
    Permiso        getPermisoById(long id) throws BusinessException,NotFoundException;
    Permiso        getPermisoByNombre(String nombre) throws BusinessException,NotFoundException;
    void           deletePermiso(long id) throws BusinessException,NotFoundException;
    Permiso        updatePermiso(Permiso permiso) throws BusinessException,NotFoundException;
}
