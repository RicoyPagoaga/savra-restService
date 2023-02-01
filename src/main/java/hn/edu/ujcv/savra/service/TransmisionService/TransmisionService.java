package hn.edu.ujcv.savra.service.TransmisionService;

import hn.edu.ujcv.savra.entity.Transmision;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.TransmisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TransmisionService implements ITransmisionService{
    @Autowired
    TransmisionRepository repository;
    @Override
    public Transmision saveTransmision(Transmision pTransmision) throws BusinessException, SQLException {
        try {
            //nombre
            if (pTransmision.getNombre().trim().isEmpty()){
                throw new BusinessException("Nombre de la transmisión esta vacío!");
            }
            if (pTransmision.getNombre().length() > 12) {
                throw new BusinessException("Nombre de la transmisión muy larga!");
            }
            if (pTransmision.getNombre().length() < 6) {
                throw new BusinessException("Nombre de la transmisión debe contener minimo 6 carácteres!");
            }
            Pattern patDoc = Pattern.compile("^([a-zA-Z]+)(\\s[a-zA-Z]+)*$");
            Matcher matDoc = patDoc.matcher(pTransmision.getNombre().trim());
            if(!matDoc.matches()){
                throw new BusinessException("Nombre de la transmisión no debe contener números o caracteres especialesఠ_ఠ");
            }
            pTransmision.setNombre(pTransmision.getNombre().toUpperCase());
            return repository.save(pTransmision);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Transmision> getTransmisiones() throws BusinessException {
        return repository.findAll();
    }

    @Override
    public Transmision getTransmisionById(long id) throws BusinessException, NotFoundException {
        return null;
    }

    @Override
    public Transmision getTransmisionByNombre(String nombre) throws BusinessException, NotFoundException {
        return null;
    }

    @Override
    public void deteleTransmision(long id) throws BusinessException, NotFoundException {
        Optional<Transmision> opt = null;
        try {
            opt = repository.findById(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("No se encontró la transmision: " + id);
        }else {
            try {
                repository.deleteById(id);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
    }

    @Override
    public Transmision updateTransmision(Transmision pTransmision) throws BusinessException, NotFoundException {
        Optional<Transmision> opt = null;
        try {
            opt = repository.findById(pTransmision.getIdTransmision());
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("No se encontró la transmision: " + pTransmision.getIdTransmision());
        }else {
            try {
                //nombre
                if (pTransmision.getNombre().isEmpty()){
                    throw new BusinessException("Nombre de la transmisión esta vacío!");
                }
                if (pTransmision.getNombre().length() > 12) {
                    throw new BusinessException("Nombre de la transmisión muy larga!");
                }
                if (pTransmision.getNombre().length() < 6) {
                    throw new BusinessException("Nombre de la transmisión debe contener minimo 6 carácteres!");
                }
                Pattern pat = Pattern.compile("[\\d]*");
                Matcher mat = pat.matcher(pTransmision.getNombre().trim());
                if (mat.matches()){
                    throw new BusinessException("Nombre de la transmisión no debe contener digitos");
                }
                Transmision nuevaTransmison = new Transmision();
                nuevaTransmison.setIdTransmision(pTransmision.getIdTransmision());
                nuevaTransmison.setNombre(pTransmision.getNombre().toUpperCase());
                return repository.save(nuevaTransmison);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
    }
}
