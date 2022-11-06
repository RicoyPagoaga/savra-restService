package hn.edu.ujcv.savra.service.Modelo;

import hn.edu.ujcv.savra.entity.Marca;
import hn.edu.ujcv.savra.entity.Modelo;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.MarcaRepository;
import hn.edu.ujcv.savra.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModeloService implements IModeloService {

    @Autowired
    private ModeloRepository repository;

    @Override
    public Modelo saveModelo(Modelo modelo) throws BusinessException {
        try {
            validarModelo(modelo);
            return repository.save(modelo);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Modelo> saveModelos(List<Modelo> modelos) throws BusinessException {
        try {
            for (Modelo item : modelos) {
                validarModelo(item);
            }
            return repository.saveAll(modelos);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Modelo> getModelos() throws BusinessException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Modelo getModeloById(long id) throws BusinessException, NotFoundException {
        Optional<Modelo> opt=null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el modelo " + id);
        }
        return opt.get();
    }

    @Override
    public Modelo getModeloByNombre(String nombre) throws BusinessException, NotFoundException {
        Optional<Modelo> opt=null;
        try {
            opt = repository.findFirstByNombre(nombre);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el modelo " + nombre);
        }
        return opt.get();
    }

    @Override
    public void deleteModelo(long id) throws BusinessException, NotFoundException {
        Optional<Modelo> opt=null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el modelo " + id);
        } else {
            try {
                repository.deleteById(id);
            } catch (Exception e1) {
                throw new BusinessException(e1.getMessage());
            }
        }
    }

    @Override
    public Modelo updateModelo(Modelo modelo) throws BusinessException, NotFoundException {
        Optional<Modelo> opt=null;
        try {
            if (String.valueOf(modelo.getIdModelo()).isEmpty()) {
                throw new BusinessException("El Id de Modelo no debe estar vacio");
            }
            if (modelo.getIdModelo() < 0) {
                throw new BusinessException("Id de Modelo invalido");
            }
            opt = repository.findById(modelo.getIdModelo());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el modelo " + modelo.getIdModelo());
        } else{
            try {
                validarModelo(modelo);
                Modelo modeloExistente = new Modelo(
                        modelo.getIdModelo(), modelo.getNombre(),
                        modelo.getIdMarca()
                );
                return repository.save(modeloExistente);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void validarModelo(Modelo modelo) throws BusinessException, NotFoundException {
        //nombre
        if (modelo.getNombre().isEmpty()) {
            throw new BusinessException("El nombre del modelo no debe estar vacio");
        }
        if (modelo.getNombre().trim().length() < 5) {
            throw new BusinessException("Ingrese mas de 5 caracteres en el nombre del modelo");
        }
        if (modelo.getNombre().trim().length() > 80) {
            throw new BusinessException("El nombre del modelo es demasiado largo");
        }
        //id marca
        if (String.valueOf(modelo.getIdMarca()).isEmpty()) {
            throw new BusinessException("La marca es requerida");
        }
        if (modelo.getIdMarca() < 0) {
            throw new BusinessException("Marca invalida");
        }
        if (!validarMarca(modelo.getIdMarca())) {
            throw new NotFoundException("Indique la marca");
        }

    }

    @Autowired
    private MarcaRepository marcaRepository;
    private boolean validarMarca(long id) {
        boolean condicion=false;
        List<Marca> marcas = marcaRepository.findAll();
        for (Marca item : marcas) {
            if (item.getIdMarca() == id) {
                condicion=true;
                break;
            }
        }
        return condicion;
    }
}
