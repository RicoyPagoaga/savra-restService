package hn.edu.ujcv.savra.controller.ComprasController;

import hn.edu.ujcv.savra.entity.Compra.CompraDetalle;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.service.ComprasService.CompraDetalleService.CompraDetalleService;
import hn.edu.ujcv.savra.utils.Constants;
import hn.edu.ujcv.savra.utils.RestApiError;
import org.hibernate.annotations.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/compra_detalle")
public class CompraDetalleController {

    @Autowired
    private CompraDetalleService service;

    @PostMapping("/addCompraDetalle")
    public ResponseEntity<Object> addCompraDetalle(@RequestBody CompraDetalle compraDetalle) {
        try {
            service.saveCompraDetalle(compraDetalle);
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("location", Constants.URL_BASE_COMPRA_DETALLE + compraDetalle.getIdCompraDetalle());
            return new ResponseEntity(compraDetalle, responseHeader, HttpStatus.CREATED);
        } catch (BusinessException e) {
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.getMessage());
            return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addComprasDetalle")
    public ResponseEntity<Object> addComprasDetalle(@RequestBody List<CompraDetalle> compras) {
        try {
            return new ResponseEntity(service.saveComprasDetalle(compras), HttpStatus.CREATED);
        } catch (BusinessException e) {
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.getMessage());
            return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateComprasDetalle")
    public ResponseEntity<Any> updateComprasDetalles(@RequestBody List<CompraDetalle> compras) {
        try {
            service.updateComprasDetalles(compras);
            return new ResponseEntity(compras, HttpStatus.OK);
        } catch (BusinessException e) {
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.getMessage());
            return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<CompraDetalle>> findAllComprasDetalle() {
        try {
            return new ResponseEntity(service.getComprasDetalle(), HttpStatus.OK);
        } catch (BusinessException e) {
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.getMessage());
            return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CompraDetalle> findCompraDetalleById(@PathVariable long id) {
        try {
            return new ResponseEntity(service.getCompraDetalleById(id), HttpStatus.OK);
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
    public ResponseEntity<Any> updateCompraDetalle(@RequestBody CompraDetalle compraDetalle) {
        try {
            service.updateCompraDetalle(compraDetalle);
            return new ResponseEntity(compraDetalle, HttpStatus.OK);
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
    public ResponseEntity<Any> deleteCompraDetalle(@PathVariable long id) {
        try {
            service.deleteCompraDetalle(id);
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
