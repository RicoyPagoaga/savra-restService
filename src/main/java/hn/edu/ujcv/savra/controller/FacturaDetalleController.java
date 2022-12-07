package hn.edu.ujcv.savra.controller;

import hn.edu.ujcv.savra.entity.FacturaDetalle;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.service.FacturaDetalleService.FacturaDetalleService;
import hn.edu.ujcv.savra.utils.Constants;
import hn.edu.ujcv.savra.utils.RestApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/facturaDetalles")
public class FacturaDetalleController {
    @Autowired
    FacturaDetalleService service;
    @PostMapping("/addFacturaDetalle")
    public ResponseEntity<Object> agregarFacturaDetalle(@RequestBody FacturaDetalle pfacturaDetalle){
        try{
            service.guardarFacturaDetalle(pfacturaDetalle);
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("location", Constants.URL_BASE_CATEGORIA_CLIENTES + pfacturaDetalle.getIdFacturaDetalle());
            return new ResponseEntity(pfacturaDetalle,responseHeader, HttpStatus.CREATED);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Información enviada no valida!",e.getMessage());
            return new ResponseEntity<>(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("")
    public ResponseEntity<List<FacturaDetalle>> listarFacturaDetalles(){
        try{
            return new ResponseEntity<>(service.obtenerFacturaDetalles(),HttpStatus.OK);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Lista no válida.",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/addFacturaDetalles")
    public ResponseEntity<Object> agregarFacturaDetalles(@RequestBody List<FacturaDetalle> pfacturaDetalles){
        try{
            return new ResponseEntity(service.guardarFacturaDetalles(pfacturaDetalles), HttpStatus.CREATED);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Información enviada no valida!",e.getMessage());
            return new ResponseEntity<>(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/idFactura/{idFactura}")
    public ResponseEntity<List<FacturaDetalle>> obtenerDetalleFacturaByNoFactura(@PathVariable long idFactura){
        try{
            return new ResponseEntity(service.obtenerFacturaDetallesByIdFactura(idFactura),HttpStatus.OK);
        }catch (BusinessException e) {
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.getMessage());
            return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
