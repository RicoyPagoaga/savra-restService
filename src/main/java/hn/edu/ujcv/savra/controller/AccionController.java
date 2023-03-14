package hn.edu.ujcv.savra.controller;

import hn.edu.ujcv.savra.entity.Cargo;
import hn.edu.ujcv.savra.entity.Accion;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.service.AccionService.AccionService;
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
@RequestMapping("/api/v1/acciones")
public class AccionController {
    @Autowired
    private AccionService service;

    @PostMapping("/addAccion")
    public ResponseEntity<Accion> addAccion (@RequestBody Accion accion){
        try {
            service.saveAccion(accion);
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("location", Constants.URL_BASE_ACCION + accion.getIdAccion());
            return new ResponseEntity(accion,responseHeader, HttpStatus.CREATED);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "La nformacion enviada no es valida ;V ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/addAcciones")
    public ResponseEntity<Any> addAcciones (@RequestBody List<Accion> accions){
        try {
            return new ResponseEntity(service.saveAccions(accions),HttpStatus.CREATED);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "La nformacion enviada no es valida XD ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("")
    public ResponseEntity <List<Accion>> findAllAcciones(){
        try {
            return new ResponseEntity(service.getAccion(),HttpStatus.OK);

        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "La nformacion enviada no es valida XD ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/id/{id}")
    public ResponseEntity <Cargo> findAccionById (@PathVariable Long id){
        try {
            return new ResponseEntity(service.getAccionById(id),HttpStatus.OK);

        }catch (BusinessException e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("")
    public ResponseEntity<Any> updateAccion (@RequestBody Accion accion){
        try {
            service.updateAccion(accion);
            return new ResponseEntity(accion,HttpStatus.OK);

        }catch (BusinessException e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<Any> deleteAccion (@PathVariable Long id){
        try {
            service.deleteAccion(id);
            return new ResponseEntity(HttpStatus.OK);

        }catch (BusinessException e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
