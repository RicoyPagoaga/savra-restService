package hn.edu.ujcv.savra.service.EmpleadoCargo;

import hn.edu.ujcv.savra.entity.EmpleadoCargo.EmpleadoCargo;
import hn.edu.ujcv.savra.entity.EmpleadoCargo.EmpleadoCargoPK;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.EmpleadoCargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoCargoService implements IEmpleadoCargoService{
    @Autowired
    private EmpleadoCargoRepository repository;

    @Override
    public EmpleadoCargo saveEmpleadoCargo(EmpleadoCargo empleadoCargo) throws BusinessException {
        try {
            /*if (empleadoCargo.getFechaFinal()==null){
                throw new BusinessException("La fecha no debe estar vacia");
            }*/
            return repository.save(empleadoCargo);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<EmpleadoCargo> saveEmpleadoCargos(List<EmpleadoCargo> empleadoCargos) throws BusinessException {
        try {
            return repository.saveAll(empleadoCargos);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<EmpleadoCargo> getEmpleadoCargos() throws BusinessException {
        try {
            return repository.findAll();

        }catch (Exception e ){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public EmpleadoCargo getEmpleadoCargoById(long id,String fechaInicio) throws BusinessException, NotFoundException {
        Optional<EmpleadoCargo> opt= null;
        long tiempo = Long.valueOf(fechaInicio);
        Timestamp date = new Timestamp(tiempo);
        EmpleadoCargoPK pk = new EmpleadoCargoPK(id, date);
        try {
            opt=repository.findById(pk);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el empleado cargo"+id);
        }
        return opt.get();
    }

    /*@Override
    public EmpleadoCargo getEmpleadoCargoByfecha(String fecha) throws BusinessException, NotFoundException {
        Optional<EmpleadoCargo> opt= null;
        try {
            opt=repository.findEmpleadoCargoByiOrderBydEmpleado(fecha);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el empleado :v"+fecha);
        }
        return opt.get();
    }*/

    @Override
    public void deleteEmpleadoCargo(long id, String fechaInicio) throws BusinessException, NotFoundException {
        Optional<EmpleadoCargo> opt= null;
        long tiempo = Long.valueOf(fechaInicio);
        Timestamp date = new Timestamp(tiempo);
        EmpleadoCargoPK pk = new EmpleadoCargoPK(id, date);
        try {
            opt=repository.findById(pk);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el empleado"+id);
        }
        else {
            try {
                repository.deleteById(pk);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
    }

    @Override
    public EmpleadoCargo updateEmpleadoCargo(EmpleadoCargo empleadoCargo) throws BusinessException, NotFoundException {
        Optional<EmpleadoCargo> opt= null;
        EmpleadoCargoPK pk = new EmpleadoCargoPK(empleadoCargo.getIdEmpleado(),empleadoCargo.getFechaInicio());
        try {
            opt=repository.findById(pk);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el empleado"+ empleadoCargo.getIdEmpleado());
        }
        else {
            try {
                EmpleadoCargo existingEmpleadoCargo =new EmpleadoCargo();
                existingEmpleadoCargo.setFechaInicio(empleadoCargo.getFechaInicio());
                existingEmpleadoCargo.setIdEmpleado(empleadoCargo.getIdEmpleado());
                existingEmpleadoCargo.setFechaFinal(empleadoCargo.getFechaFinal());
                existingEmpleadoCargo.setIdCargo(empleadoCargo.getIdCargo());
                return repository.save(existingEmpleadoCargo);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
    }

}
