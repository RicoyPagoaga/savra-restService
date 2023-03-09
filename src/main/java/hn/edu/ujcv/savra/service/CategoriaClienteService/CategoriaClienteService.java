package hn.edu.ujcv.savra.service.CategoriaClienteService;

import hn.edu.ujcv.savra.entity.CategoriaCliente;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.exceptions.SqlExceptions;
import hn.edu.ujcv.savra.repository.CategoriaClienteRepository;
import hn.edu.ujcv.savra.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CategoriaClienteService implements ICategoriaClienteService{
    private Log mi_log = new Log();
    @Autowired
    private CategoriaClienteRepository repository;

    @Override
    public CategoriaCliente saveCategoriaCliente(CategoriaCliente pCategoriaCliente) throws BusinessException, SqlExceptions {
        try{
            pCategoriaCliente.setNombre(pCategoriaCliente.getNombre().trim().toUpperCase());
            //nombre
            if(pCategoriaCliente.getNombre().trim().isEmpty()){
                throw new BusinessException("Nombre Categoría esta Vacío");
            }
            if(pCategoriaCliente.getNombre().trim().length() < 3){
                throw new BusinessException("Nombre Categoría debe tener mínimo 3 caracteres");
            }
            Pattern dobleEspacio = Pattern.compile("\\s{2,}");
            if (dobleEspacio.matcher(pCategoriaCliente.getNombre().trim()).find()) {
                throw new BusinessException("El nombre no debe contener espacios dobles");
            }
            if(pCategoriaCliente.getNombre().trim().length() >20){
                throw new BusinessException("Nombre Categoría debe contener menos de 20 caracteres");
            }
            Pattern patDoc = Pattern.compile("[a-zA-Z]*");
            Matcher matDoc = patDoc.matcher(pCategoriaCliente.getNombre().trim());
            if(!matDoc.matches()){
                throw new BusinessException("Nombre Categoría no debe contener números ఠ_ఠ");
            }
            List<CategoriaCliente> categorias = getCategoriaClientes();
            for (CategoriaCliente item : categorias) {
                if ((item.getNombre().equals(pCategoriaCliente.getNombre().trim())) &&
                        (item.getIdCategoria() != pCategoriaCliente.getIdCategoria())) {
                    throw new BusinessException("El nombre de la categoría de cliente ya está en uso");
                }
            }
            //descripcion
            if(pCategoriaCliente.getDescripcion().trim().isEmpty()){
                throw new BusinessException("Descripción de categoría esta vacío");
            }
            if(pCategoriaCliente.getDescripcion().trim().length() < 3){
                throw new BusinessException("Descripción debe tener mínimo 3 caracteres");
            }
            if (dobleEspacio.matcher(pCategoriaCliente.getDescripcion().trim()).find()) {
                throw new BusinessException("La Descripción no debe contener espacios dobles");
            }
            if(pCategoriaCliente.getDescripcion().trim().length() > 50){
                throw new BusinessException("Descripción debe debe contener menos de 50 caracteres");
            }

            return repository.save(pCategoriaCliente);
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<CategoriaCliente> getCategoriaClientes() throws BusinessException {
        return repository.findAll();
    }

    @Override
    public CategoriaCliente getCategoriaClienteById(long id) throws BusinessException, NotFoundException {
        Optional<CategoriaCliente> opt = null;
        try{
            opt = repository.findById(id);
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }if(!opt.isPresent()){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró la categoria: "+ id);
            throw new NotFoundException("No se encontró la categoria: "+ id);
        }
        return opt.get();
    }

    @Override
    public CategoriaCliente getCategoriaClienteByNombre(String nombre) throws BusinessException, NotFoundException {
        return null;
    }

    @Override
    public void deteleCategoriaCliente(long id) throws BusinessException, NotFoundException, SqlExceptions {
        Optional<CategoriaCliente> opt = null;
        try{
            opt = repository.findById(id);
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró la categoria: " + id);
            throw new NotFoundException("No se encontró la categoria: " + id);
        }else{
            try {
                repository.deleteById(id);
            }catch (Exception e1){
                mi_log.CrearArchivo(this.getClass().getSimpleName());
                mi_log.logger.severe(e1.getMessage());
                throw new BusinessException(e1.getMessage());
            }
        }
    }

    @Override
    public CategoriaCliente updateCategoriaCliente(CategoriaCliente pCategoriaCliente) throws BusinessException,NotFoundException , SqlExceptions {
        Optional<CategoriaCliente> opt = null;
        try{
            //nombre
            if(pCategoriaCliente.getNombre().trim().isEmpty()){
                throw new BusinessException("Nombre Categoría esta Vacío");
            }
            if(pCategoriaCliente.getNombre().trim().length() < 3){
                throw new BusinessException("Nombre Categoría debe tener mínimo 3 caracteres");
            }
            Pattern dobleEspacio = Pattern.compile("\\s{2,}");
            if (dobleEspacio.matcher(pCategoriaCliente.getNombre().trim()).find()) {
                throw new BusinessException("El nombre no debe contener espacios dobles");
            }
            if(pCategoriaCliente.getNombre().trim().length() >20){
                throw new BusinessException("Nombre Categoría debe contener menos de 20 caracteres");
            }
            Pattern patDoc = Pattern.compile("[a-zA-Z]*");
            Matcher matDoc = patDoc.matcher(pCategoriaCliente.getNombre().trim());
            if(!matDoc.matches()){
                throw new BusinessException("Nombre Categoría no debe contener números ఠ_ఠ");
            }
            List<CategoriaCliente> categorias = getCategoriaClientes();
            for (CategoriaCliente item : categorias) {
                if ((item.getNombre().toUpperCase().equals(pCategoriaCliente.getNombre().trim())) &&
                        (item.getIdCategoria() != pCategoriaCliente.getIdCategoria())) {
                    throw new BusinessException("El nombre de la categoría de cliente ya está en uso");
                }
            }
            //descripcion
            if(pCategoriaCliente.getDescripcion().trim().isEmpty()){
                throw new BusinessException("Descripción de categoría esta vacío");
            }
            if(pCategoriaCliente.getDescripcion().trim().length() < 3){
                throw new BusinessException("Descripción debe tener mínimo 3 caracteres");
            }
            if (dobleEspacio.matcher(pCategoriaCliente.getDescripcion().trim()).find()) {
                throw new BusinessException("La Descripción no debe contener espacios dobles");
            }
            if(pCategoriaCliente.getDescripcion().trim().length() > 50){
                throw new BusinessException("Descripción debe debe contener menos de 50 caracteres");
            }
            opt = repository.findById(pCategoriaCliente.getIdCategoria());
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró la categoria:" + pCategoriaCliente.getIdCategoria());
            throw new NotFoundException("No se encontró la categoria:" + pCategoriaCliente.getIdCategoria());
        }else {
            try {
                CategoriaCliente newCategoria = new CategoriaCliente();
                newCategoria.setIdCategoria(pCategoriaCliente.getIdCategoria());
                newCategoria.setNombre(pCategoriaCliente.getNombre().toUpperCase());
                newCategoria.setDescripcion(pCategoriaCliente.getDescripcion());
                return repository.save(newCategoria);
            }catch (Exception e1){
                mi_log.CrearArchivo(this.getClass().getSimpleName());
                mi_log.logger.severe(e1.getMessage());
                throw new BusinessException(e1.getMessage());
            }
        }
    }
}
