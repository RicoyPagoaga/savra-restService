package hn.edu.ujcv.savra.controller.ImpuestosController;

import hn.edu.ujcv.savra.entity.Impuesto.Impuesto;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.service.ImpuestosService.ImpuestoService.ImpuestoService;
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
@RequestMapping("/api/v1/impuestos")
public class ImpuestoController {

    @Autowired
    private ImpuestoService service;

    @PostMapping("/addImpuesto/{valor}/{nulo}")
    public ResponseEntity<Object> addImpuesto(@RequestBody Impuesto impuesto,
                                              @PathVariable int valor,
                                              @PathVariable boolean nulo) {
        try {
            service.saveImpuesto(impuesto, valor, nulo);
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("location", Constants.URL_BASE_IMPUESTO + impuesto.getIdImpuesto());
            return new ResponseEntity(impuesto, responseHeader, HttpStatus.CREATED);
        } catch (BusinessException e) {
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.getMessage());
            return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addImpuestos")
    public ResponseEntity<Any> addImpuestos(@RequestBody List<Impuesto> impuestos) {
        try {
            return new ResponseEntity(service.saveImpuestos(impuestos), HttpStatus.CREATED);
        } catch (BusinessException e) {
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.getMessage());
            return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Impuesto>> findAllImpuestos() {
        try {
            return new ResponseEntity(service.getImpuestos(), HttpStatus.OK);
        } catch (BusinessException e) {
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.getMessage());
            return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Impuesto> findImpuestoById(@PathVariable long id) {
        try {
            return new ResponseEntity(service.getImpuestoById(id), HttpStatus.OK);
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
    public ResponseEntity<Impuesto> findImpuestoByNombre(@PathVariable String nombre) {
        try {
            return new ResponseEntity(service.getImpuestoByNombre(nombre), HttpStatus.OK);
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

    @PutMapping("/{valor}/{nulo}")
    public ResponseEntity<Any> updateImpuesto(@RequestBody Impuesto impuesto,
                                              @PathVariable int valor,
                                              @PathVariable boolean nulo) {
        try {
            service.updateImpuesto(impuesto, valor, nulo);
            return new ResponseEntity(impuesto, HttpStatus.OK);
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
    public ResponseEntity<Any> deleteImpuesto(@PathVariable long id) {
        try {
            service.deleteImpuesto(id);
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
