package hn.edu.ujcv.savra.service.EmpleadoService;
import hn.edu.ujcv.savra.entity.Empleado;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface IEmpleadosService {
    Empleado        saveEmpleados(Empleado empleado) throws BusinessException;
    List <Empleado> saveEmpleados (List<Empleado> empleado) throws BusinessException;
    List <Empleado> getEmpleados() throws BusinessException;
    Empleado        getEmpleadosById(long id)throws BusinessException, NotFoundException;
    Empleado        getEmpleadoByNombre(String nombre) throws BusinessException, NotFoundException;
    void            deleteEmpleados(long id) throws BusinessException,NotFoundException;
    Empleado        updateEmpleados(Empleado empleado) throws BusinessException,NotFoundException;
}
