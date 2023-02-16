package hn.edu.ujcv.savra.service.EmpleadoService;
import hn.edu.ujcv.savra.entity.Empleado;
import hn.edu.ujcv.savra.entity.TipoDocumento;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.EmpleadosRepository;
import hn.edu.ujcv.savra.repository.TipoDocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmpleadosService implements IEmpleadosService{

    @Autowired
    private EmpleadosRepository repository;

    @Override
    public Empleado saveEmpleados(Empleado empleado) throws BusinessException {
        try {
            //nombre
            if (empleado.getNombre().trim().isEmpty()){
                throw new BusinessException("El nombre no debe estar vacio ఠ_ఠ");
            }
            if(empleado.getNombre().trim().length()<3){
                throw new BusinessException("El nombre debe tener mas de 3 caracteres ఠ_ఠ");
            }
            if(empleado.getNombre().trim().length()>50){
                throw new BusinessException("El nombre no debe tener mas de 50 caracteres ఠ_ఠ");
            }
//            Pattern patDoc = Pattern.compile("^([a-zA-Z]+)(\\s[a-zA-Z]+)*$");
            Pattern patDoc = Pattern.compile("^[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+(\\s[a-zA-ZÀ-ÿ\\u00f1\\u00d1])*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+$");
            Matcher matDoc = patDoc.matcher(empleado.getNombre().trim());
            if(!matDoc.matches()){
                throw new BusinessException("Nombre no debe contener números o caracteres especiales ni espacios duplicados ఠ_ఠ");
            }
            if (empleado.getDocumento().trim().isEmpty()){
                throw new BusinessException("El documento está vacío");
            }
            //idTipoDocumento
            if(empleado.getIdEmpleado() < 1) {
                throw new BusinessException("Id Documento vacío");
            }
            //documento
            contiene(empleado.getTipoDocumento(), empleado.getDocumento().trim());

            //fecha nacimiento
            if (empleado.getFechaNacimiento()==null){
                throw new BusinessException("Fecha de nacimiento esta vacía");
            }
            if (empleado.getFechaNacimiento().isAfter( LocalDate.now().minusYears(18))){
                throw new BusinessException("El empleado debe ser mayor de 18 años");
            }
            if (empleado.getFechaNacimiento().isBefore(LocalDate.now().minusYears(60))){
                throw new BusinessException("El empleado no debe ser mayor de 60 años");
            }
            //Fecha ingreso
            if (empleado.getFechaIngreso()==null){
                throw new BusinessException("Fecha de ingreso esta vacía");
            }
            if(!empleado.getFechaIngreso().equals(LocalDate.now())){
                throw new BusinessException("La fecha de ingreso no puede ser distinta a la de hoy ");
            }
            //telefono
            if (empleado.getTelefono().trim().isEmpty()){
                throw new BusinessException("Teléfono esta vacío");
            }
            Pattern patron=Pattern.compile("[72389]");
            Matcher validarNumero = patron.matcher(empleado.getTelefono().substring(0,1));
            if (empleado.getTelefono().trim().length() != 8){
                throw new BusinessException("No. de teléfono debe ser igual a 8 dígitos ఠ_ఠ");
            }
            Pattern pat = Pattern.compile("[\\d]*");
            Matcher mat = pat.matcher(empleado.getTelefono().trim());
            if(!mat.matches()){
                throw new BusinessException("No. de teléfono debe ser númerico ఠ_ఠ");
            }
            if (!validarNumero.matches()){
                throw new BusinessException("No. de teléfono no pertenece a una operadora valida ఠ_ఠ");
            }
            //Correo
            if (empleado.getCorreo().isEmpty()) {
                throw new BusinessException("El correo no debe estar vacío");
            }
            if (empleado.getCorreo().trim().length() < 3) {
                throw new BusinessException("Ingrese más de tres caracteres en el correo");
            }
            if (empleado.getCorreo().trim().length() > 75) {
                throw new BusinessException("Debe ingresar menos de 75 caracteres en el correo");
            }
            if (!validarCorreo(empleado.getCorreo())) {
                throw new BusinessException("La dirección de correo electrónico es inválida");
            }
            //direccion
            if (empleado.getDireccion().trim().isEmpty()){
                throw new BusinessException("Dirección del empleado esta vacio ఠ_ఠ");
            }
            if (empleado.getDireccion().trim().length() < 6){
                throw new BusinessException("Dirección debe ser mayor a 6 caracteres ఠ_ఠ");
            }
            if (empleado.getDireccion().trim().length() > 100){
                throw new BusinessException("Dirección no debe ser mayor a 100 caracteres ఠ_ఠ");
            }
            empleado.setNombre(empleado.getNombre().trim().toUpperCase());
            return repository.save(empleado);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Empleado> saveEmpleados(List<Empleado> empleados) throws BusinessException {
        try {
            for (Empleado empleado:empleados) {
                if(empleado.getNombre().length()<5){
                    throw new BusinessException("El nombre debe tener mas de 10 caracteres");
                }if (empleado.getTelefono().length()<8){
                    throw new BusinessException("No debe tener menos de 8 caracteres");
                }
            }
            return repository.saveAll(empleados);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Empleado> getEmpleados() throws BusinessException {
        try {
            return repository.findAll();
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Empleado getEmpleadosById(long id) throws BusinessException, NotFoundException {
        Optional<Empleado> opt= null;
        try {
            opt=repository.findById(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el empleado"+id);
        }
        return opt.get();
    }

    @Override
    public Empleado getEmpleadoByNombre(String nombre) throws BusinessException, NotFoundException {
        Optional<Empleado> opt= null;
        try {
            opt=repository.findByNombre(nombre);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el empleado :v"+nombre);
        }
        return opt.get();
    }

    @Override
    public void deleteEmpleados(long id) throws BusinessException, NotFoundException {
        Optional<Empleado> opt= null;
        try {
            opt=repository.findById(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el empleado"+id);
        }
        else {
            try {
                repository.deleteById(id);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
    }

    @Override
    public Empleado updateEmpleados(Empleado empleado) throws BusinessException, NotFoundException {
        Optional<Empleado> opt= null;
        try {
            opt=repository.findById(empleado.getIdEmpleado());
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el empleado"+ empleado.getIdEmpleado());
        }
        else {
            try {
                //nombre
                if (empleado.getNombre().trim().isEmpty()){
                    throw new BusinessException("El nombre no debe estar vacio ఠ_ఠ");
                }
                if(empleado.getNombre().trim().length()<3){
                    throw new BusinessException("El nombre debe tener mas de 3 caracteres ఠ_ఠ");
                }
                if(empleado.getNombre().trim().length()>50){
                    throw new BusinessException("El nombre no debe tener mas de 50 caracteres ఠ_ఠ");
                }
                Pattern pat3 = Pattern.compile("[a-zA-ZñÑáéíóúÁÉÍÓÚ ]*");
                Matcher matDoc = pat3.matcher(empleado.getNombre().trim());
                if(!matDoc.matches()){
                    throw new BusinessException("Nombre no debe contener números o caracteres especiales.");
                }
                Pattern dobleEspacio = Pattern.compile("\\s{2,}");
                if (dobleEspacio.matcher(empleado.getNombre().trim()).find()) {
                    throw new BusinessException("Nombre no debe contener espacios dobles.");
                }
                if (empleado.getDocumento().trim().isEmpty()){
                    throw new BusinessException("El documento está vacío");
                }
                //idTipoDocumento

                //documento
                contiene(empleado.getTipoDocumento(),empleado.getDocumento().trim());

                //fecha nacimiento
                if (empleado.getFechaNacimiento()==null){
                    throw new BusinessException("Fecha de nacimiento esta vacía");
                }
                if (empleado.getFechaNacimiento().isAfter( LocalDate.now().minusYears(18))){
                    throw new BusinessException("El empleado debe ser mayor de 18 años");
                }
                if (empleado.getFechaNacimiento().isBefore(LocalDate.now().minusYears(60))){
                    throw new BusinessException("El empleado no debe ser mayor de 60 años");
                }
                //Fecha ingreso
                if (empleado.getFechaIngreso()==null){
                    throw new BusinessException("Fecha de ingreso esta vacía");
                }
                //if (empleado.getFechaIngreso().)

                //if(!empleado.getFechaIngreso().equals(LocalDate.now())){
                    //throw new BusinessException("La fecha de ingreso no puede ser distinta a la de hoy ");
                //}
                //telefono
                if (empleado.getTelefono().trim().isEmpty()){
                    throw new BusinessException("Teléfono esta vacío");
                }
                Pattern patron=Pattern.compile("[72389]");
                Matcher validarNumero = patron.matcher(empleado.getTelefono().substring(0,1));
                if (empleado.getTelefono().trim().length() != 8){
                    throw new BusinessException("No. de teléfono debe ser igual a 8 dígitos ఠ_ఠ");
                }
                Pattern pat = Pattern.compile("[\\d]*");
                Matcher mat = pat.matcher(empleado.getTelefono().trim());
                if(!mat.matches()){
                    throw new BusinessException("No. de teléfono debe ser númerico ఠ_ఠ");
                }
                if (!validarNumero.matches()){
                    throw new BusinessException("No. de teléfono no pertenece a una operadora valida ఠ_ఠ");
                }
                //Correo
                if (empleado.getCorreo().isEmpty()) {
                    throw new BusinessException("El correo no debe estar vacío");
                }
                if (empleado.getCorreo().trim().length() < 3) {
                    throw new BusinessException("Ingrese más de tres caracteres en el correo");
                }
                if (empleado.getCorreo().trim().length() > 75) {
                    throw new BusinessException("Debe ingresar menos de 75 caracteres en el correo");
                }
                if (!validarCorreo(empleado.getCorreo())) {
                    throw new BusinessException("La dirección de correo electrónico es inválida");
                }
                //direccion
                if (empleado.getDireccion().trim().isEmpty()){
                    throw new BusinessException("Dirección del empleado esta vacio ఠ_ఠ");
                }
                if (empleado.getDireccion().trim().length() < 6){
                    throw new BusinessException("Dirección debe ser mayor a 6 caracteres ఠ_ఠ");
                }
                if (empleado.getDireccion().trim().length() > 100){
                    throw new BusinessException("Dirección no debe ser mayor a 100 caracteres ఠ_ఠ");
                }
                empleado.setNombre(empleado.getNombre().trim().toUpperCase());

                Empleado existingEmpleado =new Empleado();
                existingEmpleado.setIdEmpleado(empleado.getIdEmpleado());
                existingEmpleado.setNombre(empleado.getNombre());
                existingEmpleado.setDocumento(empleado.getDocumento());
                existingEmpleado.setTipoDocumento(empleado.getTipoDocumento());
                existingEmpleado.setFechaNacimiento(empleado.getFechaNacimiento());
                existingEmpleado.setTelefono(empleado.getTelefono());
                existingEmpleado.setFechaIngreso(empleado.getFechaIngreso());
                existingEmpleado.setCorreo(empleado.getCorreo());
                existingEmpleado.setDireccion(empleado.getDireccion());
                return repository.save(existingEmpleado);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
    }
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;
    private TipoDocumento buscarDocumento(long id) throws BusinessException, NotFoundException {
        Optional<TipoDocumento> opt=null;
        try {
            opt = tipoDocumentoRepository.findById(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("No se encontro el documento" + id);
        }
        System.out.println(opt.get().getNombreDocumento());
        return opt.get();
    }
    private void contiene(TipoDocumento tipoDocumento, String nombreDoc) throws BusinessException {
        try {
            String valorInicial = nombreDoc.substring(0,2);
            Pattern pat = Pattern.compile("[\\d]*");
            Matcher mat = pat.matcher(nombreDoc);
            Pattern patron=Pattern.compile("[01]");
            Matcher validarNumero = patron.matcher(nombreDoc.substring(0,1));
            if (tipoDocumento.getNombreDocumento().contains("DNI")){
                if(!mat.matches()){
                    throw new BusinessException("DNI debe ser númerico");
                }
                if (nombreDoc.length() != 13){
                    throw new BusinessException("Cantidad de digitos DNI invalido! requeridos:13");
                }
                if (!validarNumero.matches()){
                    throw new BusinessException("DNI invalido! debe comenzar con 0 u 1");
                }
                if(Integer.parseInt(valorInicial) < 1){
                    throw new BusinessException("DNI invalido! código depto no existe!");
                }
                if(Integer.parseInt(valorInicial) > 18){
                    throw new BusinessException("DNI invalido! código depto no existe!");
                }
            }else if (tipoDocumento.getNombreDocumento().contains("RTN")){
                if(!mat.matches()){
                    throw new BusinessException("RTN debe ser númerico");
                }
                if (nombreDoc.length() != 14){
                    throw new BusinessException("Cantidad de digitos RTN invalido! requeridos:14");
                }
                if (!validarNumero.matches()){
                    throw new BusinessException("RTN invalido! debe comenzar con 0 u 1");
                }

            }else {
                Pattern patDoc = Pattern.compile("[a-zA-Z\\d]]*");
                Matcher matDoc = patDoc.matcher(nombreDoc.trim());
//                if (!matDoc.matches()){
//                    throw new BusinessException("Documento no debe contener espacios ni caracteres especiales");
//                }
                if (nombreDoc.length() < 7){
                    throw new BusinessException("Documento no debe ser menor a 7 de caracteres !");
                }
                if (nombreDoc.length() > 11){
                    //System.out.println(nombreDoc.length());  imporimir en consola
                    throw new BusinessException("Documento no debe ser mayor a 11 de caracteres !");
                }
                Pattern numerosCai = Pattern.compile("[0-9]+");
                if(!numerosCai.matcher(nombreDoc.trim()).find()){
                    throw new BusinessException("Pasaporte debe contener almenos un número");
                }
                Pattern letrasCai = Pattern.compile("[a-zA-Z]+");
                if(!letrasCai.matcher(nombreDoc.trim()).find()){
                    throw new BusinessException("Pasaporte debe contener almenos una letra");
                }
            }
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }
    private boolean validarCorreo(String correo) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]{3,}+(\\.[_A-Za-z0-9-]{3,}+)*@" +
                "[A-Za-z0-9-]{2,}+(\\.[A-Za-z0-9]{2,}+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(correo);

        return matcher.find();
    }
    private void validarEmpleado(Empleado empleado) throws BusinessException{

    }
}