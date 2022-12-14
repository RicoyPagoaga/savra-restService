package hn.edu.ujcv.savra.service.MetodoPagoService;

import hn.edu.ujcv.savra.entity.MetodoPago;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.MetodoPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MetodoPagoService implements IMetodoPagoService {

    @Autowired
    private MetodoPagoRepository repository;

    @Override
    public MetodoPago saveMetodoPago(MetodoPago metodoPago) throws BusinessException {
        try {
            metodoPago.setNombre(metodoPago.getNombre().trim().toUpperCase());
            validarMetodoPago(metodoPago);
            return repository.save(metodoPago);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<MetodoPago> saveMetodosPago(List<MetodoPago> metodosPago) throws BusinessException {
        try {
            for (MetodoPago metodo : metodosPago) {
                validarMetodoPago(metodo);
            }
            return repository.saveAll(metodosPago);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<MetodoPago> getMetodosPago() throws BusinessException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public MetodoPago getMetodoPagoById(long id) throws BusinessException, NotFoundException {
        Optional<MetodoPago> opt=null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el método de pago " + id);
        }
        return opt.get();
    }

    @Override
    public MetodoPago getMetodoPagoByNombre(String nombre) throws BusinessException, NotFoundException {
        Optional<MetodoPago> opt=null;
        try {
            opt = repository.findFirstByNombre(nombre);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el método de pago " + nombre);
        }
        return opt.get();
    }

    @Override
    public void deleteMetodoPago(long id) throws BusinessException, NotFoundException {
        Optional<MetodoPago> opt=null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el método de pago " + id);
        } else {
            try {
                repository.deleteById(id);
            } catch (Exception e1) {
                throw new BusinessException(e1.getMessage());
            }
        }
    }

    @Override
    public MetodoPago updateMetodoPago(MetodoPago metodoPago) throws BusinessException, NotFoundException {
        Optional<MetodoPago> opt=null;
        try {
            if (String.valueOf(metodoPago.getIdMetodoPago()).isEmpty()) {
                throw new BusinessException("El id del Método de Pago no debe estar vacio");
            }
            if (metodoPago.getIdMetodoPago() <= 0) {
                throw new BusinessException("Id de Método de Pago invalido");
            }
            opt = repository.findById(metodoPago.getIdMetodoPago());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el método de pago " + metodoPago.getIdMetodoPago());
        } else{
            try {
                metodoPago.setNombre(metodoPago.getNombre().trim().toUpperCase());
                validarMetodoPago(metodoPago);
                MetodoPago metodoExistente = new MetodoPago(
                        metodoPago.getIdMetodoPago(), metodoPago.getNombre()
                );
                return repository.save(metodoExistente);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void validarMetodoPago(MetodoPago metodoPago) throws BusinessException {
        if (metodoPago.getNombre().trim().isEmpty()) {
            throw new BusinessException("El nombre del método de pago no debe estar vacío");
        }
        if (metodoPago.getNombre().trim().length() < 3) {
            throw new BusinessException("Ingrese más de 3 caracteres en el nombre del método de pago");
        }
        if (metodoPago.getNombre().trim().length() > 50) {
            throw new BusinessException("El nombre del método de pago no debe exceder los cincuenta caracteres");
        }
        Pattern pat = Pattern.compile("[a-zA-Z]*");
        Matcher mat = pat.matcher(metodoPago.getNombre().trim());
        if (!mat.matches()){
            throw new BusinessException("Nombre de método de pago no debe tener espacios, números ni caracteres especiales");
        }
        if(metodoPago.getNombre().trim().matches("(.)\\1{2,}")) {
            throw new BusinessException("Nombre de método de pago no debe tener tantas letras repetidas ఠ_ఠ");
        }
        List<MetodoPago> metodos = getMetodosPago();
        for (MetodoPago item : metodos) {
            if ((item.getNombre().equals(metodoPago.getNombre().trim())) && (item.getIdMetodoPago() != metodoPago.getIdMetodoPago())) {
                throw new BusinessException("El nombre del método de pago ya está en uso");
            }
        }
    }

}
