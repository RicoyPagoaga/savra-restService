package hn.edu.ujcv.savra.service.TransmisionService;

import hn.edu.ujcv.savra.entity.TipoDocumento;
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
            pTransmision.setNombre(pTransmision.getNombre().toUpperCase());
            validarTransmision(pTransmision);
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
                pTransmision.setNombre(pTransmision.getNombre().toUpperCase());

                validarTransmision(pTransmision);
                Transmision nuevaTransmison = new Transmision();
                nuevaTransmison.setIdTransmision(pTransmision.getIdTransmision());
                return repository.save(nuevaTransmison);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
    }
    private void validarTransmision(Transmision pTransmision)throws BusinessException {
        //nombre
        if (pTransmision.getNombre().trim().isEmpty()){
            throw new BusinessException("Nombre de la transmisión esta vacío!");
        }
        if (pTransmision.getNombre().trim().length() > 18) {
            throw new BusinessException("Nombre de la transmisión muy largo! no debe contener más de 18 caracteres");
        }
        if (pTransmision.getNombre().trim().length() < 6) {
            throw new BusinessException("Nombre de la transmisión debe contener minimo 6 carácteres!");
        }
        Pattern pat3 = Pattern.compile("[a-zA-Z ]*");
        Matcher matDoc = pat3.matcher(pTransmision.getNombre().trim());
        if(!matDoc.matches()){
            throw new BusinessException("Nombre no debe contener números o caracteres especiales.");
        }
        Pattern dobleEspacio = Pattern.compile("\\s{2,}");
        if (dobleEspacio.matcher(pTransmision.getNombre().trim()).find()) {
            throw new BusinessException("Nombre no debe contener espacios dobles.");
        }
        List<Transmision> transmisiones = getTransmisiones();

        for (Transmision item : transmisiones) {
            if (item.getNombre().equalsIgnoreCase(pTransmision.getNombre().trim()) && item.getIdTransmision() != pTransmision.getIdTransmision()) {
                throw new BusinessException("El nombre de la transmisión ya está en uso");
            }
        }
    }
}
