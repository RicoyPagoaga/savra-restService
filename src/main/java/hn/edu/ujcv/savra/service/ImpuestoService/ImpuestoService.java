package hn.edu.ujcv.savra.service.ImpuestoService;

import hn.edu.ujcv.savra.entity.Impuesto;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.ImpuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ImpuestoService implements IImpuestoService {

    @Autowired
    private ImpuestoRepository repository;

    @Override
    public Impuesto saveImpuesto(Impuesto impuesto) throws BusinessException {
        try {
            validarImpuesto(impuesto);
            return repository.save(impuesto);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Impuesto> saveImpuestos(List<Impuesto> impuestos) throws BusinessException {
        try {
            for (Impuesto item : impuestos) {
                validarImpuesto(item);
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
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
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
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
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
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el impuesto " + id);
        } else {
            try {
                repository.deleteById(id);
            } catch (Exception e1) {
                throw new BusinessException(e1.getMessage());
            }
        }
    }

    @Override
    public Impuesto updateImpuesto(Impuesto impuesto) throws BusinessException, NotFoundException {
        Optional<Impuesto> opt=null;
        try {
            opt = repository.findById(impuesto.getIdImpuesto());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el impuesto " + impuesto.getIdImpuesto());
        } else{
            try {
                validarImpuesto(impuesto);
                Impuesto impuestoExistente = new Impuesto(
                        impuesto.getIdImpuesto(), impuesto.getNombre(),
                        impuesto.getFechaInicio(), impuesto.getFechaFinal(),
                        impuesto.getValor()
                );
                return repository.save(impuestoExistente);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void validarImpuesto(Impuesto impuesto) throws BusinessException {
        //nombre
        if (impuesto.getNombre().isEmpty()) {
            throw new BusinessException("El nombre del impuesto es requerido");
        }
        if (impuesto.getNombre().trim().length() < 3) {
            throw new BusinessException("Ingrese más de tres caracteres en el nombre del impuesto");
        }
        if (impuesto.getNombre().trim().length() > 30) {
            throw new BusinessException("El nombre no debe exceder los treinta caracteres");
        }
        Pattern dobleEspacio = Pattern.compile("\\s{2,}");
        if (dobleEspacio.matcher(impuesto.getNombre()).find()) {
            throw new BusinessException("Nombre de impuesto no debe contener espacios dobles ఠ_ఠ");
        }
        List<Impuesto> impuestos = getImpuestos();
        for (Impuesto item : impuestos) {
            if ((item.getNombre().equals(impuesto.getNombre())) && (item.getIdImpuesto() != impuesto.getIdImpuesto())) {
                throw new BusinessException("El nombre del impuesto ya está en uso");
            }
        }
        //fechas
        LocalDate fechaActual = LocalDate.now();
        //fecha inicio
        if (impuesto.getFechaInicio() == null) {
            throw new BusinessException("La fecha de inicio es requerida");
        }
        if (impuesto.getFechaInicio().isAfter(fechaActual)) {
            throw new BusinessException("La fecha de inicio no puede ser mayor a la fecha actual");
        }
        //fecha final
        if (impuesto.getFechaFinal() == null) {
            throw new BusinessException("La fecha final es requerida");
        }
        if (impuesto.getFechaFinal().isAfter(fechaActual)) {
            throw new BusinessException("La fecha final no puede ser mayor a la fecha actual");
        }
        if (impuesto.getFechaInicio().isAfter(impuesto.getFechaFinal())) {
            throw new BusinessException("La fecha de inicio no debe ser mayor a la fecha final");
        }
        //valor
        if (String.valueOf(impuesto.getValor()).isEmpty()) {
            throw new BusinessException("El valor no debe estar vacío");
        }
        if (impuesto.getValor() < 0) {
            throw new BusinessException("El valor no puede ser menor a cero");
        }
        if (impuesto.getValor() < 12 || impuesto.getValor() > 30) {
            throw new BusinessException("Debe indicar el valor del impuesto según el rango: 12% a 30%");
        }
    }
}
