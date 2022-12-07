package hn.edu.ujcv.savra.controller.ImpuestosController;

import hn.edu.ujcv.savra.entity.Impuesto.ImpuestoHistorico.ImpuestoHistorico;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.service.ImpuestosService.ImpuestoHistoricoService.ImpuestoHistoricoService;
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
@RequestMapping("/api/v1/historico_impuestos")
public class ImpuestoHistoricoController {
    @Autowired
    private ImpuestoHistoricoService service;

    @PostMapping("/addImpuestoHistorico")
    public ResponseEntity<Object> addImpuestoHistorico(@RequestBody ImpuestoHistorico impuestoHistorico) {
        try {
            service.saveImpuestoHistorico(impuestoHistorico);
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("location", Constants.URL_BASE_IMPUESTO_HISTORICO + impuestoHistorico.getIdImpuesto());
            return new ResponseEntity(impuestoHistorico, responseHeader, HttpStatus.CREATED);
        } catch (BusinessException e) {
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.getMessage());
            return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<ImpuestoHistorico>> findAllImpuestos() {
        try {
            return new ResponseEntity(service.getImpuestosHistorico(), HttpStatus.OK);
        } catch (BusinessException e) {
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.getMessage());
            return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Any> updateImpuestoHistorico(@RequestBody ImpuestoHistorico impuestoHistorico) {
        try {
            service.updateImpuestoHistorico(impuestoHistorico);
            return new ResponseEntity(impuestoHistorico, HttpStatus.OK);
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

    /*@DeleteMapping("/delete/{id}/{fechaInicio}")
    public ResponseEntity<Any> deleteImpuestoHistorico(@PathVariable long id, @PathVariable LocalDate fechaInicio) {
        try {
            service.deleteImpuestoHistorico(id, fechaInicio);
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
    }*/
}
