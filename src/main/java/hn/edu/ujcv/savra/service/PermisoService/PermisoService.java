package hn.edu.ujcv.savra.service.PermisoService;

import hn.edu.ujcv.savra.entity.Cargo;
import hn.edu.ujcv.savra.entity.Permiso;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PermisoService implements IPermisoService{
    @Autowired
    private PermisoRepository repository;
    @Override
    public Permiso savePermiso(Permiso permiso) throws BusinessException {
        try {
            permiso.setNombre(permiso.getNombre().toUpperCase());
            Optional<Permiso> opt=null;
            opt=repository.findPermisoByNombre(permiso.getNombre());
            if (opt.isPresent()){
                throw new BusinessException("Permiso ya existe!!");
            }
            validarPermisos(permiso);
            return repository.save(permiso);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Permiso> savePermisos(List<Permiso> permisos) throws BusinessException {
        try {
            for (Permiso permiso1:permisos) {
                validarPermisos(permiso1);

            }
            return repository.saveAll(permisos);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Permiso> getPermisos() throws BusinessException {
        try {
            return repository.findAll();
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Permiso getPermisoById(long id) throws BusinessException, NotFoundException {
        Optional<Permiso> opt= null;
        try {
            opt=repository.findById(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }if (!opt.isPresent()){
            throw new NotFoundException("No se encontr?? el permiso"+id);
        }
        return opt.get();
    }

    @Override
    public Permiso getPermisoByNombre(String nombre) throws BusinessException, NotFoundException {
        Optional<Permiso> opt= null;
        try {
            opt=repository.findPermisoByNombre(nombre);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el cargo :v"+nombre);
        }
        return opt.get();
    }

    @Override
    public void deletePermiso(long id) throws BusinessException, NotFoundException {
        Optional<Permiso> opt= null;
        try {
            opt=repository.findById(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el permiso"+id);
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
    public Permiso updatePermiso(Permiso permiso) throws BusinessException, NotFoundException {
        Optional<Permiso> opt= null;
        try {
            opt=repository.findById(permiso.getIdPermiso());
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el permiso"+permiso.getIdPermiso());
        }
        else {
            try {
                validarPermisos(permiso);
                Permiso existingPermiso =new Permiso();
                existingPermiso.setIdPermiso(permiso.getIdPermiso());
                existingPermiso.setNombre(permiso.getNombre());
                existingPermiso.setDescripcion(permiso.getDescripcion());
                permiso.setNombre(permiso.getNombre().toUpperCase());
                return repository.save(existingPermiso);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void validarPermisos(Permiso permiso) throws BusinessException{
        //Nombre
        if (permiso.getNombre().trim().isEmpty()){
            throw new BusinessException("El nombre no debe estar vacio");
        }
        if (permiso.getNombre().trim().length()>50){
            throw new BusinessException("El nombre no debe contener mas de 50 car??cteres ???_???");
        }
        if (permiso.getNombre().trim().length()<4){
            throw new BusinessException("El nombre debe contener minimo 4 car??cteres ???_???");
        }
        Pattern patDoc = Pattern.compile("^([a-zA-Z]+)(\\s[a-zA-Z]+)*$");
        Matcher matDoc = patDoc.matcher(permiso.getNombre().trim());
        if(!matDoc.matches()){
            throw new BusinessException("Nombre Cargo no debe contener n??meros o car??cteres especiales ???_???");
        }

        //Descripcion
        //Pattern expresion= Pattern.compile("^[a-zA-Z]+(.|\\s)*$");
        Pattern patDocc = Pattern.compile("/[A-Z]+$/");
        Matcher matDocc = patDocc.matcher(permiso.getDescripcion().trim());
        if(!matDocc.matches()){
            throw new BusinessException("Descripci??n no debe contener caracteres especiales ni espacios duplicados ???_???");
        }
        if(permiso.getDescripcion().trim().isEmpty()){
            throw new BusinessException("La descripci??n no debe estar vacia ???_???");
        }
        if (permiso.getDescripcion().trim().length()>50){
            throw new BusinessException("La descripci??n no debe ser mayor a 50 ???_???");
        }
        if (permiso.getDescripcion().trim().length()<3){
            throw new BusinessException("La descripci??n no debe contener menos de 3 caracteres ???_???");
        }
    }
}
