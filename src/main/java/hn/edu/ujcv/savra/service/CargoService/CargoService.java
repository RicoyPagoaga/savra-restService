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
            if(cargo.getNombre().trim().length() >25){
                throw new BusinessException("Nombre Cargo no debe contener mas de 25 carácteres ఠ_ఠ");
            }
            Pattern patDoc = Pattern.compile("^([a-zA-Z]+)(\\s[a-zA-Z]+)*$");
            Matcher matDoc = patDoc.matcher(cargo.getNombre().trim());
            if(!matDoc.matches()){
                throw new BusinessException("Nombre Cargo no debe contener números o carácteres especiales ఠ_ఠ");
            }

            //Descripcion
            if (cargo.getDescrpcion().trim().isEmpty()){
                throw new BusinessException("La descripción no debe estar vacía");
            }
            if(cargo.getDescrpcion().trim().length() < 3){
                throw new BusinessException("Descripción Cargo debe contener mínimo 3 carácteres ఠ_ఠ");
            }
            if(cargo.getDescrpcion().trim().length() >50){
                throw new BusinessException("Descripción Cargo debe contener menos de 50 carácteres ఠ_ఠ");
            }

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
                //Nombre Cargo
                if (cargo.getNombre().trim().isEmpty()){
                    throw new BusinessException("El nombre no debe estar vacio");
                }
                if(cargo.getNombre().trim().length() <5){
                    throw new BusinessException("Nombre Cargo debe contener mínimo 5 carácteres ఠ_ఠ");
                }
                if(cargo.getNombre().trim().length() >25){
                    throw new BusinessException("Nombre Cargo no debe contener mas de 25 carácteres ఠ_ఠ");
                }
                Pattern patDoc = Pattern.compile("^([a-zA-Z]+)(\\s[a-zA-Z]+)*$");
                Matcher matDoc = patDoc.matcher(cargo.getNombre().trim());
                if(!matDoc.matches()){
                    throw new BusinessException("Nombre Cargo no debe contener números o carácteres especiales ఠ_ఠ");
                }

                //Descripcion
                if (cargo.getDescrpcion().trim().isEmpty()){
                    throw new BusinessException("La descripción no debe estar vacía");
                }
                if(cargo.getDescrpcion().trim().length() < 3){
                    throw new BusinessException("Descripción Cargo debe contener mínimo 3 carácteres ఠ_ఠ");
                }
                if(cargo.getDescrpcion().trim().length() >50){
                    throw new BusinessException("Descripción Cargo debe contener menos de 50 carácteres ఠ_ఠ");
                }

                Cargo existingCargo=new Cargo();
                existingCargo.setIdCargo(cargo.getIdCargo());
                existingCargo.setNombre(cargo.getNombre());
                existingCargo.setDescrpcion(cargo.getDescrpcion());
                return repository.save(existingCargo);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
    }

}