package hn.edu.ujcv.savra.service.ModeloService;

import hn.edu.ujcv.savra.entity.Modelo;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface IModeloService {
    Modelo saveModelo(Modelo modelo) throws BusinessException;
    List<Modelo> saveModelos(List<Modelo> modelos) throws BusinessException;
    List<Modelo> getModelos() throws BusinessException;
    Modelo       getModeloById(long id) throws BusinessException, NotFoundException;
    Modelo       getModeloByNombre(String nombre) throws BusinessException, NotFoundException;
    void         deleteModelo(long id) throws BusinessException, NotFoundException;
    Modelo       updateModelo(Modelo modelo) throws BusinessException, NotFoundException;
}
