package hn.edu.ujcv.savra.controller;

import hn.edu.ujcv.savra.entity.MetodoPago;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.service.MetodoPagoService.MetodoPagoService;
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
@RequestMapping("/api/v1/metodos_pago")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoService service;

    @PostMapping("/addMetodoPago")
    public ResponseEntity<Object> addMetodoPago(@RequestBody MetodoPago metodoPago) {
        try {
            service.saveMetodoPago(metodoPago);
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("location", Constants.URL_BASE_METODO_PAGO + metodoPago.getIdMetodoPago());
            return new ResponseEntity(metodoPago, responseHeader, HttpStatus.CREATED);
        } catch (BusinessException e) {
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.getMessage());
            return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addMetodosPago")
    public ResponseEntity<Any> addMetodosPago(@RequestBody List<MetodoPago> metodosPago) {
        try {
            return new ResponseEntity(service.saveMetodosPago(metodosPago), HttpStatus.CREATED);
        } catch (BusinessException e) {
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.getMessage());
            return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<MetodoPago>> findAllMetodosPago() {
        try {
            return new ResponseEntity(service.getMetodosPago(), HttpStatus.OK);
        } catch (BusinessException e) {
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.getMessage());
            return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<MetodoPago> findMetodoPagoById(@PathVariable long id) {
        try {
            return new ResponseEntity(service.getMetodoPagoById(id), HttpStatus.OK);
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

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<MetodoPago> findMetodoPagoByNombre(@PathVariable String nombre) {
        try {
            return new ResponseEntity(service.getMetodoPagoByNombre(nombre), HttpStatus.OK);
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

    @PutMapping("")
    public ResponseEntity<Any> updateMetodoPago(@RequestBody MetodoPago metodoPago) {
        try {
            service.updateMetodoPago(metodoPago);
            return new ResponseEntity(metodoPago, HttpStatus.OK);
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Any> deleteMetodoPago(@PathVariable long id) {
        try {
            service.deleteMetodoPago(id);
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
