package hn.edu.ujcv.savra.service.PaisService;

import hn.edu.ujcv.savra.entity.Pais;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface IPaisService {
    Pais savePais(Pais pPais)throws BusinessException, SQLException;
    List<Pais> getPaises()throws BusinessException;
    Pais getPaisById(long id)throws BusinessException, NotFoundException;
    Pais getPaisByNombre(String nombre)throws  BusinessException,NotFoundException;
    void detelePais(long id)throws BusinessException,NotFoundException;
    Pais updatePais(Pais pPais)throws BusinessException,NotFoundException,SQLException;
}
