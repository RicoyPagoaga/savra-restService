package hn.edu.ujcv.savra.service.ModuloService;

import hn.edu.ujcv.savra.entity.Modulo;
import hn.edu.ujcv.savra.entity.Pais;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.ModuloRepository;
import hn.edu.ujcv.savra.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ModuloService implements IModuloService {
    private Log mi_log = new Log();
    @Autowired
    private ModuloRepository Repository;
    @Override
    public Modulo saveModulo(Modulo modulo) throws BusinessException, SQLException {
        try {
            return Repository.save(modulo);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Modulo> getModulos() throws BusinessException {
        return  Repository.findAll();
    }

    @Override
    public Modulo getModuloById(long id) throws BusinessException, NotFoundException {
        Optional<Modulo> opt=null;
        try {
            opt=Repository.findById(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }if (!opt.isPresent()){
            throw new NotFoundException("No se encontrò el modulo" +id);
        }
        return opt.get();
    }

    @Override
    public Modulo getModuloByNombre(String nombre) throws BusinessException, NotFoundException {
        Optional<Modulo> opt=null;
        try {
            opt = Repository.findByNombre(nombre);
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró el país " + nombre);
            throw new NotFoundException("No se encontró el país " + nombre);
        }
        return opt.get();
    }

    @Override
    public void deleteModulo(long id) throws BusinessException, NotFoundException {
        Optional<Modulo> opt = null;
        try{
            opt = Repository.findById(id);
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró el modulo: " + id);
            throw new NotFoundException("No se encontró el modulo: " + id);
        }else{
            try {
                Repository.deleteById(id);
            }catch (Exception e1){
                mi_log.CrearArchivo(this.getClass().getSimpleName());
                mi_log.logger.severe(e1.getMessage());
                throw new BusinessException(e1.getMessage());
            }
        }
    }

    @Override
    public Modulo updateModulo(Modulo modulo) throws BusinessException, NotFoundException {
        Optional<Modulo> opt = null;
        try {
            opt = Repository.findById(modulo.getIdModulo());
        } catch (Exception e){
        mi_log.CrearArchivo(this.getClass().getSimpleName());
        mi_log.logger.severe(e.getMessage());
        throw new BusinessException(e.getMessage());
    }
        if (!opt.isPresent()){
        mi_log.CrearArchivo(this.getClass().getSimpleName());
        mi_log.logger.severe("No se encontró el modulo:" + modulo.getIdModulo());
        throw new NotFoundException("No se encontró el modulo:" + modulo.getIdModulo());
    }else {
        try {
            Pais newPais = new Pais();
            Modulo newModulo=new Modulo();
            newModulo.setIdModulo(modulo.getIdModulo());
            newModulo.setNombre(modulo.getNombre());
            return Repository.save(newModulo);
        }catch (Exception e1){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e1.getMessage());
            throw new BusinessException(e1.getMessage());
        }
    }
    }
}
