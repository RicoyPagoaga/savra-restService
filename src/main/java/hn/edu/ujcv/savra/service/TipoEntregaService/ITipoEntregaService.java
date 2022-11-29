package hn.edu.ujcv.savra.service.TipoEntregaService;

import hn.edu.ujcv.savra.entity.TipoEntrega;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface ITipoEntregaService {
    TipoEntrega       saveTipoEntrega(TipoEntrega tipoEntrega) throws BusinessException;
    List<TipoEntrega> saveTiposEntrega(List<TipoEntrega> tiposEntrega) throws BusinessException;
    List<TipoEntrega> getTiposEntrega() throws BusinessException;
    TipoEntrega       getTipoEntregaById(long id) throws BusinessException, NotFoundException;
    TipoEntrega       getTipoEntregaByNombre(String nombre) throws BusinessException, NotFoundException;
    void              deleteTipoEntrega(long id) throws BusinessException, NotFoundException;
    TipoEntrega       updateTipoEntrega(TipoEntrega tipoEntrega) throws BusinessException, NotFoundException;
}
