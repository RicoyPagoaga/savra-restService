package hn.edu.ujcv.savra.service.TipoDocumentoService;

import hn.edu.ujcv.savra.entity.MetodoPago;
import hn.edu.ujcv.savra.entity.TipoDocumento;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.TipoDocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TipoDocumentoService implements ITipoDocumentoService {
    @Autowired
    private TipoDocumentoRepository repository;

    @Override
    public TipoDocumento saveTipoDocumento(TipoDocumento tipoDocumento) throws BusinessException {
        try {
            //nombreDocumento
            if(tipoDocumento.getNombreDocumento().isEmpty()){
                throw new BusinessException("El nombre no debe estar vacio ఠ_ఠ");
            }
            if(tipoDocumento.getNombreDocumento().length()< 3){
                throw new BusinessException("El nombre no debe tener menos de 3 caracteres ఠ_ఠ");
            }
            if (tipoDocumento.getNombreDocumento().length()>30){
                throw new BusinessException("El nombre no debe tener mas de 30 caracteres ఠ_ఠ");
            }
            if (tipoDocumento.getNombreDocumento().matches("(.)\\1{2,}")){
                throw new BusinessException("No debe tener tantas letras repetidas ఠ_ఠ");
            }
            if (tipoDocumento.getNombreDocumento().matches("[^A-Za-zÁÉÍÓÚáéíóúñ\\s]")){
                throw new BusinessException("El nombre solo debe tener letras ఠ_ఠ");
            }
            Pattern patDoc = Pattern.compile("^([a-zA-Z]+)(\\s[a-zA-Z]+)*$");
            Matcher matDoc = patDoc.matcher(tipoDocumento.getNombreDocumento().trim());
            if(!matDoc.matches()){
                throw new BusinessException("Nombre Documento no debe contener números o letras con tildeఠ_ఠ");
            }
            List<TipoDocumento> metodos = getTipoDocumento();
            for (TipoDocumento item : metodos) {
                if ((item.getNombreDocumento().equals(tipoDocumento.getNombreDocumento().trim())) && (item.getIdTipoDocumento() != tipoDocumento.getIdTipoDocumento())) {
                    throw new BusinessException("El nombre del documento de pago ya está en uso");
                }
            }
            tipoDocumento.setNombreDocumento(tipoDocumento.getNombreDocumento().toUpperCase());
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
            opt=repository.findByNombreDocumento(nombre);
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
                //NombreDocumento
                if(tipoDocumento.getNombreDocumento().isEmpty()){
                    throw new BusinessException("El nombre no debe estar vacio ఠ_ఠ");
                }
                if(tipoDocumento.getNombreDocumento().trim().length()< 3){
                    throw new BusinessException("El nombre no debe tener menos de 3 caracteres ఠ_ఠ");
                }
                if (tipoDocumento.getNombreDocumento().trim().length()>30){
                    throw new BusinessException("El nombre no debe tener mas de 30 caracteres ఠ_ఠ");
                }
                if (tipoDocumento.getNombreDocumento().trim().matches("(.)\\1{2,}")){
                    throw new BusinessException("No debe tener tantas letras repetidas ఠ_ఠ");
                }
                if (tipoDocumento.getNombreDocumento().trim().matches("[^A-Za-zÁÉÍÓÚáéíóúñ\\s]")){
                    throw new BusinessException("El nombre solo debe tener letras ఠ_ఠ");
                }
                Pattern patDoc = Pattern.compile("^([a-zA-Z]+)(\\s[a-zA-Z]+)*$");
                Matcher matDoc = patDoc.matcher(tipoDocumento.getNombreDocumento().trim());
                if(!matDoc.matches()){
                    throw new BusinessException("Nombre Documento no debe contener números o caracteres especialesఠ_ఠ");
                }
                List<TipoDocumento> metodos = getTipoDocumento();
                for (TipoDocumento item : metodos) {
                    if ((item.getNombreDocumento().equals(tipoDocumento.getNombreDocumento().trim())) && (item.getIdTipoDocumento() != tipoDocumento.getIdTipoDocumento())) {
                        throw new BusinessException("El nombre del documento ya está en uso");
                    }
                }
                TipoDocumento existingtTipoDocumento =new TipoDocumento();
                existingtTipoDocumento.setIdTipoDocumento(tipoDocumento.getIdTipoDocumento());
                existingtTipoDocumento.setNombreDocumento(tipoDocumento.getNombreDocumento().toUpperCase());

                return repository.save(existingtTipoDocumento);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
    }

}