package hn.edu.ujcv.savra.service.TransmisionService;

import hn.edu.ujcv.savra.entity.Pais;
import hn.edu.ujcv.savra.entity.Transmision;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface ITransmisionService {
    Transmision saveTransmision(Transmision pTransmision)throws BusinessException, SQLException;
    List<Transmision> getTransmisiones()throws BusinessException;
    Transmision getTransmisionById(long id)throws BusinessException, NotFoundException;
    Transmision getTransmisionByNombre(String nombre)throws  BusinessException,NotFoundException;
    void deteleTransmision(long id)throws BusinessException,NotFoundException;
    Transmision updateTransmision(Transmision pTransmision)throws BusinessException,NotFoundException,SQLException;
}
