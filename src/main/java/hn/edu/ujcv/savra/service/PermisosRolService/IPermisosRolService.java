package hn.edu.ujcv.savra.service.PermisosRolService;

import hn.edu.ujcv.savra.entity.ModuloAccion;
import hn.edu.ujcv.savra.entity.PermisosRol.PermisosRol;
import hn.edu.ujcv.savra.entity.Rol;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface IPermisosRolService {
    PermisosRol saveModuloPermiso(PermisosRol moduloPermiso) throws BusinessException;
    List<PermisosRol> saveModuloPermisos(List<PermisosRol> moduloPermisos) throws BusinessException;
    List<PermisosRol> getModuloPermisos() throws BusinessException;
    PermisosRol getModuloPermisoById(long idModuloAccion, long idRol) throws BusinessException, NotFoundException;
    void deleteModuloPermiso(long idModuloAccion, long idRol) throws BusinessException,NotFoundException;
    PermisosRol updateModuloPermiso(PermisosRol moduloPermiso) throws BusinessException,NotFoundException;

}

