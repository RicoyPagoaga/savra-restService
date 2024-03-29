package hn.edu.ujcv.savra.service.ImpuestosService.ImpuestoService;

import hn.edu.ujcv.savra.entity.Impuesto.Impuesto;
import hn.edu.ujcv.savra.entity.TipoEntrega;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.Impuesto.ImpuestoRepository;
import hn.edu.ujcv.savra.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ImpuestoService implements IImpuestoService {

    private Log mi_log = new Log();

    @Autowired
    private ImpuestoRepository repository;

    @Override
    public Impuesto saveImpuesto(Impuesto impuesto, int valor, boolean nulo) throws BusinessException {
        try {
            impuesto.setNombre(impuesto.getNombre().trim());
            impuesto.setDescripcion(impuesto.getDescripcion().trim());
            validarImpuesto(impuesto, valor, nulo);
            return repository.save(impuesto);
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Impuesto> saveImpuestos(List<Impuesto> impuestos) throws BusinessException {
        try {
            for (Impuesto item : impuestos) {
                validarImpuesto(item, 1, false);
            }
            return repository.saveAll(impuestos);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Impuesto> getImpuestos() throws BusinessException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Impuesto getImpuestoById(long id) throws BusinessException, NotFoundException {
        Optional<Impuesto> opt=null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró el impuesto " + id);
            throw new NotFoundException("No se encontró el impuesto " + id);
        }
        return opt.get();
    }

    @Override
    public Impuesto getImpuestoByNombre(String nombre) throws BusinessException, NotFoundException {
        Optional<Impuesto> opt=null;
        try {
            opt = repository.findFirstByNombre(nombre);
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró el impuesto " + nombre);
            throw new NotFoundException("No se encontró el impuesto " + nombre);
        }
        return opt.get();
    }

    @Override
    public void deleteImpuesto(long id) throws BusinessException, NotFoundException {
        Optional<Impuesto> opt=null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró el impuesto " + id);
            throw new NotFoundException("No se encontró el impuesto " + id);
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
    public Impuesto updateImpuesto(Impuesto impuesto, int valor, boolean nulo) throws BusinessException, NotFoundException {
        Optional<Impuesto> opt=null;
        try {
            opt = repository.findById(impuesto.getIdImpuesto());
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró el impuesto " + impuesto.getIdImpuesto());
            throw new NotFoundException("No se encontró el impuesto " + impuesto.getIdImpuesto());
        } else{
            try {
                validarImpuesto(impuesto, valor, nulo);
                Impuesto impuestoExistente = new Impuesto(
                        impuesto.getIdImpuesto(), impuesto.getNombre().trim(),
                        impuesto.getDescripcion().trim(), impuesto.getEstado()
                );
                return repository.save(impuestoExistente);
            } catch (Exception e) {
                mi_log.CrearArchivo(this.getClass().getSimpleName());
                mi_log.logger.severe(e.getMessage());
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void validarImpuesto(Impuesto impuesto, int valor, boolean nulo) throws BusinessException {
        //nombre
        if (impuesto.getNombre().trim().isEmpty()) {
            throw new BusinessException("El nombre del impuesto es requerido");
        }
        if (impuesto.getNombre().trim().length() < 3) {
            throw new BusinessException("Ingrese más de tres caracteres en el nombre del impuesto");
        }
        if (impuesto.getNombre().trim().length() > 30) {
            throw new BusinessException("El nombre no debe exceder los treinta caracteres");
        }
        Pattern pat = Pattern.compile("[a-zA-Z0-9]*");
        Matcher mat = pat.matcher(impuesto.getNombre().trim());
        if (!mat.matches()){
            throw new BusinessException("Nombre de impuesto no debe tener espacios ni caracteres especiales");
        }
        if(impuesto.getNombre().trim().matches("(.)\\1{2,}")) {
            throw new BusinessException("Nombre de impuesto no debe tener tantas letras repetidas ఠ_ఠ");
        }
        List<Impuesto> impuestos = getImpuestos();
        for (Impuesto item : impuestos) {
            if ((item.getNombre().equals(impuesto.getNombre().trim())) && (item.getIdImpuesto() != impuesto.getIdImpuesto())) {
                throw new BusinessException("El nombre del impuesto ya está en uso");
            }
        }
        //descripcion
        if (impuesto.getDescripcion().trim().isEmpty()) {
            throw new BusinessException("La descripción es requerida");
        }
        if (impuesto.getDescripcion().trim().length() < 5) {
            throw new BusinessException("La descripción debe contener mínimo cinco caracteres");
        }
        if (impuesto.getDescripcion().trim().length() > 50) {
            throw new BusinessException("La descripción no debe exceder los cincuenta caracteres");
        }
        Pattern dobleEspacio = Pattern.compile("\\s{2,}");
        if (dobleEspacio.matcher(impuesto.getDescripcion().trim()).find()) {
            throw new BusinessException("Descripción de categoría no debe contener espacios dobles ఠ_ఠ");
        }
        String[] descripcion = impuesto.getDescripcion().trim().split(" ");
        for (String item: descripcion) {
            if(item.matches("(.)\\1{2,}")) {
                throw new BusinessException("La descripción no debe tener tantas letras repetidas ఠ_ఠ");
            }
        }
        //valor
        if (nulo) {
            throw new BusinessException("El valor es requerido");
        }
        if (valor < 0) {
            throw new BusinessException("El valor no puede ser menor a cero");
        }
        if (valor > 100) {
            throw new BusinessException("El valor no puede ser mayor a cien");
        }
    }
}
