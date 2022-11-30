package hn.edu.ujcv.savra.service.CargoService;

import hn.edu.ujcv.savra.entity.Cargo;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CargoService implements ICargoService{
    @Autowired
    private CargoRepository repository;

    @Override
    public Cargo saveCargo(Cargo cargo) throws BusinessException {
        try {
            //Nombre Cargo
            if (cargo.getNombre().trim().isEmpty()){
                throw new BusinessException("El nombre no debe estar vacio");
            }
            if(cargo.getNombre().trim().length() <5){
                throw new BusinessException("Nombre Cargo debe contener mínimo 5 carácteres ఠ_ఠ");
            }
            if(cargo.getNombre().trim().length() >50){
                throw new BusinessException("Nombre Cargo no debe contener mas de 50 carácteres ఠ_ఠ");
            }
            Pattern patDoc = Pattern.compile("^([a-zA-ZÀ]+)(\\s[a-zA-ZÀ]+)*$");
            Matcher matDoc = patDoc.matcher(cargo.getNombre().trim());
            if(!matDoc.matches()){
                throw new BusinessException("Nombre Cargo no debe contener números o carácteres especiales ఠ_ఠ");
            }

            //Descripcion
            if (cargo.getDescripcion().trim().isEmpty()){
                throw new BusinessException("La descripción no debe estar vacía");
            }
            if(cargo.getDescripcion().trim().length() < 3){
                throw new BusinessException("la descripción debe contener mínimo 3 carácteres ఠ_ఠ");
            }
            if(cargo.getDescripcion().trim().length() >80){
                throw new BusinessException("la descripción no debe contener más de 80 carácteres ఠ_ఠ");
            }
            //salario
            if (String.valueOf(cargo.getSalario()).isEmpty()){
                throw new BusinessException("Salario esta vacío");
            }
            if (cargo.getSalario() <= 0){
                throw new BusinessException("Salario no debe ser menor o igual a 0");
            }
            cargo.setNombre(cargo.getNombre().toUpperCase());
            return repository.save(cargo);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Cargo> saveCargos(List<Cargo> cargos) throws BusinessException {
        try {
            for (Cargo cargo1:cargos) {
                if (cargo1.getNombre().trim().isEmpty()){
                    throw new BusinessException("No debe estar vacio");
                }
            }
            return repository.saveAll(cargos);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Cargo> getCargos() throws BusinessException {
        try {
            return repository.findAll();
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Cargo getCargoById(long id) throws BusinessException, NotFoundException {
        Optional<Cargo> opt= null;
        try {
            opt=repository.findById(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el Cargo"+id);
        }
        return opt.get();
    }

    @Override
    public Cargo getCargoByNombre(String nombre) throws BusinessException, NotFoundException {
        Optional<Cargo> opt= null;
        try {
            opt=repository.findCargoByNombre(nombre);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el cargo :v"+nombre);
        }
        return opt.get();
    }

    @Override
    public void deleteCargo(long id) throws BusinessException, NotFoundException {
        Optional<Cargo> opt= null;
        try {
            opt=repository.findById(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el cargo"+id);
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
    public Cargo updateCargo(Cargo cargo) throws BusinessException, NotFoundException {
        Optional<Cargo> opt= null;
        try {
            opt=repository.findById(cargo.getIdCargo());
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el cargo"+ cargo.getIdCargo());
        }
        else {
            try {
                cargo.setNombre(cargo.getNombre().toUpperCase());
                Cargo existingCargo=new Cargo();
                existingCargo.setIdCargo(cargo.getIdCargo());
                existingCargo.setNombre(cargo.getNombre());
                existingCargo.setDescripcion(cargo.getDescripcion());
                existingCargo.setSalario(cargo.getSalario());
                return repository.save(existingCargo);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
    }

}