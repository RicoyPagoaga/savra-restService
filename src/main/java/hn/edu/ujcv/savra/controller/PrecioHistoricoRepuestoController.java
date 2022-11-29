package hn.edu.ujcv.savra.controller;

import hn.edu.ujcv.savra.entity.PrecioHistoricoRepuesto.PrecioHistoricoRepuesto;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.service.PrecioHistoricoRepuestoService.PrecioHistoricoRepuestoService;
import hn.edu.ujcv.savra.utils.Constants;
import hn.edu.ujcv.savra.utils.RestApiError;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/precios_repuesto")
public class PrecioHistoricoRepuestoController {

    @Autowired
    private PrecioHistoricoRepuestoService service;

    @PostMapping("/addPrecioHistoricoRepuesto")
    public ResponseEntity<Object> addPrecioHistoricoRepuesto(@RequestBody PrecioHistoricoRepuesto precioHistorico) {
        try {
            service.savePrecioRepuesto(precioHistorico);
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("location", Constants.URL_BASE_PRECIO_REPUESTO + precioHistorico.getIdRepuesto());
            return new ResponseEntity(precioHistorico, responseHeader, HttpStatus.CREATED);
        } catch (BusinessException e) {
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.getMessage());
            return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<PrecioHistoricoRepuesto>> findAllPrecios() {
        try {
            return new ResponseEntity(service.getPreciosRepuesto(), HttpStatus.OK);
        } catch (BusinessException e) {
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.getMessage());
            return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Any> updatePrecioHistoricoRepuesto(@RequestBody PrecioHistoricoRepuesto precioHistorico) {
        try {
            service.updatePrecioRepuesto(precioHistorico);
            return new ResponseEntity(precioHistorico, HttpStatus.OK);
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

    @DeleteMapping("/delete/{id}/{fechaInicio}")
    public ResponseEntity<Any> deletePrecioHistoricoRepuesto(@PathVariable long id, @PathVariable LocalDate fechaInicio) {
        try {
            service.deletePrecioRepuesto(id, fechaInicio);
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
