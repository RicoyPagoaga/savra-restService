package hn.edu.ujcv.savra.service.CategoriaRepuestoService;

import hn.edu.ujcv.savra.entity.CategoriaRepuesto;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.CategoriaRepuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaRepuestoService implements ICategoriaRepuestoService {
    @Autowired
    private CategoriaRepuestoRepository repository;

    @Override
    public CategoriaRepuesto saveCategoriaRepuesto(CategoriaRepuesto categoriaRepuesto) throws BusinessException {
        try {
            validarCategoria(categoriaRepuesto);
            return repository.save(categoriaRepuesto);
        } catch (Exception e) {
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
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<CategoriaRepuesto> getCategoriasRepuesto() throws BusinessException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public CategoriaRepuesto getCategoriaRepuestoById(long id) throws BusinessException, NotFoundException {
        Optional<CategoriaRepuesto> opt=null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
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
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
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
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró la categoría de repuesto: " + id);
        } else {
            try {
                repository.deleteById(id);
            } catch (Exception e) {
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
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró la categoría de repuesto: " + categoriaRepuesto.getIdCategoria());
        } else {
            try {
                validarCategoria(categoriaRepuesto);
                CategoriaRepuesto categoriaExistente = new CategoriaRepuesto(
                       categoriaRepuesto.getIdCategoria(), categoriaRepuesto.getNombre(),
                        categoriaRepuesto.getDescripcion()
                );
                return repository.save(categoriaExistente);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void validarCategoria(CategoriaRepuesto categoriaRepuesto) throws BusinessException {
        //nombre
        if (categoriaRepuesto.getNombre().isEmpty()) {
            throw new BusinessException("El nombre de la categoría de repuesto no debe estar vacío");
        }
        if (categoriaRepuesto.getNombre().trim().length() < 3) {
            throw new BusinessException("Ingrese más de tres caracteres en el nombre de la categoría de repuesto");
        }
        if (categoriaRepuesto.getNombre().trim().length() > 80) {
            throw new BusinessException("El nombre de la categoría de repuesto no debe exceder los ochenta caracteres");
        }
        //descripcion
        if (categoriaRepuesto.getDescripcion().isEmpty()) {
            throw new BusinessException("La descripción es requerida");
        }
        if (categoriaRepuesto.getDescripcion().trim().length() < 5) {
            throw new BusinessException("La descripción debe contener mínimo cinco caracteres");
        }
        if (categoriaRepuesto.getDescripcion().trim().length() > 100) {
            throw new BusinessException("La descripción no debe exceder los cien caracteres");
        }
        List<CategoriaRepuesto> categorias = getCategoriasRepuesto();
        for (CategoriaRepuesto item : categorias) {
            if ((item.getNombre().equals(categoriaRepuesto.getNombre())) && (item.getIdCategoria() != categoriaRepuesto.getIdCategoria())) {
                throw new BusinessException("El nombre de la categoría de repuesto ya está en uso");
            }
        }
    }
}
