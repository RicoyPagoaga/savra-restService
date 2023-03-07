package hn.edu.ujcv.savra.service.ArqueoService;
import hn.edu.ujcv.savra.entity.Arqueo;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.ArqueoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ArqueoService implements IArqueoService {
    @Autowired
    private ArqueoRepository repository;
    @Override
    public Arqueo saveArqueo(Arqueo arqueo) throws BusinessException {
        try {

            validarArqueo(arqueo);
            return repository.save(arqueo);
        }catch (Exception e){

            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Arqueo> saveArqueos(List<Arqueo> arqueos) throws BusinessException {
        try {

            return repository.saveAll(arqueos);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Arqueo> getArqueos() throws BusinessException {
        try {
            return repository.findAll();
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Arqueo getArqueoById(long id) throws BusinessException, NotFoundException {
        Optional<Arqueo> opt= null;
        try {
            opt=repository.findById(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("No se encontro el arqueo"+id);
        }
        return opt.get();
    }

    @Override
    public Arqueo getArqueoByObservacion(String observacion) throws BusinessException, NotFoundException {
        Optional<Arqueo> opt= null;
        try {
            opt=repository.findArqueoByObservacion(observacion);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el arqueo :v"+observacion);
        }
        return opt.get();
    }

    @Override
    public void deleteArqueo(long id) throws BusinessException, NotFoundException {

        Optional<Arqueo> opt= null;
        try {
            opt=repository.findById(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el arqueo"+id);
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
    public Arqueo udateArqueo(Arqueo arqueo) throws BusinessException, NotFoundException {
        Optional<Arqueo> opt= null;
        try {
            opt=repository.findById(arqueo.getIdArqueo());
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el Arqueo"+arqueo.getIdArqueo());
        }
        else {
            try {
                validarArqueo(arqueo);
                Arqueo existingArqueo = new Arqueo();
                existingArqueo.setIdArqueo(arqueo.getIdArqueo());
                existingArqueo.setFecha(arqueo.getFecha());
                existingArqueo.setEmpleado(arqueo.getEmpleado());
                existingArqueo.setTotalRecuento(arqueo.getTotalRecuento());
                existingArqueo.setObservacion(arqueo.getObservacion());
                return repository.save(existingArqueo);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
    }
    private void validarArqueo(Arqueo arqueo) throws BusinessException{

        //Fecha
        if (arqueo.getFecha()==null){
            throw new BusinessException("Fecha no debe estar vacio");
        }
        if(arqueo.getIdArqueo() == 0){
            if (!arqueo.getFecha().equals(LocalDate.now())){
                throw new BusinessException("La fecha del arqueo debe ser del dia actual");
            }
        }
        //total recuento
        if (arqueo.getTotalRecuento()>1000000){
            throw new BusinessException("Total recuento no debe ser mayor a un millon");
        }
        if (arqueo.getTotalRecuento()<=0){
            throw new BusinessException("Total recuento no debe ser menor o igual a 0");
        }
        //observacion
        Pattern dobleEspacio = Pattern.compile("\\s{2,}");
        if (arqueo.getObservacion().trim().isEmpty()){
            throw new BusinessException("La observación no debe estar vacía");
        }
        if(arqueo.getObservacion().trim().length() < 3){
            throw new BusinessException("Observación debe contener mínimo 3 carácteres ఠ_ఠ");
        }
        if (dobleEspacio.matcher(arqueo.getObservacion().trim()).find()) {
            throw new BusinessException("Observación no debe contener espacios dobles ఠ_ఠ");
        }
        if(arqueo.getObservacion().trim().length() >80){
            throw new BusinessException("Observación no debe contener mas de 80 carácteres ఠ_ఠ");
        }
    }
}
