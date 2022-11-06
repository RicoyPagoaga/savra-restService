package hn.edu.ujcv.savra.service.TipoDocumentoService;

import hn.edu.ujcv.savra.entity.TipoDocumento;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.TipoDocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoDocumentoService implements ITipoDocumentoService {
    @Autowired
    private TipoDocumentoRepository repository;

    @Override
    public TipoDocumento saveTipoDocumento(TipoDocumento tipoDocumento) throws BusinessException {
        try {
            if(tipoDocumento.getNombreDocumento().isEmpty()){
                throw new BusinessException("No debe estar vacio");
            }
            return repository.save(tipoDocumento);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<TipoDocumento> saveTipoDocumento(List<TipoDocumento> tipoDocumentos) throws BusinessException {
        try {
            for (TipoDocumento tipodocumento:tipoDocumentos) {
                if (tipodocumento.getNombreDocumento().isEmpty()){
                    throw new BusinessException("No debe estar vacio");
                }
            }
            return repository.saveAll(tipoDocumentos);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }
    @Override
    public List<TipoDocumento> getTipoDocumento() throws BusinessException{
        try {

            return repository.findAll();

        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }
    @Override
    public TipoDocumento getTipoDocumentoById(long id) throws BusinessException, NotFoundException {
        Optional<TipoDocumento> opt=null;
        try {
            opt=repository.findById(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el documento"+id);
        }
        return opt.get();
    }

    @Override
    public TipoDocumento getTipoDocumentoByNombre(String nombre) throws BusinessException, NotFoundException {
        Optional<TipoDocumento> opt= null;
        try {
            opt=repository.findByNombre(nombre);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el documento :v"+nombre);
        }
        return opt.get();
    }

    @Override
    public void deleteTipoDocumento(long id) throws BusinessException, NotFoundException {
        Optional<TipoDocumento> opt= null;
        try {
            opt=repository.findById(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el documento v;"+id);
        }
        else {
            try {
                repository.deleteById(id);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }

    }

    @Override
    public TipoDocumento updateTipoDocumento(TipoDocumento tipoDocumento) throws BusinessException, NotFoundException {
        Optional<TipoDocumento> opt= null;
        try {
            opt=repository.findById(tipoDocumento.getIdTipoDocumento());
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el documento v;"+ tipoDocumento.getIdTipoDocumento());
        }
        else {
            try {
                TipoDocumento existingtTipoDocumento =new TipoDocumento();
                existingtTipoDocumento.setIdTipoDocumento(tipoDocumento.getIdTipoDocumento());
                existingtTipoDocumento.setNombreDocumento(tipoDocumento.getNombreDocumento());
                return repository.save(existingtTipoDocumento);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
    }

}
