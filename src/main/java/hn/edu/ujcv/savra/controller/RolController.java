package hn.edu.ujcv.savra.controller;
import hn.edu.ujcv.savra.entity.Rol;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.service.RolService.RolService;
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
@RequestMapping("/api/v1/rol")
public class RolController {
    @Autowired
    private RolService service;
    @PostMapping("/addRol")
    public ResponseEntity<Object> addRol (@RequestBody Rol rol){
        try {
            service.saveRol(rol);
            HttpHeaders responseHeader= new HttpHeaders();
            responseHeader.set("location", Constants.URL_BASE_ROL + rol.getIdRol());
            return  new ResponseEntity(rol,responseHeader, HttpStatus.CREATED);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "La nformacion enviada no es valida ;V ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/addRols")
    public ResponseEntity<Any> addRols (@RequestBody List<Rol> rols){
        try {
            return new ResponseEntity(service.saveRoles(rols),HttpStatus.CREATED);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "La nformacion enviada no es valida V; ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("")
    public ResponseEntity <List<Rol>> findAllRol(){
        try {
            return new ResponseEntity(service.getRol(),HttpStatus.OK);

        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "La nformacion enviada no es valida XD ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Rol> findRolById (@PathVariable Long id){
        try {
            return new ResponseEntity(service.getRolById(id),HttpStatus.OK);

        }catch (BusinessException e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("")
    public ResponseEntity<Any> updateRol (@RequestBody Rol rol){
        try {
            service.updateRol(rol);
            return new ResponseEntity(rol,HttpStatus.OK);

        }catch (BusinessException e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Any> deleteRol (@PathVariable Long id){
        try {
            service.deleteRol(id);
            return new ResponseEntity(HttpStatus.OK);

        }catch (BusinessException e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
