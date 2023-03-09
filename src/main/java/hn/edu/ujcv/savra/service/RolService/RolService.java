package hn.edu.ujcv.savra.service.RolService;

import hn.edu.ujcv.savra.entity.Rol;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.RolRepository;
import hn.edu.ujcv.savra.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class RolService implements IRolService{
    private Log mi_log = new Log();
    @Autowired
    private RolRepository repository;
    @Override
    public Rol saveRol(Rol rol) throws BusinessException {
        try {
            //Instancia del metodo que contiene las validaciones
            validarRoles(rol);

            return repository.save(rol);
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Rol> saveRoles(List<Rol> rols) throws BusinessException {
        try {
            for (Rol roles:rols) {
                if (roles.getNombre().trim().isEmpty()){
                    throw new BusinessException("El nombre no debe estar vacio");
                }
            }
            return repository.saveAll(rols);
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Rol> getRol() throws BusinessException {
        try {
            return repository.findAll();
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Rol getRolById(long id) throws BusinessException, NotFoundException {
        Optional<Rol> opt=null;
        try {
            opt=repository.findById(id);
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }if (!opt.isPresent()){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("no se encontro el Rol"+id);
            throw new NotFoundException("no se encontro el Rol"+id);
        }
        return opt.get();
    }

    @Override
    public Rol getRolByNombre(String nombre) throws BusinessException, NotFoundException {
        return null;
    }

    @Override
    public void deleteRol(long id) throws BusinessException, NotFoundException {
        Optional<Rol> opt= null;
        try {
            opt=repository.findById(id);
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("no se encontro el Rol v;"+id);
            throw new NotFoundException("no se encontro el Rol v;"+id);
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
    public Rol updateRol(Rol rol) throws BusinessException, NotFoundException {
        Optional<Rol> opt= null;
        try {
            opt=repository.findById(rol.getIdRol());
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("no se encontro el documento v;"+ rol.getIdRol());
            throw new NotFoundException("no se encontro el documento v;"+ rol.getIdRol());
        }
        else {
            try {
                validarRoles(rol);
                Rol existingRol = new Rol();
                existingRol.setIdRol(rol.getIdRol());
                existingRol.setNombre(rol.getNombre());
                existingRol.setDescripcion(rol.getDescripcion());
                return repository.save(existingRol);
            }catch (Exception e){
                mi_log.CrearArchivo(this.getClass().getSimpleName());
                mi_log.logger.severe(e.getMessage());
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void validarRoles(Rol  rol) throws BusinessException {
        //Nombre Rol
        if(rol.getNombre().trim().isEmpty()){
            throw new BusinessException("El nombre no debe estar vacio ఠ_ఠ");
        }
        if(rol.getNombre().trim().length()< 3){
            throw new BusinessException("El nombre no debe tener menos de 3 caracteres ఠ_ఠ");
        }
        if (rol.getNombre().trim().length()>30){
            throw new BusinessException("El nombre no debe tener mas de 40 caracteres ఠ_ఠ");
        }
        Pattern dobleEspacio = Pattern.compile("\\s{2,}");
        if (dobleEspacio.matcher(rol.getNombre()).find()) {
            throw new BusinessException("Nombre de rol no debe contener espacios dobles ఠ_ఠ");
        }
        if (rol.getNombre().trim().matches("(.)\\1{2,}")){
            throw new BusinessException("No debe tener tantas letras repetidas ఠ_ఠ");
        }
        if (rol.getNombre().trim().matches("[^A-Za-zÁÉÍÓÚáéíóúñ\\s]")){
            throw new BusinessException("El nombre solo debe tener letras ఠ_ఠ");
        }
        Pattern patDoc = Pattern.compile("^([a-zA-Z]+)(\\s[a-zA-Z]+)*$");
        Matcher matDoc = patDoc.matcher(rol.getNombre().trim());
        if(!matDoc.matches()){
            throw new BusinessException("El Nombre no debe contener números o caracteres especialesఠ_ఠ");
        }

        //Descripcion
        if(rol.getDescripcion().trim().isEmpty()){
            throw new BusinessException("La Descripcion no debe estar vacio ఠ_ఠ");
        }
        if(rol.getDescripcion().trim().length()< 3){
            throw new BusinessException("La descripción no debe tener menos de 3 caracteres ఠ_ఠ");
        }
        if (rol.getDescripcion().trim().length()>50){
            throw new BusinessException("El descripción no debe tener mas de 50 caracteres ఠ_ఠ");
        }
        Pattern dobleEspacio2 = Pattern.compile("\\s{2,}");
        if (dobleEspacio2.matcher(rol.getNombre()).find()) {
            throw new BusinessException("Descripción de rol no debe contener espacios dobles ఠ_ఠ");
        }
        if (rol.getNombre().trim().matches("(.)\\1{2,}")){
            throw new BusinessException("No debe tener tantas letras repetidas ఠ_ఠ");
        }

        Pattern patDoc1 = Pattern.compile("^([a-zA-Z]+)(\\s[a-zA-Z]+)*$");
        Matcher matDoc1 = patDoc1.matcher(rol.getDescripcion().trim());
        if(!matDoc1.matches()){
            throw new BusinessException("La descripción no debe contener números o caracteres especialesఠ_ఠ");
        }
    }
}
