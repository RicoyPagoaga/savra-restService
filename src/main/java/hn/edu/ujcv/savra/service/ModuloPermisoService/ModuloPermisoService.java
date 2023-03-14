package hn.edu.ujcv.savra.service.ModuloPermisoService;

import hn.edu.ujcv.savra.entity.ModuloPermiso.ModuloPermiso;
import hn.edu.ujcv.savra.entity.ModuloPermiso.ModuloPermisoPK;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.ModuloPermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuloPermisoService implements IModuloPermisoService {
    @Autowired
    private ModuloPermisoRepository repository;
    @Override
    public ModuloPermiso saveModuloPermiso(ModuloPermiso moduloPermiso) throws BusinessException {
        try {
            return repository.save(moduloPermiso);

        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<ModuloPermiso> saveModuloPermisos(List<ModuloPermiso> moduloPermisos) throws BusinessException {
        try {
            return repository.saveAll(moduloPermisos);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<ModuloPermiso> getModuloPermisos() throws BusinessException {
        try {
            return repository.findAll();
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public ModuloPermiso getModuloPermisoById(long id, long idmodulo) throws BusinessException, NotFoundException {
        Optional<ModuloPermiso> opt=null;
        ModuloPermisoPK pk = new ModuloPermisoPK(id,idmodulo);
        try {
            opt= repository.findById(pk);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el modulo permiso " + id);
        }
        return opt.get();
    }

  //  @Override
    //public UsuarioPermiso getUsuarioPermisoByIdUsuario(String usuario) throws BusinessException, NotFoundException {
      //  return null;
    //}

    @Override
    public void deleteModuloPermiso(long id, long idmodulo) throws BusinessException, NotFoundException {
        Optional<ModuloPermiso> opt=null;
        ModuloPermisoPK pk = new ModuloPermisoPK(id,idmodulo);
        try {
            opt = repository.findById(pk);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el modulo permiso" + id);
        } else {
            try {
                repository.deleteById(pk);
            } catch (Exception e1) {
                throw new BusinessException(e1.getMessage());
            }
        }

    }

    @Override
    public ModuloPermiso updateModuloPermiso(ModuloPermiso moduloPermiso) throws BusinessException, NotFoundException {
        Optional<ModuloPermiso> opt=null;
        ModuloPermisoPK pk = new ModuloPermisoPK(moduloPermiso.getIdModulo(), moduloPermiso.getIdModulo());
        try {

            opt = repository.findById(pk);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el usuario " + moduloPermiso.getIdModulo());
        } else{
            try {
                ModuloPermiso existingModuloPermiso = new ModuloPermiso();
                existingModuloPermiso.setIdModulo(moduloPermiso.getIdModulo());
                existingModuloPermiso.setIdPermiso(moduloPermiso.getIdPermiso());
                return repository.save(existingModuloPermiso);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }
}