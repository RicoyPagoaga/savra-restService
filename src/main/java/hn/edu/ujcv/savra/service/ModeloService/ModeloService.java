package hn.edu.ujcv.savra.service.ModeloService;

import hn.edu.ujcv.savra.entity.Marca;
import hn.edu.ujcv.savra.entity.Modelo;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.MarcaRepository;
import hn.edu.ujcv.savra.repository.ModeloRepository;
import hn.edu.ujcv.savra.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ModeloService implements IModeloService {
    private Log mi_log = new Log();
    @Autowired
    private ModeloRepository repository;

    @Override
    public Modelo saveModelo(Modelo modelo) throws BusinessException {
        try {
            modelo.setNombre(modelo.getNombre().trim().toUpperCase());
            validarModelo(modelo);
            return repository.save(modelo);
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
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
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Modelo> getModelos() throws BusinessException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Modelo getModeloById(long id) throws BusinessException, NotFoundException {
        Optional<Modelo> opt=null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró el modelo " + id);
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
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró el modelo " + nombre);
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
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró el modelo " + id);
            throw new NotFoundException("No se encontró el modelo " + id);
        } else {
            try {
                repository.deleteById(id);
            } catch (Exception e1) {
                mi_log.CrearArchivo(this.getClass().getSimpleName());
                mi_log.logger.severe(e1.getMessage());
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
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró el modelo " + modelo.getIdModelo());
            throw new NotFoundException("No se encontró el modelo " + modelo.getIdModelo());
        } else{
            try {
                modelo.setNombre(modelo.getNombre().trim().toUpperCase());
                validarModelo(modelo);
                Modelo modeloExistente = new Modelo(
                        modelo.getIdModelo(), modelo.getNombre(),
                        modelo.getMarca()
                );
                return repository.save(modeloExistente);
            } catch (Exception e) {
                mi_log.CrearArchivo(this.getClass().getSimpleName());
                mi_log.logger.severe(e.getMessage());
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void validarModelo(Modelo modelo) throws BusinessException, NotFoundException {
        //nombre
        if (modelo.getNombre().trim().isEmpty()) {
            throw new BusinessException("El nombre del modelo no debe estar vacio");
        }
        if (modelo.getNombre().trim().length() < 5) {
            throw new BusinessException("Ingrese mas de 5 caracteres en el nombre del modelo");
        }
        if (modelo.getNombre().trim().length() > 80) {
            throw new BusinessException("El nombre del modelo no debe exceder los ochenta caracteres");
        }
        Pattern dobleEspacio = Pattern.compile("\\s{2,}");
        if (dobleEspacio.matcher(modelo.getNombre()).find()) {
            throw new BusinessException("Nombre de modelo no debe contener espacios dobles ఠ_ఠ");
        }
        Pattern pat = Pattern.compile("[\\d]*");
        Matcher mat_ = pat.matcher(modelo.getNombre().trim());
        if(mat_.matches()) {
            throw new BusinessException("El nombre de modelo no debe contener solo números ఠ_ఠ");
        }
        String[] nombre = modelo.getNombre().trim().split(" ");
        for (String item: nombre) {
            if(item.matches("(.)\\1{2,}")) {
                throw new BusinessException("El nombre no debe tener tantas letras repetidas ఠ_ఠ");
            }
            if(item.length()==1) {
                throw new BusinessException("Nombre de modelo inválido");
            }
        }
        List<Modelo> modelos = getModelos();
        for (Modelo item : modelos) {
            if ((item.getNombre().equals(modelo.getNombre().trim())) && (item.getIdModelo() != modelo.getIdModelo())) {
                throw new BusinessException("El nombre del modelo ya está en uso");
            }
        }
        //id marca


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
