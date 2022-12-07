package hn.edu.ujcv.savra.service.RepuestosService.RepuestoService;



import hn.edu.ujcv.savra.entity.Repuesto.Repuesto;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface IRepuestoService {
    Repuesto       saveRepuesto(Repuesto repuesto, boolean stockA, boolean stockM,
                                boolean stockMa, double precio) throws BusinessException;
    List<Repuesto> saveRepuestos(List<Repuesto> repuestos) throws BusinessException;
    List<Repuesto> getRepuestos() throws BusinessException;
    Repuesto       getRepuestoById(long id) throws BusinessException, NotFoundException;
    Repuesto       getRepuestoByNombre(String nombre) throws BusinessException, NotFoundException;
    void           deleteRepuesto(long id) throws BusinessException, NotFoundException;
    Repuesto       updateRepuesto(Repuesto repuesto, boolean stockA, boolean stockM,
                                  boolean stockMa, double precio) throws BusinessException, NotFoundException;
}
