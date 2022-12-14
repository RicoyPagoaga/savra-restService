package hn.edu.ujcv.savra.service.PaisService;

import hn.edu.ujcv.savra.entity.Pais;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.*;

@Service
public class PaisService implements IPaisService{
    @Autowired
    private PaisRepository repository;
    @Override
    public Pais savePais(Pais pPais) throws BusinessException, SQLException {
        try{
            //cod_iso
            if(pPais.getCod_iso().isEmpty()){
                throw new BusinessException("Código ISO esta Vacío ఠ_ఠ");
            }
            if(pPais.getCod_iso().length() != 2){
                throw new BusinessException("Utilice el código ISO de 2 letras ఠ_ఠ");
            }
            //nombre
            if(pPais.getNombre().isEmpty()){
                throw new BusinessException("Nombre País esta vacío ఠ_ఠ");
            }
            if(pPais.getNombre().length() < 4){
                throw new BusinessException("Nombre País debe contener más de 4 caracteres ఠ_ఠ");
            }
            if(pPais.getNombre().length() >56){
                throw new BusinessException("Nombre País debe contener menos de 56 caracteres ఠ_ఠ");
            }

            //cod_area
            if(pPais.getCod_area().isEmpty()){
                throw new BusinessException("Código de área esta vacío ఠ_ఠ");
            }
            if(pPais.getCod_area().length() < 1){
                throw new BusinessException("Código de área debe contener al menos un caracterఠ_ఠ");
            }
            if(pPais.getCod_area().length() > 3){
                throw new BusinessException("Código de área no debe contener mas de 3 caracteresఠ_ఠ");
            }
            Pattern pat = Pattern.compile("[\\d]*");
            Matcher mat = pat.matcher(pPais.getCod_area());
            if(!mat.matches()){
                throw new BusinessException("Código de área debe ser númerico (no es necesario utilizar '+')");
            }
            pPais.setCod_iso(pPais.getCod_iso().toUpperCase());
            pPais.setNombre(pPais.getNombre().toUpperCase());
            return repository.save(pPais);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }
    @Override
    public List<Pais> getPaises() throws BusinessException {
        return repository.findAll();
    }

    @Override
    public Pais getPaisById(long iso) throws BusinessException, NotFoundException {
        return null;
    }

    @Override
    public Pais getPaisByNombre(String nombre) throws BusinessException, NotFoundException {
        return null;
    }

    @Override
    public void detelePais(long id) throws BusinessException, NotFoundException {
        Optional<Pais> opt = null;
        try{
            opt = repository.findById(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("No se encontró el país: " + id);
        }else{
            try {
                repository.deleteById(id);
            }catch (Exception e1){
                throw new BusinessException(e1.getMessage());
            }
        }
    }

    @Override
    public Pais updatePais(Pais pPais) throws BusinessException, NotFoundException {
        Optional<Pais> opt = null;
        try{
            //cod_iso
            if(pPais.getCod_iso().isEmpty()){
                throw new BusinessException("Código ISO esta Vacío");
            }
            if(pPais.getCod_iso().length() != 2){
                throw new BusinessException("Utilice el código de 2 letras");
            }
            //nombre
            if(pPais.getNombre().isEmpty()){
                throw new BusinessException("Nombre País esta vacío");
            }
            if(pPais.getNombre().length() < 4){
                throw new BusinessException("Nombre País debe contener más de 4 caracteres");
            }
            if(pPais.getNombre().length() >56){
                throw new BusinessException("Nombre País debe contener menos de 56 caracteres");
            }
            //cod_area
            if(pPais.getCod_area().isEmpty()){
                throw new BusinessException("Código de área esta vacío");
            }
            if(pPais.getCod_area().length() < 1){
                throw new BusinessException("Código de área debe contener al menos un caracter");
            }
            if(pPais.getCod_area().length() > 3){
                throw new BusinessException("Código de área no debe contener mas de 3 caracteres");
            }
            Pattern pat = Pattern.compile("[\\d]*");
            Matcher mat = pat.matcher(pPais.getCod_area());
            if(!mat.matches()){
                throw new BusinessException("Código de área debe ser númerico (no es necesario utilizar '+')");
            }
            opt = repository.findById(pPais.getIdPais());
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("No se encontró el país:" + pPais.getIdPais());
        }else {
            try {
                Pais newPais = new Pais();
                newPais.setIdPais(pPais.getIdPais());
                newPais.setNombre(pPais.getNombre().toUpperCase());
                newPais.setCod_iso(pPais.getCod_iso().toUpperCase());
                newPais.setCod_area(pPais.getCod_area());
                return repository.save(newPais);
            }catch (Exception e1){
                throw new BusinessException(e1.getMessage());
            }
        }
    }
}
