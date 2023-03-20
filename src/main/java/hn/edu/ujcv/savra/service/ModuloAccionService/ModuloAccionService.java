package hn.edu.ujcv.savra.service.ModuloAccionService;

import hn.edu.ujcv.savra.entity.ModuloAccion;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.ModuloAccionRepository;
import hn.edu.ujcv.savra.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuloAccionService implements IModuloAccionService {
    private Log mi_log = new Log();

    @Autowired
    private ModuloAccionRepository repository;

    @Override
    public ModuloAccion saveModuloAccion(ModuloAccion moduloAccion) throws BusinessException {
        try {
            return repository.save(moduloAccion);
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<ModuloAccion> saveModulosAccion(List<ModuloAccion> modulosAccion) throws BusinessException {
        try {
            return repository.saveAll(modulosAccion);
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<ModuloAccion> getModulosAccion() throws BusinessException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public ModuloAccion getModuloAccionById(long id) throws BusinessException, NotFoundException {
        Optional<ModuloAccion> opt=null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró la acción de módulo: " + id);
            throw new NotFoundException("No se encontró la acción de módulo: " + id);
        }
        return opt.get();
    }

    @Override
    public void deleteModuloAccion(long id) throws BusinessException, NotFoundException {
        Optional<ModuloAccion> opt = null;
        try{
            opt = repository.findById(id);
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró la acción de módulo: " + id);
            throw new NotFoundException("No se encontró la acción de módulo: " + id);
        }else{
            try {
                repository.deleteById(id);
            }catch (Exception e1){
                mi_log.CrearArchivo(this.getClass().getSimpleName());
                mi_log.logger.severe(e1.getMessage());
                throw new BusinessException(e1.getMessage());
            }
        }
    }

    @Override
    public ModuloAccion updateModuloAccion(ModuloAccion moduloAccion) throws BusinessException, NotFoundException {
        Optional<ModuloAccion> opt = null;
        try {
            opt = repository.findById(moduloAccion.getIdModuloAccion());
        } catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró la acción: " + moduloAccion.getAccion().getIdAccion() + " del módulo:" + moduloAccion.getModulo().getIdModulo());
            throw new NotFoundException("No se encontró la acción: " + moduloAccion.getAccion().getIdAccion() + " del módulo:" + moduloAccion.getModulo().getIdModulo());
        }else {
            try {
                ModuloAccion newModulo = new ModuloAccion();
                newModulo.setIdModuloAccion(moduloAccion.getIdModuloAccion());
                newModulo.setModulo(moduloAccion.getModulo());
                newModulo.setAccion(moduloAccion.getAccion());
                return repository.save(newModulo);
            }catch (Exception e1){
                mi_log.CrearArchivo(this.getClass().getSimpleName());
                mi_log.logger.severe(e1.getMessage());
                throw new BusinessException(e1.getMessage());
            }
        }
    }

}
