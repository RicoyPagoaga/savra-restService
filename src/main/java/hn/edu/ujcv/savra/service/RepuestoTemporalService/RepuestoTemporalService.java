package hn.edu.ujcv.savra.service.RepuestoTemporalService;

import hn.edu.ujcv.savra.entity.*;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class RepuestoTemporalService implements IRepuestoTemporalService {

    @Autowired
    private RepuestoTemporalRepository repository;

    @Override
    public RepuestoTemporal saveRepuesto(RepuestoTemporal repuesto) throws BusinessException {
        try {
            validarRepuesto(repuesto);
            return repository.save(repuesto);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<RepuestoTemporal> saveRepuestos(List<RepuestoTemporal> repuestos) throws BusinessException {
        try {
            for (RepuestoTemporal item : repuestos) {
                validarRepuesto(item);
            }
            return repository.saveAll(repuestos);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<RepuestoTemporal> getRepuestos() throws BusinessException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public RepuestoTemporal getRepuestoById(long id) throws BusinessException, NotFoundException {
        Optional<RepuestoTemporal> opt=null;
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
    public void deleteRepuesto(long id) throws BusinessException, NotFoundException {
        Optional<RepuestoTemporal> opt=null;
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

    private void validarRepuesto(RepuestoTemporal repuesto) throws BusinessException, NotFoundException {
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
            throw new BusinessException("Nombre de modelo no debe contener espacios dobles ఠ_ఠ");
        }
        validarNombre(repuesto.getNombre());

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
        if (String.valueOf(repuesto.getIdCategoria()).isEmpty()) {
            throw new BusinessException("La categoría es requerida");
        }
        if (repuesto.getIdCategoria() < 0) {
            throw new BusinessException("Id de Categoría inválido");
        }
        if (!validarCategoria(repuesto.getIdCategoria())) {
            throw new BusinessException("Indique una categoría válida");
        }
        //stock Actual
        if (String.valueOf(repuesto.getStockActual()).trim().isEmpty()) {
            throw new BusinessException("El stock actual no debe estar vacío");
        }
        if (repuesto.getStockActual() < 0) {
            throw new BusinessException("El stock actual no puede ser menor a cero");
        }
        //stock Minimo
        if (String.valueOf(repuesto.getStockMinimo()).trim().isEmpty()) {
            throw new BusinessException("El stock mínimo no debe estar vacío");
        }
        if (repuesto.getStockMinimo() < 0) {
            throw new BusinessException("El stock mínimo no puede ser menor a cero");
        }
        //stock Maximo
        if (String.valueOf(repuesto.getStockMaximo()).trim().isEmpty()) {
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
        if (String.valueOf(repuesto.getIdProveedor()).isEmpty()) {
            throw new BusinessException("El proveedor es requerido");
        }
        if (repuesto.getIdProveedor() < 0) {
            throw new BusinessException("Proveedor inválido");
        }
        if (!validarProveedor(repuesto.getIdProveedor())) {
            throw new NotFoundException("Indique el proveedor");
        }
        //id Modelo
        if (String.valueOf(repuesto.getIdModelo()).isEmpty()) {
            throw new BusinessException("El modelo es requerido");
        }
        if (repuesto.getIdModelo() < 0) {
            throw new BusinessException("Modelo inválido");
        }
        if (!validarModelo(repuesto.getIdModelo())) {
            throw new NotFoundException("Indique el modelo");
        }
        //id Transmision
        if (String.valueOf(repuesto.getIdTransmision()).isEmpty()) {
            throw new BusinessException("La transmisión es requerida");
        }
        if (repuesto.getIdTransmision() < 0) {
            throw new BusinessException("Transmisión inválida");
        }
        if (!validarTransmision(repuesto.getIdTransmision())) {
            throw new NotFoundException("Indique transmisión válida");
        }
        //precio
        if (String.valueOf(repuesto.getPrecio()).isEmpty()) {
            throw new BusinessException("El precio no debe estar vacío");
        }
        if (repuesto.getPrecio() <= 0) {
            throw new BusinessException("El precio no puede ser menor o igual a cero");
        }
    }

    @Autowired
    private RepuestoRepository repuestoRepository;
    private void validarNombre(String nombre) throws BusinessException {
        List<Repuesto> repuestos = repuestoRepository.findAll();
        for (Repuesto item : repuestos) {
            if ((item.getNombre().equals(nombre.trim()))) {
                throw new BusinessException("El nombre del repuesto ya está en uso");
            }
        }
    }

    @Autowired
    private ProveedorRepository proveedorRepository;
    private boolean validarProveedor(long id) {
        boolean condicion=false;
        List<Proveedor> proveedores = proveedorRepository.findAll();
        for (Proveedor item : proveedores) {
            if (item.getIdProveedor() == id) {
                condicion=true;
                break;
            }
        }
        return condicion;
    }

    @Autowired
    private ModeloRepository modeloRepository;
    private boolean validarModelo(long id) {
        boolean condicion=false;
        List<Modelo> modelos = modeloRepository.findAll();
        for (Modelo item : modelos) {
            if (item.getIdModelo() == id) {
                condicion=true;
                break;
            }
        }
        return condicion;
    }

    @Autowired
    private CategoriaRepuestoRepository categoriaRepository;
    private boolean validarCategoria(long id) {
        boolean condicion=false;
        List<CategoriaRepuesto> lista = categoriaRepository.findAll();
        for (CategoriaRepuesto item : lista) {
            if (item.getIdCategoria() == id) {
                condicion=true;
                break;
            }
        }
        return condicion;
    }

    @Autowired
    private TransmisionRepository transmisionRepository;
    private boolean validarTransmision(long id) {
        boolean condicion=false;
        List<Transmision> lista = transmisionRepository.findAll();
        for (Transmision item : lista) {
            if (item.getIdTransmision() == id) {
                condicion=true;
                break;
            }
        }
        return condicion;
    }

}
