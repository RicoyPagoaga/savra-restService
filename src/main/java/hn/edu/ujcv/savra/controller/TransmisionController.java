package hn.edu.ujcv.savra.controller;

import hn.edu.ujcv.savra.entity.Transmision;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.service.TransmisionService.TransmisionService;
import hn.edu.ujcv.savra.utils.Constants;
import hn.edu.ujcv.savra.utils.RestApiError;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("api/v1/transmisiones")
public class TransmisionController {
    @Autowired
    private TransmisionService service;
    @PostMapping("/addTransmision")
    public ResponseEntity<Object> guardarTransmision(@RequestBody Transmision pTransmision){
        try{
            service.saveTransmision(pTransmision);
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("location", Constants.URL_BASE_TRANSMISIONES + pTransmision.getIdTransmision());
            return new ResponseEntity(pTransmision,responseHeader, HttpStatus.CREATED);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Información enviada no valida!",e.getMessage());
            return new ResponseEntity<>(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("")
    public ResponseEntity<List<Transmision>> obtenerTransmisones(){
        try{
            return new ResponseEntity(service.getTransmisiones(),HttpStatus.OK);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Lista no válida.",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Transmision> buscarTrasnmisionPorId(@PathVariable long id){
       return null;
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Any> eliminarTransmision(@PathVariable long id){
        try{
            service.deteleTransmision(id);
            return new ResponseEntity(HttpStatus.OK);
        }catch (BusinessException e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Trasmisión no válida",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (NotFoundException e){
            RestApiError apiError = new RestApiError(HttpStatus.NOT_FOUND,
                    "No se encontró la transmisión",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("")
    public ResponseEntity<Any> actualizarTransmision(@RequestBody Transmision pTransmision){
        try{
            service.updateTransmision(pTransmision);
            return new ResponseEntity(pTransmision,HttpStatus.OK);
        }catch (BusinessException e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Trasmisión no válida!",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (NotFoundException e){
            RestApiError apiError = new RestApiError(HttpStatus.NOT_FOUND,
                    "No se encontró la trasmisión!",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.NOT_FOUND);
        }
    }
}
