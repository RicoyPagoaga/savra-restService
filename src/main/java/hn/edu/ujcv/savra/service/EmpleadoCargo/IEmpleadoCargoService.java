package hn.edu.ujcv.savra.service.EmpleadoCargo;

import hn.edu.ujcv.savra.entity.EmpleadoCargo.EmpleadoCargo;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface IEmpleadoCargoService {
    EmpleadoCargo        saveEmpleadoCargo (EmpleadoCargo empleadoCargo) throws BusinessException;
    List <EmpleadoCargo> saveEmpleadoCargos (List<EmpleadoCargo> empleadoCargos) throws BusinessException;
    List <EmpleadoCargo> getEmpleadoCargos() throws BusinessException;
    EmpleadoCargo        getEmpleadoCargoById(long id,String fecha) throws BusinessException, NotFoundException;
    //EmpleadoCargo        getEmpleadoCargoByfecha(String fecha) throws BusinessException,NotFoundException;
    void                 deleteEmpleadoCargo(long id ,String fecha) throws BusinessException,NotFoundException;
    EmpleadoCargo        updateEmpleadoCargo (EmpleadoCargo empleadoCargo) throws BusinessException,NotFoundException;
}
