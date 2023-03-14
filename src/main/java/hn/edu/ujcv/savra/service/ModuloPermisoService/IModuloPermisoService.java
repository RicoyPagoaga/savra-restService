package hn.edu.ujcv.savra.service.ModuloPermisoService;

import hn.edu.ujcv.savra.entity.ModuloPermiso.ModuloPermiso;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface IModuloPermisoService {
    ModuloPermiso saveModuloPermiso(ModuloPermiso moduloPermiso) throws BusinessException;
    List<ModuloPermiso> saveModuloPermisos(List<ModuloPermiso> moduloPermisos) throws BusinessException;
    List<ModuloPermiso> getModuloPermisos() throws BusinessException;
    ModuloPermiso getModuloPermisoById(long id, long idPermiso) throws BusinessException, NotFoundException;
    //UsuarioPermiso       getUsuarioPermisoByIdUsuario(String usuario) throws BusinessException, NotFoundException;
    void deleteModuloPermiso(long id, long idPermiso) throws BusinessException,NotFoundException;
    ModuloPermiso updateModuloPermiso(ModuloPermiso moduloPermiso) throws BusinessException,NotFoundException;

}

