package hn.edu.ujcv.savra.service.RepuestosService.RepuestoService;


import hn.edu.ujcv.savra.entity.*;
import hn.edu.ujcv.savra.entity.Impuesto.Impuesto;
import hn.edu.ujcv.savra.entity.Repuesto.Repuesto;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.*;
import hn.edu.ujcv.savra.repository.Impuesto.ImpuestoRepository;
import hn.edu.ujcv.savra.repository.Repuesto.RepuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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
            for (Repuesto item : repuestos) {
                validarRepuesto(item, true, true, true,0);
            }
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
            throw new NotFoundException("No se encontr?? el repuesto " + id);
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
            throw new NotFoundException("No se encontr?? el repuesto " + nombre);
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
            throw new NotFoundException("No se encontr?? el repuesto " + id);
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
            throw new NotFoundException("No se encontr?? el repuesto " + repuesto.getIdRepuesto());
        } else{
            try {
                validarRepuesto(repuesto, stockA, stockM, stockMa, precio);
                Repuesto repuestoExistente = new Repuesto(
                        repuesto.getIdRepuesto(), repuesto.getNombre().trim(), repuesto.getAnio_referenciaInicio(),
                        repuesto.getAnio_referenciaFinal(), repuesto.getIdCategoria(), repuesto.getStockActual(),
                        repuesto.getStockMinimo(), repuesto.getStockMaximo(), repuesto.getIdProveedor(),
                        repuesto.getIdModelo(), repuesto.getIdTransmision(), repuesto.getIdImpuesto()
                );
                return repository.save(repuestoExistente);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void validarRepuesto(Repuesto repuesto, boolean stockA, boolean stockM, boolean stockMa,
                                 double precio) throws BusinessException, NotFoundException, ParseException {
        //nombre
        if (repuesto.getNombre().trim().isEmpty()) {
            throw new BusinessException("El nombre del repuesto es requerido");
        }
        if (repuesto.getNombre().trim().length() < 5) {
            throw new BusinessException("Ingrese m??s de cinco caracteres en el nombre del repuesto");
        }
        if (repuesto.getNombre().trim().length() > 80) {
            throw new BusinessException("El nombre del repuesto debe contener m??ximo ochenta caracteres");
        }
        Pattern dobleEspacio = Pattern.compile("\\s{2,}");
        if (dobleEspacio.matcher(repuesto.getNombre()).find()) {
            throw new BusinessException("Nombre de repuesto no debe contener espacios dobles ???_???");
        }
        Pattern pat = Pattern.compile("[\\d]*");
        Matcher mat_ = pat.matcher(repuesto.getNombre().trim());
        if(mat_.matches()) {
            throw new BusinessException("El nombre de repuesto no debe contener solo n??meros ???_???");
        }
        String[] nombre = repuesto.getNombre().trim().split(" ");
        for (String item: nombre) {
            if(item.matches("(.)\\1{2,}")) {
                throw new BusinessException("El nombre no debe tener tantas letras repetidas ???_???");
            }
            if(item.length()==1) {
                throw new BusinessException("Nombre inv??lido");
            }
        }
        List<Repuesto> repuestos = getRepuestos();
        for (Repuesto item : repuestos) {
            if ((item.getNombre().equals(repuesto.getNombre().trim())) && (item.getIdRepuesto() != repuesto.getIdRepuesto())) {
                throw new BusinessException("El nombre del repuesto ya est?? en uso");
            }
        }
        LocalDate fechaActual = LocalDate.now();
        //referencia inicio
        if (String.valueOf(repuesto.getAnio_referenciaInicio()).trim().isEmpty()) {
            throw new BusinessException("El a??o de referencia inicial no debe estar vac??o");
        }
        if (repuesto.getAnio_referenciaInicio() < 0) {
            throw new BusinessException("El a??o de referencia inicial no debe ser un valor negativo");
        }
        if (String.valueOf(repuesto.getAnio_referenciaInicio()).length() != 4) {
            throw new BusinessException("El a??o de referencia inicial debe contener cuatro digitos");
        }
        if (repuesto.getAnio_referenciaInicio() < 1970) {
            throw new BusinessException("El a??o de referencia inicial debe ser mayor o igual al a??o 1970");
        }
        //referencia final
        if (String.valueOf(repuesto.getAnio_referenciaFinal()).trim().isEmpty()) {
            throw new BusinessException("El a??o de referencia final no debe estar vac??o");
        }
        if (repuesto.getAnio_referenciaFinal() < 0) {
            throw new BusinessException("El a??o de referencia final no debe ser un valor negativo");
        }
        if (String.valueOf(repuesto.getAnio_referenciaFinal()).length() != 4) {
            throw new BusinessException("El a??o de referencia final debe contener cuatro digitos");
        }
        if (repuesto.getAnio_referenciaFinal() > fechaActual.getYear()) {
            throw new BusinessException("El a??o de referencia final no puede ser a futuro");
        }
        //
        if (repuesto.getAnio_referenciaInicio() > repuesto.getAnio_referenciaFinal()) {
            throw new BusinessException("El a??o de referencia inicial no debe ser mayor al a??o de referencia final");
        }
        //id Categoria
        if (String.valueOf(repuesto.getIdCategoria()).isEmpty()) {
            throw new BusinessException("La categor??a es requerida");
        }
        if (repuesto.getIdCategoria() < 0) {
            throw new BusinessException("Id de Categor??a inv??lido");
        }
        if (!validarCategoria(repuesto.getIdCategoria())) {
            throw new BusinessException("Indique una categor??a v??lida");
        }
        //stock Actual
        if(stockA) {
            throw new BusinessException("El stock actual no debe estar vac??o");
        }
        if (repuesto.getStockActual() < 0) {
            throw new BusinessException("El stock actual no puede ser menor a cero");
        }
        //stock Minimo
        if (stockM) {
            throw new BusinessException("El stock m??nimo no debe estar vac??o");
        }
        if (repuesto.getStockMinimo() < 0) {
            throw new BusinessException("El stock m??nimo no puede ser menor a cero");
        }
        //stock Maximo
        if (stockMa) {
            throw new BusinessException("El stock m??ximo no debe estar vac??o");
        }
        if (repuesto.getStockMaximo() <= 0) {
            throw new BusinessException("El stock m??ximo no puede ser menor o igual a cero");
        }
        //
        if (repuesto.getStockMinimo() >= repuesto.getStockMaximo()) {
            throw new BusinessException("El stock m??nimo no debe ser mayor o igual al stock m??ximo");
        }
        if (repuesto.getStockActual() > repuesto.getStockMaximo()) {
            throw new BusinessException("El stock actual no debe ser mayor al stock m??ximo");
        }
        //id Proveedor
        if (String.valueOf(repuesto.getIdProveedor()).isEmpty()) {
            throw new BusinessException("El proveedor es requerido");
        }
        if (repuesto.getIdProveedor() < 0) {
            throw new BusinessException("Proveedor inv??lido");
        }
        if (!validarProveedor(repuesto.getIdProveedor())) {
            throw new NotFoundException("Indique el proveedor");
        }
        //id Modelo
        if (String.valueOf(repuesto.getIdModelo()).isEmpty()) {
            throw new BusinessException("El modelo es requerido");
        }
        if (repuesto.getIdModelo() < 0) {
            throw new BusinessException("Modelo inv??lido");
        }
        if (!validarModelo(repuesto.getIdModelo())) {
            throw new NotFoundException("Indique el modelo");
        }
        //id Transmision
        if (String.valueOf(repuesto.getIdTransmision()).isEmpty()) {
            throw new BusinessException("La transmisi??n es requerida");
        }
        if (repuesto.getIdTransmision() < 0) {
            throw new BusinessException("Transmisi??n inv??lida");
        }
        if (validarTransmision(repuesto.getIdTransmision())) {
            throw new NotFoundException("Indique transmisi??n v??lida");
        }
        //precio
        if (precio <= 0) {
            throw new BusinessException("El precio es requerido, no puede ser menor o igual a cero");
        }
        //idImpuesto
        if (repuesto.getIdImpuesto() <= 0) {
            throw new BusinessException("Impuesto inv??lido");
        }
        if (!validarImpuesto(repuesto.getIdImpuesto())) {
            throw new NotFoundException("Indique impuesto v??lido");
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
        return !condicion;
    }

    @Autowired
    private ImpuestoRepository impuestoRepository;
    private boolean validarImpuesto(long id) {
        boolean condicion=false;
        List<Impuesto> lista = impuestoRepository.findAll();
        for (Impuesto item : lista) {
            if (item.getIdImpuesto() == id) {
                condicion=true;
                break;
            }
        }
        return condicion;
    }

}
