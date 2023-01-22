package hn.edu.ujcv.savra.service.RolService;

import hn.edu.ujcv.savra.entity.Rol;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface IRolService {
    Rol         saveRol(Rol rol ) throws BusinessException;
    List<Rol>   saveRoles (List<Rol> rols) throws BusinessException;
    List<Rol>   getRol()throws BusinessException;
    Rol         getRolById(long id) throws BusinessException, NotFoundException;
    Rol         getRolByNombre(String nombre) throws BusinessException,NotFoundException;
    void        deleteRol(long id) throws BusinessException,NotFoundException;
    Rol         updateRol (Rol rol) throws BusinessException,NotFoundException;

}
