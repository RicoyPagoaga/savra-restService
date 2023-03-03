package hn.edu.ujcv.savra.service.RepuestosService.RepuestoService;


import hn.edu.ujcv.savra.entity.Repuesto.Repuesto;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.Repuesto.RepuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RepuestoService implements IRepuestoService {

    @Autowired
    private RepuestoRepository repository;

    @Override
    public Repuesto saveRepuesto(Repuesto repuesto, boolean stockA,
                                 boolean stockM, boolean stockMa, double precio) throws BusinessException {
        try {
            repuesto.setNombre(repuesto.getNombre().trim());
            validarRepuesto(repuesto, stockA, stockM, stockMa, precio);
            return repository.save(repuesto);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Repuesto> saveRepuestos(List<Repuesto> repuestos) throws BusinessException {
        try {
//            for (Repuesto item : repuestos) {
//                validarRepuesto(item, true, true, true,0);
//            }
            return repository.saveAll(repuestos);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Repuesto> getRepuestos() throws BusinessException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Repuesto getRepuestoById(long id) throws BusinessException, NotFoundException {
        Optional<Repuesto> opt=null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el repuesto " + id);
        }
        return opt.get();
    }

    @Override
    public Repuesto getRepuestoByNombre(String nombre) throws BusinessException, NotFoundException {
        Optional<Repuesto> opt=null;
        try {
            opt = repository.findFirstByNombre(nombre);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el repuesto " + nombre);
        }
        return opt.get();
    }

    @Override
    public void deleteRepuesto(long id) throws BusinessException, NotFoundException {
        Optional<Repuesto> opt=null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el repuesto " + id);
        } else {
            try {
                repository.deleteById(id);
            } catch (Exception e1) {
                throw new BusinessException(e1.getMessage());
            }
        }
    }

    @Override
    public Repuesto updateRepuesto(Repuesto repuesto, boolean stockA,
                                   boolean stockM, boolean stockMa, double precio) throws BusinessException, NotFoundException {
        Optional<Repuesto> opt=null;
        try {
            opt = repository.findById(repuesto.getIdRepuesto());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el repuesto " + repuesto.getIdRepuesto());
        } else{
            try {
                validarRepuesto(repuesto, stockA, stockM, stockMa, precio);
                Repuesto repuestoExistente = new Repuesto(
                        repuesto.getIdRepuesto(), repuesto.getNombre().trim(), repuesto.getAnio_referenciaInicio(),
                        repuesto.getAnio_referenciaFinal(), repuesto.getCategoria(), repuesto.getStockActual(),
                        repuesto.getStockMinimo(), repuesto.getStockMaximo(), repuesto.getProveedor(),
                        repuesto.getModelo(), repuesto.getTransmision(), repuesto.getImpuesto()
                );
                return repository.save(repuestoExistente);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void validarRepuesto(Repuesto repuesto, boolean stockA, boolean stockM, boolean stockMa,
                                 double precio) throws BusinessException, NotFoundException {
        //nombre
        if (repuesto.getNombre().trim().isEmpty()) {
            throw new BusinessException("El nombre del repuesto es requerido");
        }
        if (repuesto.getNombre().trim().length() < 5) {
            throw new BusinessException("Ingrese más de cinco caracteres en el nombre del repuesto");
        }
        if (repuesto.getNombre().trim().length() > 80) {
            throw new BusinessException("El nombre del repuesto debe contener máximo ochenta caracteres");
        }
        Pattern dobleEspacio = Pattern.compile("\\s{2,}");
        if (dobleEspacio.matcher(repuesto.getNombre()).find()) {
            throw new BusinessException("Nombre de repuesto no debe contener espacios dobles ఠ_ఠ");
        }
        Pattern pat = Pattern.compile("[\\d]*");
        Matcher mat_ = pat.matcher(repuesto.getNombre().trim());
        if(mat_.matches()) {
            throw new BusinessException("El nombre de repuesto no debe contener solo números ఠ_ఠ");
        }
        String[] nombre = repuesto.getNombre().trim().split(" ");
        for (String item: nombre) {
            if(item.matches("(.)\\1{2,}")) {
                throw new BusinessException("El nombre no debe tener tantas letras repetidas ఠ_ఠ");
            }
            if(item.length()==1) {
                throw new BusinessException("Nombre inválido");
            }
        }
//        List<Repuesto> repuestos = getRepuestos();
//        for (Repuesto item : repuestos) {
//            if ((item.getNombre().equals(repuesto.getNombre().trim())) && (item.getIdRepuesto() != repuesto.getIdRepuesto())) {
//                throw new BusinessException("El nombre del repuesto ya está en uso");
//            }
//        }
        LocalDate fechaActual = LocalDate.now();
        //referencia inicio
        if (String.valueOf(repuesto.getAnio_referenciaInicio()).trim().isEmpty()) {
            throw new BusinessException("El año de referencia inicial no debe estar vacío");
        }
        if (repuesto.getAnio_referenciaInicio() < 0) {
            throw new BusinessException("El año de referencia inicial no debe ser un valor negativo");
        }
        if (String.valueOf(repuesto.getAnio_referenciaInicio()).length() != 4) {
            throw new BusinessException("El año de referencia inicial debe contener cuatro digitos");
        }
        if (repuesto.getAnio_referenciaInicio() < 1970) {
            throw new BusinessException("El año de referencia inicial debe ser mayor o igual al año 1970");
        }
        //referencia final
        if (String.valueOf(repuesto.getAnio_referenciaFinal()).trim().isEmpty()) {
            throw new BusinessException("El año de referencia final no debe estar vacío");
        }
        if (repuesto.getAnio_referenciaFinal() < 0) {
            throw new BusinessException("El año de referencia final no debe ser un valor negativo");
        }
        if (String.valueOf(repuesto.getAnio_referenciaFinal()).length() != 4) {
            throw new BusinessException("El año de referencia final debe contener cuatro digitos");
        }
        if (repuesto.getAnio_referenciaFinal() > fechaActual.getYear()) {
            throw new BusinessException("El año de referencia final no puede ser a futuro");
        }
        //
        if (repuesto.getAnio_referenciaInicio() > repuesto.getAnio_referenciaFinal()) {
            throw new BusinessException("El año de referencia inicial no debe ser mayor al año de referencia final");
        }
        //id Categoria
        if (repuesto.getCategoria() == null) {
            throw new BusinessException("Indique la Categoría");
        }
        //stock Actual
        if(stockA) {
            throw new BusinessException("El stock actual no debe estar vacío");
        }
        if (repuesto.getStockActual() < 0) {
            throw new BusinessException("El stock actual no puede ser menor a cero");
        }
        //stock Minimo
        if (stockM) {
            throw new BusinessException("El stock mínimo no debe estar vacío");
        }
        if (repuesto.getStockMinimo() < 0) {
            throw new BusinessException("El stock mínimo no puede ser menor a cero");
        }
        //stock Maximo
        if (stockMa) {
            throw new BusinessException("El stock máximo no debe estar vacío");
        }
        if (repuesto.getStockMaximo() <= 0) {
            throw new BusinessException("El stock máximo no puede ser menor o igual a cero");
        }
        //
        if (repuesto.getStockMinimo() >= repuesto.getStockMaximo()) {
            throw new BusinessException("El stock mínimo no debe ser mayor o igual al stock máximo");
        }
        if (repuesto.getStockActual() > repuesto.getStockMaximo()) {
            throw new BusinessException("El stock actual no debe ser mayor al stock máximo");
        }
        //id Proveedor
        if (repuesto.getProveedor() == null) {
            throw new BusinessException("Proveedor inválido");
        }
        //id Modelo
        if (repuesto.getModelo() == null) {
            throw new BusinessException("Modelo inválido");
        }
        //id Transmision
        if (repuesto.getTransmision() == null) {
            throw new BusinessException("Transmisión inválida");
        }
        //precio
        if (precio <= 0) {
            throw new BusinessException("El precio es requerido, no puede ser menor o igual a cero");
        }
        //idImpuesto
        if (repuesto.getImpuesto() == null) {
            throw new BusinessException("Impuesto inválido");
        }
    }
}
