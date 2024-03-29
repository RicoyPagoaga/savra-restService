package hn.edu.ujcv.savra.service.CategoriaRepuestoService;

import hn.edu.ujcv.savra.entity.CategoriaRepuesto;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.CategoriaRepuestoRepository;
import hn.edu.ujcv.savra.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class CategoriaRepuestoService implements ICategoriaRepuestoService {

    private Log mi_log = new Log();
    private final String clase = this.getClass().getSimpleName();
    @Autowired
    private CategoriaRepuestoRepository repository;

    @Override
    public CategoriaRepuesto saveCategoriaRepuesto(CategoriaRepuesto categoriaRepuesto) throws BusinessException {
        try {
            categoriaRepuesto.setNombre(categoriaRepuesto.getNombre().trim());
            categoriaRepuesto.setDescripcion(categoriaRepuesto.getDescripcion().trim());
            validarCategoria(categoriaRepuesto);
            return repository.save(categoriaRepuesto);
        } catch (Exception e) {
            mi_log.CrearArchivo(clase);
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<CategoriaRepuesto> saveCategoriasRepuesto(List<CategoriaRepuesto> categoriasRepuesto) throws BusinessException {
        try {
            for (CategoriaRepuesto item: categoriasRepuesto) {
                validarCategoria(item);
            }
            return repository.saveAll(categoriasRepuesto);
        } catch (Exception e){
            mi_log.CrearArchivo(clase);
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<CategoriaRepuesto> getCategoriasRepuesto() throws BusinessException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            mi_log.CrearArchivo(clase);
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public CategoriaRepuesto getCategoriaRepuestoById(long id) throws BusinessException, NotFoundException {
        Optional<CategoriaRepuesto> opt=null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            mi_log.CrearArchivo(clase);
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            mi_log.CrearArchivo(clase);
            mi_log.logger.severe("No se encontró la categoría de repuesto: " + id);
            throw new NotFoundException("No se encontró la categoría de repuesto: " + id);
        }
        return opt.get();
    }

    @Override
    public CategoriaRepuesto getCategoriaRepuestoByNombre(String nombre) throws BusinessException, NotFoundException {
        Optional<CategoriaRepuesto> opt=null;
        try {
            opt = repository.findByNombre(nombre);
        } catch (Exception e) {
            mi_log.CrearArchivo(clase);
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            mi_log.CrearArchivo(clase);
            mi_log.logger.severe("No se encontró la categoría de repuesto: " + nombre);
            throw new NotFoundException("No se encontró la categoría de repuesto: " + nombre);
        }
        return opt.get();
    }

    @Override
    public void deleteCategoriaRepuesto(long id) throws BusinessException, NotFoundException {
        Optional<CategoriaRepuesto> opt = null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            mi_log.CrearArchivo(clase);
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            mi_log.CrearArchivo(clase);
            mi_log.logger.severe("No se encontró la categoría de repuesto: " + id);
            throw new NotFoundException("No se encontró la categoría de repuesto: " + id);
        } else {
            try {
                repository.deleteById(id);
            } catch (Exception e) {
                mi_log.CrearArchivo(clase);
                mi_log.logger.severe(e.getMessage());
                throw new BusinessException(e.getMessage());
            }
        }
    }

    @Override
    public CategoriaRepuesto updateCategoriaRepuesto(CategoriaRepuesto categoriaRepuesto) throws BusinessException, NotFoundException {
        Optional<CategoriaRepuesto> opt = null;
        try {
            if (String.valueOf(categoriaRepuesto.getIdCategoria()).isEmpty()) {
                throw new BusinessException("El id de la Categoría no debe estar vacío");
            }
            if (categoriaRepuesto.getIdCategoria() < 0) {
                throw new BusinessException("Id de Categoría inválido");
            }
            opt = repository.findById(categoriaRepuesto.getIdCategoria());
        } catch (Exception e) {
            mi_log.CrearArchivo(clase);
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            mi_log.CrearArchivo(clase);
            mi_log.logger.severe("No se encontró la categoría de repuesto: " + categoriaRepuesto.getIdCategoria());
            throw new NotFoundException("No se encontró la categoría de repuesto: " + categoriaRepuesto.getIdCategoria());
        } else {
            try {
                validarCategoria(categoriaRepuesto);
                CategoriaRepuesto categoriaExistente = new CategoriaRepuesto(
                       categoriaRepuesto.getIdCategoria(), categoriaRepuesto.getNombre().trim(),
                        categoriaRepuesto.getDescripcion().trim()
                );
                return repository.save(categoriaExistente);
            } catch (Exception e) {
                mi_log.CrearArchivo(clase);
                mi_log.logger.severe(e.getMessage());
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void validarCategoria(CategoriaRepuesto categoriaRepuesto) throws BusinessException {
        //nombre
        if (categoriaRepuesto.getNombre().trim().isEmpty()) {
            throw new BusinessException("El nombre de la categoría de repuesto no debe estar vacío");
        }
        if (categoriaRepuesto.getNombre().trim().length() < 3) {
            throw new BusinessException("Ingrese más de tres caracteres en el nombre de la categoría de repuesto");
        }
        if (categoriaRepuesto.getNombre().trim().length() > 80) {
            throw new BusinessException("El nombre de la categoría de repuesto no debe exceder los ochenta caracteres");
        }
        Pattern patDoc = Pattern.compile("[0-9]+");
        boolean matDoc = patDoc.matcher(categoriaRepuesto.getNombre().trim()).find();
        if(matDoc){
            throw new BusinessException("Nombre de categoría no debe contener números ఠ_ఠ");
        }
        Pattern dobleEspacio = Pattern.compile("\\s{2,}");
        if (dobleEspacio.matcher(categoriaRepuesto.getNombre().trim()).find()) {
            throw new BusinessException("Nombre de categoría no debe contener espacios dobles ఠ_ఠ");
        }
        String[] nombre = categoriaRepuesto.getNombre().trim().split(" ");
        for (String item: nombre) {
            if(item.matches("(.)\\1{2,}")) {
                throw new BusinessException("El nombre no debe tener tantas letras repetidas ఠ_ఠ");
            }
        }
        List<CategoriaRepuesto> categorias = getCategoriasRepuesto();
        for (CategoriaRepuesto item : categorias) {
            if ((item.getNombre().equals(categoriaRepuesto.getNombre().trim())) &&
                    (item.getIdCategoria() != categoriaRepuesto.getIdCategoria())) {
                throw new BusinessException("El nombre de la categoría de repuesto ya está en uso");
            }
        }
        //descripcion
        if (categoriaRepuesto.getDescripcion().trim().isEmpty()) {
            throw new BusinessException("La descripción es requerida");
        }
        if (categoriaRepuesto.getDescripcion().trim().length() < 5) {
            throw new BusinessException("La descripción debe contener mínimo cinco caracteres");
        }
        if (categoriaRepuesto.getDescripcion().trim().length() > 100) {
            throw new BusinessException("La descripción no debe exceder los cien caracteres");
        }
        if (dobleEspacio.matcher(categoriaRepuesto.getDescripcion().trim()).find()) {
            throw new BusinessException("Descripción de categoría no debe contener espacios dobles ఠ_ఠ");
        }
        String[] descripcion = categoriaRepuesto.getDescripcion().trim().split(" ");
        for (String item: descripcion) {
            if(item.matches("(.)\\1{2,}")) {
                throw new BusinessException("La descripción no debe tener tantas letras repetidas ఠ_ఠ");
            }
        }
    }
}
