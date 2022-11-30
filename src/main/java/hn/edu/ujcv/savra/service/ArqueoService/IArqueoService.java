package hn.edu.ujcv.savra.service.ArqueoService;

import hn.edu.ujcv.savra.entity.Arqueo;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import org.aspectj.weaver.ast.Not;

import java.util.List;

public interface IArqueoService {
    Arqueo          saveArqueo(Arqueo arqueo) throws BusinessException;
    List <Arqueo>   saveArqueos (List<Arqueo> arqueos) throws BusinessException;
    List <Arqueo>   getArqueos() throws BusinessException;
    Arqueo          getArqueoById(long id) throws BusinessException, NotFoundException;
    Arqueo          getArqueoByObservacion(String observacion) throws BusinessException, NotFoundException;
    void            deleteArqueo (long id) throws BusinessException,NotFoundException;
    Arqueo          udateArqueo (Arqueo arqueo) throws BusinessException,NotFoundException;
}
