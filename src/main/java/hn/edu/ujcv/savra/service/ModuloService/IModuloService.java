package hn.edu.ujcv.savra.service.ModuloService;

import hn.edu.ujcv.savra.entity.Modulo;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface IModuloService {
    Modulo saveModulo(Modulo modulo) throws BusinessException, SQLException;
    List<Modulo> getModulos() throws BusinessException;
    Modulo getModuloById(long id) throws BusinessException, NotFoundException;
    Modulo getModuloByNombre(String nombre) throws BusinessException,NotFoundException;
    void deleteModulo(long id) throws BusinessException,NotFoundException;
    Modulo updateModulo(Modulo modulo) throws BusinessException,NotFoundException;
}
