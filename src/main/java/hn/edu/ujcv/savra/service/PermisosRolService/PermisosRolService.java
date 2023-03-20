package hn.edu.ujcv.savra.service.PermisosRolService;

import hn.edu.ujcv.savra.entity.ModuloAccion;
import hn.edu.ujcv.savra.entity.PermisosRol.PermisosRol;
import hn.edu.ujcv.savra.entity.PermisosRol.PermisosRolPK;
import hn.edu.ujcv.savra.entity.Rol;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.PermisosRolRepository;
import hn.edu.ujcv.savra.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermisosRolService implements IPermisosRolService {
    private Log mi_log = new Log();

    @Autowired
    private PermisosRolRepository repository;

    @Override
    public PermisosRol saveModuloPermiso(PermisosRol moduloPermiso) throws BusinessException {
        try {
            return repository.save(moduloPermiso);
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<PermisosRol> saveModuloPermisos(List<PermisosRol> moduloPermisos) throws BusinessException {
        try {
            return repository.saveAll(moduloPermisos);
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<PermisosRol> getModuloPermisos() throws BusinessException {
        try {
            return repository.findAll();
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public PermisosRol getModuloPermisoById(long idModuloAccion, long idRol) throws BusinessException, NotFoundException {
        Optional<PermisosRol> opt=null;
        PermisosRolPK pk = new PermisosRolPK(idModuloAccion,idRol);
        try {
            opt= repository.findById(pk);
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }if (!opt.isPresent()) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró el modulo permiso " + idModuloAccion);
            throw new NotFoundException("No se encontró el modulo permiso " + idModuloAccion);
        }
        return opt.get();
    }

    @Override
    public void deleteModuloPermiso(long idModuloAccion, long idRol) throws BusinessException, NotFoundException {
        Optional<PermisosRol> opt=null;
        PermisosRolPK pk = new PermisosRolPK(idModuloAccion,idRol);
        try {
            opt = repository.findById(pk);
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró el modulo permiso" + idModuloAccion);
            throw new NotFoundException("No se encontró el modulo permiso" + idModuloAccion);
        } else {
            try {
                repository.deleteById(pk);
            } catch (Exception e1) {
                mi_log.CrearArchivo(this.getClass().getSimpleName());
                mi_log.logger.severe(e1.getMessage());
                throw new BusinessException(e1.getMessage());
            }
        }

    }

    @Override
    public PermisosRol updateModuloPermiso(PermisosRol moduloPermiso) throws BusinessException, NotFoundException {
        Optional<PermisosRol> opt=null;
        PermisosRolPK pk = new PermisosRolPK(moduloPermiso.getModuloAccion().getIdModuloAccion(), moduloPermiso.getRol().getIdRol());
        try {
            opt = repository.findById(pk);
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró el usuario " + moduloPermiso.getModuloAccion().getIdModuloAccion());
            throw new NotFoundException("No se encontró el usuario " + moduloPermiso.getModuloAccion().getIdModuloAccion());
        } else{
            try {
                PermisosRol existingModuloPermiso = new PermisosRol();
                existingModuloPermiso.setIdModuloAccion(moduloPermiso.getIdModuloAccion());
                existingModuloPermiso.setIdRol(moduloPermiso.getIdRol());
                return repository.save(existingModuloPermiso);
            } catch (Exception e) {
                mi_log.CrearArchivo(this.getClass().getSimpleName());
                mi_log.logger.severe(e.getMessage());
                throw new BusinessException(e.getMessage());
            }
        }
    }
}