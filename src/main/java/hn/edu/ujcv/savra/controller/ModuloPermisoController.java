package hn.edu.ujcv.savra.controller;

import hn.edu.ujcv.savra.entity.Cargo;
import hn.edu.ujcv.savra.entity.ModuloPermiso.ModuloPermiso;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.service.ModuloPermisoService.ModuloPermisoService;
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
@RequestMapping("/api/v1/modulo_permisos")
public class ModuloPermisoController {
    @Autowired
    private ModuloPermisoService service;

    @PostMapping("/addModuloPermiso")
    public ResponseEntity<Object> addUsuarioPermiso (@RequestBody ModuloPermiso moduloPermiso){
        try {

            service.saveModuloPermiso(moduloPermiso);
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("location", Constants.URL_BASE_MODULO_PERMISO + moduloPermiso.getIdModulo());
            return new ResponseEntity(moduloPermiso,responseHeader, HttpStatus.CREATED);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,

                    "La nformacion enviada no es valida ;V ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @PostMapping("/addModuloPermisos")
    public ResponseEntity<Any> addUsuarioPermisos (@RequestBody List<ModuloPermiso> moduloPermisos){
        try {
            return new ResponseEntity(service.saveModuloPermisos(moduloPermisos),HttpStatus.CREATED);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "La nformacion enviada no es valida XD ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity <List<ModuloPermiso>> findAllUsuarioPermisos(){
        try {
            return new ResponseEntity(service.getModuloPermisos(),HttpStatus.OK);

        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "La nformacion enviada no es valida XD ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/id/{id}/{idPermiso}")
    public ResponseEntity <ModuloPermiso> findModuloPermisoById (@PathVariable Long id, @PathVariable Long idPermiso){
        try {
            return new ResponseEntity(service.getModuloPermisoById(id,idPermiso),HttpStatus.OK);

        }catch (BusinessException e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("")
    public ResponseEntity<Any> updateUsuarioPermiso (@RequestBody ModuloPermiso moduloPermiso){
        try {
            service.updateModuloPermiso(moduloPermiso);
            return new ResponseEntity(moduloPermiso,HttpStatus.OK);

        }catch (BusinessException e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}/{idPermiso}")
    public  ResponseEntity<Any> deleteUsuarioPermiso (@PathVariable Long id,@PathVariable Long idPermiso){
        try {
            service.deleteModuloPermiso(id,idPermiso);
            return new ResponseEntity(HttpStatus.OK);

        }catch (BusinessException e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
