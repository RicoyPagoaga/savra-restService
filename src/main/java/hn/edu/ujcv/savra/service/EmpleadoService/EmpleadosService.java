package hn.edu.ujcv.savra.service.EmpleadoService;
import hn.edu.ujcv.savra.entity.Empleado;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.EmpleadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadosService implements IEmpleadosService{

    @Autowired
    private EmpleadosRepository repository;

    @Override
    public Empleado saveEmpleados(Empleado empleado) throws BusinessException {
        try {
            if (empleado.getNombre().isEmpty()){
                throw new BusinessException("El campo no debe estar vacio");
            }if(empleado.getNombre().length()<5){
                throw new BusinessException("El nombre debe tener mas de 10 caracteres");
            }if (empleado.getTelefono().length()<8){
                throw new BusinessException("No debe tener menos de 8 caracteres");
            }
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
                Empleado existingEmpleado =new Empleado();
                existingEmpleado.setIdEmpleado(empleado.getIdEmpleado());
                existingEmpleado.setNombre(empleado.getNombre());
                existingEmpleado.setDocumento(empleado.getDocumento());
                existingEmpleado.setIdTipoDocumento(empleado.getIdTipoDocumento());
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
}
