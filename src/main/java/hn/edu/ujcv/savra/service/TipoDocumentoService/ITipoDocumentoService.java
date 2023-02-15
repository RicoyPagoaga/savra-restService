package hn.edu.ujcv.savra.service.TipoDocumentoService;

import hn.edu.ujcv.savra.entity.TipoDocumento;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface ITipoDocumentoService {
    TipoDocumento       saveTipoDocumento( TipoDocumento tipoDocumento) throws BusinessException;
    List<TipoDocumento> saveTipoDocumentos (List<TipoDocumento> tipoDocumentos) throws BusinessException;
    List<TipoDocumento> getTipoDocumentos() throws BusinessException;
    TipoDocumento       getTipoDocumentoById(long id) throws BusinessException, NotFoundException;
    TipoDocumento       getTipoDocumentoByNombre(String nombre) throws BusinessException,NotFoundException;
    void                deleteTipoDocumento(long id) throws BusinessException,NotFoundException;
    TipoDocumento       updateTipoDocumento(TipoDocumento tipoDocumento) throws BusinessException,NotFoundException;
}
