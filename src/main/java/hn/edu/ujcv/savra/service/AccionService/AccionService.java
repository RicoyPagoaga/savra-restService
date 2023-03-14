package hn.edu.ujcv.savra.service.AccionService;

import hn.edu.ujcv.savra.entity.Accion;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.AccionRepository;
import hn.edu.ujcv.savra.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AccionService implements IAccionService {
    private Log mi_log = new Log();
    @Autowired
    private AccionRepository repository;
    @Override
    public Accion saveAccion(Accion accion) throws BusinessException {
        try {
            accion.setNombre(accion.getNombre().toUpperCase());
            Optional<Accion> opt=null;
            opt=repository.findAccionByNombre(accion.getNombre());
            if (opt.isPresent()){
                throw new BusinessException("Permiso ya existe!!");
            }
            validarAcciones(accion);
            return repository.save(accion);
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Accion> saveAccions(List<Accion> accions) throws BusinessException {
        try {
            for (Accion accion1 : accions) {
                validarAcciones(accion1);

            }
            return repository.saveAll(accions);
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Accion> getAccion() throws BusinessException {
        try {
            return repository.findAll();
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Accion getAccionById(long id) throws BusinessException, NotFoundException {
        Optional<Accion> opt= null;
        try {
            opt=repository.findById(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }if (!opt.isPresent()){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró el permiso"+id);
            throw new NotFoundException("No se encontró el permiso"+id);
        }
        return opt.get();
    }

    @Override
    public Accion getAccionByNombre(String nombre) throws BusinessException, NotFoundException {
        Optional<Accion> opt= null;
        try {
            opt=repository.findAccionByNombre(nombre);
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("no se encontro el cargo :v"+nombre);
            throw new NotFoundException("no se encontro el cargo :v"+nombre);
        }
        return opt.get();
    }

    @Override
    public void deleteAccion(long id) throws BusinessException, NotFoundException {
        Optional<Accion> opt= null;
        try {
            opt=repository.findById(id);
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("no se encontro el permiso"+id);
            throw new NotFoundException("no se encontro el permiso"+id);
        }
        else {
            try {
                repository.deleteById(id);
            }catch (Exception e){
                mi_log.CrearArchivo(this.getClass().getSimpleName());
                mi_log.logger.severe(e.getMessage());
                throw new BusinessException(e.getMessage());
            }
        }
    }

    @Override
    public Accion updateAccion(Accion accion) throws BusinessException, NotFoundException {
        Optional<Accion> opt= null;
        try {
            opt=repository.findById(accion.getIdAccion());
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("no se encontro el permiso"+ accion.getIdAccion());
            throw new NotFoundException("no se encontro el permiso"+ accion.getIdAccion());
        }
        else {
            try {
                validarAcciones(accion);
                Accion existingAccion =new Accion();
                existingAccion.setIdAccion(accion.getIdAccion());
                existingAccion.setNombre(accion.getNombre());
                accion.setNombre(accion.getNombre().toUpperCase());
                return repository.save(existingAccion);
            }catch (Exception e){
                mi_log.CrearArchivo(this.getClass().getSimpleName());
                mi_log.logger.severe(e.getMessage());
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void validarAcciones(Accion accion) throws BusinessException{
        //Nombre
        if (accion.getNombre().trim().isEmpty()){
            throw new BusinessException("El nombre no debe estar vacio");
        }
        if (accion.getNombre().trim().length()>50){
            throw new BusinessException("El nombre no debe contener mas de 50 carácteres ఠ_ఠ");
        }
        if (accion.getNombre().trim().length()<4){
            throw new BusinessException("El nombre debe contener minimo 4 carácteres ఠ_ఠ");
        }
        Pattern patDoc = Pattern.compile("^([a-zA-Z]+)(\\s[a-zA-Z]+)*$");
        Matcher matDoc = patDoc.matcher(accion.getNombre().trim());
        if(!matDoc.matches()){
            throw new BusinessException("Nombre Cargo no debe contener números o carácteres especiales ఠ_ఠ");
        }

        //Descripcion
       /* if(accion.getDescripcion().trim().isEmpty()){
            throw new BusinessException("La descripción no debe estar vacia ఠ_ఠ");
        }
        if (accion.getDescripcion().trim().length()>50){
            throw new BusinessException("La descripción no debe ser mayor a 50 ఠ_ఠ");
        }
        if (accion.getDescripcion().trim().length()<3){
            throw new BusinessException("La descripción no debe contener menos de 3 caracteres ఠ_ఠ");
        }
        Pattern pat3 = Pattern.compile("[a-zA-ZñÑáéíóúÁÉÍÓÚ ]*");
        Matcher matDoc1 = pat3.matcher(accion.getDescripcion().trim());
        if(!matDoc1.matches()){
            throw new BusinessException("Descripción no debe contener números o caracteres especiales.");
        }
        Pattern dobleEspacio = Pattern.compile("\\s{2,}");
        if (dobleEspacio.matcher(accion.getDescripcion().trim()).find()) {
            throw new BusinessException("Descripción no debe contener espacios dobles.");
        }*/
    }
}
