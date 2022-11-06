package hn.edu.ujcv.savra.service.Marca;

import hn.edu.ujcv.savra.entity.Marca;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface IMarcaService {
    Marca saveMarca(Marca marca) throws BusinessException;
    List<Marca> saveMarcas(List<Marca> marcas) throws BusinessException;
    List<Marca> getMarcas() throws BusinessException;
    Marca       getMarcaById(long id) throws BusinessException, NotFoundException;
    Marca       getMarcaByNombre(String nombre) throws BusinessException, NotFoundException;
    void        deleteMarca(long id) throws BusinessException, NotFoundException;
    Marca       updateMarca(Marca marca) throws BusinessException, NotFoundException;
}
