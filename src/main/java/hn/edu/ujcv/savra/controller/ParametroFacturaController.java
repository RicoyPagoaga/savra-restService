package hn.edu.ujcv.savra.controller;

import hn.edu.ujcv.savra.entity.ParametroFactura;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.service.ParametroFacturaService.ParametroFacturaService;
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
@RequestMapping("/api/v1/parametros")
public class ParametroFacturaController {
    @Autowired
    private ParametroFacturaService service;
    @PostMapping("/addParametro")
    public ResponseEntity<Object> guardarParametro(@RequestBody ParametroFactura pParametro){
        try {
            service.saveParametro(pParametro);
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("location", Constants.URL_BASE_PAISES+pParametro.getIdParametro());
            return new ResponseEntity(pParametro,responseHeader, HttpStatus.CREATED);
        }catch (Exception e){
            RestApiError restApiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al guardar el Parámetro",e.getMessage());
            return new ResponseEntity<>(restApiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("")
    public ResponseEntity<Any> actualizarParametro(@RequestBody ParametroFactura pParametro){
        try {
            service.updateParametro(pParametro);
            return new ResponseEntity(pParametro,HttpStatus.OK);
        }catch(BusinessException e){
            RestApiError restApiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al guardar el Parámetro",e.getMessage());
            return new ResponseEntity(restApiError,HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e){
            RestApiError restApiError = new RestApiError(HttpStatus.NOT_FOUND,
                    "Error al actualizar el Parámetro",e.getMessage());
            return new ResponseEntity(restApiError,HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("")
    public ResponseEntity<List<ParametroFactura>> obtenerParametros(){
        try {
            return new ResponseEntity(service.getParametrosFactura(),HttpStatus.OK);
        }catch (Exception e){
            RestApiError restApiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Lista no válida!",e.getMessage());
            return new ResponseEntity(restApiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/delete/{idParametro}")
    public ResponseEntity<Any> eliminarParametro(@PathVariable long idParametro){
        try {
            service.deleteParametro(idParametro);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (BusinessException e){
            RestApiError restApiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al eliminar el Parámetro",e.getMessage());
            return new ResponseEntity(restApiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (NotFoundException e){
            RestApiError restApiError = new RestApiError(HttpStatus.NOT_FOUND,
                    "Error al eliminar el Parámetro",e.getMessage());
            return new ResponseEntity(restApiError,HttpStatus.NOT_FOUND);
        }
    }
}
