package hn.edu.ujcv.savra.service.ProveedorService;

import hn.edu.ujcv.savra.entity.Pais;
import hn.edu.ujcv.savra.entity.Proveedor;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.PaisRepository;
import hn.edu.ujcv.savra.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ProveedorService implements IProveedorService {

    @Autowired
    private ProveedorRepository repository;

    @Override
    public Proveedor saveProveedor(Proveedor proveedor) throws BusinessException {
        try {
            validarProvedor(proveedor);
            return repository.save(proveedor);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Proveedor> saveProveedores(List<Proveedor> proveedores) throws BusinessException {
        try {

            for (Proveedor item : proveedores) {
                validarProvedor(item);
            }
            return repository.saveAll(proveedores);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Proveedor> getProveedores() throws BusinessException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Proveedor getProveedorById(long id) throws BusinessException, NotFoundException {
        Optional<Proveedor> opt=null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el proveedor " + id);
        }
        return opt.get();
    }

    @Override
    public Proveedor getProveedorByNombre(String nombre) throws BusinessException, NotFoundException {
        Optional<Proveedor> opt=null;
        try {
            opt = repository.findFirstByNombre(nombre);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el proveedor " + nombre);
        }
        return opt.get();
    }

    @Override
    public void deleteProveedor(long id) throws BusinessException, NotFoundException {
        Optional<Proveedor> opt=null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el proveedor " + id);
        } else {
            try {
                repository.deleteById(id);
            } catch (Exception e1) {
                throw new BusinessException(e1.getMessage());
            }
        }
    }

    @Override
    public Proveedor updateProveedor(Proveedor proveedor) throws BusinessException, NotFoundException {
        Optional<Proveedor> opt=null;
        try {
            opt = repository.findById(proveedor.getIdProveedor());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el proveedor " + proveedor.getIdProveedor());
        } else{
            try {
                validarProvedor(proveedor);
                Proveedor proveedorExistente = new Proveedor(
                        proveedor.getIdProveedor(), proveedor.getNombre(), proveedor.getCorreo(), proveedor.getTelefono(),
                        proveedor.getIdPais(), proveedor.getNombreContacto(), proveedor.getSitioWeb()
                );
                return repository.save(proveedorExistente);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void validarProvedor(Proveedor proveedor) throws BusinessException {
        Pattern dobleEspacio = Pattern.compile("\\s{2,}");
        //nombre
        if (proveedor.getNombre().trim().isEmpty()) {
            throw new BusinessException("El nombre del proveedor es requerido");
        }
        if (proveedor.getNombre().trim().length() < 5) {
            throw new BusinessException("Ingrese más de cinco caracteres en el nombre del proveedor");
        }
        if (proveedor.getNombre().trim().length() > 100) {
            throw new BusinessException("El nombre no debe exceder los cien caracteres");
        }
        if (dobleEspacio.matcher(proveedor.getNombre()).find()) {
            throw new BusinessException("Nombre de proveedor no debe contener espacios dobles ఠ_ఠ");
        }
        List<Proveedor> proveedores = getProveedores();
        for (Proveedor item : proveedores) {
            if ((item.getNombre().equals(proveedor.getNombre().trim())) &&
                    (item.getIdProveedor() != proveedor.getIdProveedor())) {
                throw new BusinessException("El nombre del proveedor ya está en uso");
            }
        }
        //correo
        if (proveedor.getCorreo().trim().isEmpty()) {
            throw new BusinessException("El correo no debe estar vacío");
        }
        if (proveedor.getCorreo().trim().length() < 3) {
            throw new BusinessException("Ingrese más de tres caracteres en el correo");
        }
        if (proveedor.getCorreo().trim().length() > 75) {
            throw new BusinessException("Debe ingresar menos de 75 caracteres en el correo");
        }
        if (!validarCorreo(proveedor.getCorreo())) {
            throw new BusinessException("La dirección de correo es inválida");
        }
        //telefono
        if (proveedor.getTelefono().trim().isEmpty()) {
            throw new BusinessException("El teléfono no debe estar vacío");
        }
        Pattern patron=Pattern.compile("[27389]");
        Matcher validarNumero = patron.matcher(proveedor.getTelefono().substring(0,1));
        Pattern pat = Pattern.compile("[\\d]*");
        Matcher mat = pat.matcher(proveedor.getTelefono().trim());

        if (proveedor.getTelefono().length() != 8){
            throw new BusinessException("No. de teléfono debe ser igual a 8 carácteres");
        }
        if(!mat.matches()){
            throw new BusinessException("No. de teléfono debe ser númerico ఠ_ఠ");
        }
        if (!validarNumero.matches()){
            throw new BusinessException("No. de teléfono no pertenece a una operadora válida");
        }
        //idPais
        if (String.valueOf(proveedor.getIdPais()).isEmpty()) {
            throw new BusinessException("El Código ISO no debe estar vacío");
        }
        if (proveedor.getIdPais() <= 0) {
            throw new BusinessException("Código ISO inválido");
        }
        if (!validarPais(proveedor.getIdPais())) {
            throw new BusinessException("Indique Código ISO válido");
        }
        //nombreContacto
        if (proveedor.getNombreContacto().trim().isEmpty()) {
            throw new BusinessException("El nombre de contacto no debe estar vacío");
        }
        if (proveedor.getNombreContacto().trim().length() < 5) {
            throw new BusinessException("Ingrese más de cinco caracteres en el nombre del contacto");
        }
        Pattern patDoc = Pattern.compile("[0-9]+");
        boolean matDoc = patDoc.matcher(proveedor.getNombreContacto().trim()).find();
        if(matDoc){
            throw new BusinessException("Nombre de contacto no debe contener números ఠ_ఠ");
        }
        if (dobleEspacio.matcher(proveedor.getNombreContacto()).find()) {
            throw new BusinessException("Nombre de contacto no debe contener espacios dobles ఠ_ఠ");
        }
        if (proveedor.getNombreContacto().length() > 100) {
            throw new BusinessException("El nombre de contacto no debe exceder los cien caracteres");
        }
        //sitioWeb
        if (proveedor.getSitioWeb().trim().isEmpty()) {
            throw new BusinessException("El sitio web es requerido");
        }
        if (proveedor.getSitioWeb().trim().length() < 3) {
            throw new BusinessException("Ingrese más de tres caracteres en el sitio web");
        }
        if (proveedor.getSitioWeb().trim().length() > 50) {
            throw new BusinessException("El sitio web no debe exceder los cincuenta caracteres");
        }
        if (!validarSitioWeb(proveedor.getSitioWeb())) {
            throw new BusinessException("EL sitio web es inválido");
        }
    }

    private boolean validarCorreo(String correo) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]{3,}+(\\.[_A-Za-z0-9-]{3,}+)*@" +
                "[A-Za-z0-9-]{2,}+(\\.[A-Za-z0-9]{2,}+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(correo);

        return matcher.find();
    }

    private boolean validarSitioWeb(String sitio) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]{3,}+(\\.[_A-Za-z0-9-]{2,}+)");
        Matcher matcher = pattern.matcher(sitio);

        return matcher.find();
    }

    @Autowired
    private PaisRepository paisRepository;
    private boolean validarPais(long id) {
        boolean condicion=false;
        List<Pais> paises = paisRepository.findAll();
        for (Pais item : paises) {
            if (item.getIdPais() == id) {
                condicion=true;
                break;
            }
        }
        return condicion;
    }
}
