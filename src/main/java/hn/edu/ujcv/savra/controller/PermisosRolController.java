package hn.edu.ujcv.savra.controller;

import hn.edu.ujcv.savra.entity.ModuloAccion;
import hn.edu.ujcv.savra.entity.PermisosRol.PermisosRol;
import hn.edu.ujcv.savra.entity.Rol;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.service.PermisosRolService.PermisosRolService;
import hn.edu.ujcv.savra.utils.Constants;
import hn.edu.ujcv.savra.utils.RestApiError;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/permisos_rol")
public class PermisosRolController {
    @Autowired
    private PermisosRolService service;

    @PostMapping("/addPermisoRol")
    public ResponseEntity<Object> addPermisoRol (@RequestBody PermisosRol moduloPermiso){
        try {
            service.saveModuloPermiso(moduloPermiso);
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("location", Constants.URL_BASE_PERMISOS_ROL + moduloPermiso.getModuloAccion().getIdModuloAccion());
            return new ResponseEntity(moduloPermiso,responseHeader, HttpStatus.CREATED);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,

                    "La nformacion enviada no es valida ;V ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addPermisosRol")
    public ResponseEntity<Any> addPermisosRol (@RequestBody List<PermisosRol> permisosRol){
        try {
            return new ResponseEntity(service.saveModuloPermisos(permisosRol),HttpStatus.CREATED);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "La nformacion enviada no es valida XD ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity <List<PermisosRol>> findAllPermisosRol(){
        try {
            return new ResponseEntity(service.getModuloPermisos(),HttpStatus.OK);

        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "La nformacion enviada no es valida XD ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Any> updatePermisoRol (@RequestBody PermisosRol permisoRol){
        try {
            service.updateModuloPermiso(permisoRol);
            return new ResponseEntity(permisoRol,HttpStatus.OK);

        } catch (BusinessException e) {
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.getMessage());
            return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.getMessage());
            return new ResponseEntity(apiError, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{idModuloAccion}/{idRol}")
    public  ResponseEntity<Any> deletePermisoRol (@PathVariable long idModuloAccion, @PathVariable long idRol){
        try {
            service.deleteModuloPermiso(idModuloAccion,idRol);
            return new ResponseEntity(HttpStatus.OK);

        } catch (BusinessException e) {
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.getMessage());
            return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.getMessage());
            return new ResponseEntity(apiError, HttpStatus.NOT_FOUND);
        }
    }

}
