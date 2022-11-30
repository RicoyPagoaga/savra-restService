package hn.edu.ujcv.savra.service.RepuestoTemporalService;

import hn.edu.ujcv.savra.entity.RepuestoTemporal;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface IRepuestoTemporalService {
    RepuestoTemporal       saveRepuesto(RepuestoTemporal repuesto) throws BusinessException;
    List<RepuestoTemporal> saveRepuestos(List<RepuestoTemporal> repuestos) throws BusinessException;
    List<RepuestoTemporal> getRepuestos() throws BusinessException;
    RepuestoTemporal       getRepuestoById(long id) throws BusinessException, NotFoundException;
    //RepuestoTemporal       getRepuestoByNombre(String nombre) throws BusinessException, NotFoundException;
    void                   deleteRepuesto(long id) throws BusinessException, NotFoundException;
    //RepuestoTemporal       updateRepuesto(RepuestoTemporal repuesto) throws BusinessException, NotFoundException;
}
