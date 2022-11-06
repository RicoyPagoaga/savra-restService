package hn.edu.ujcv.savra.controller;


import hn.edu.ujcv.savra.entity.TipoDocumento;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.service.TipoDocumentoService.TipoDocumentoService;
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
@RequestMapping("/api/v1/tipoDocumento")
public class TipoDocumentoController {
    @Autowired
    private TipoDocumentoService service;


    @PostMapping("/addTipoDocumento")
    public ResponseEntity<Object> addTipoDocumento (@RequestBody TipoDocumento tipoDocumento){
        try {
            service.saveTipoDocumento(tipoDocumento);
            HttpHeaders responseHeader= new HttpHeaders();
            responseHeader.set("location", Constants.URL_BASE_TIPO_DOCUMENTO + tipoDocumento.getIdTipoDocumento());
            return  new ResponseEntity(tipoDocumento,responseHeader,HttpStatus.CREATED);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "La nformacion enviada no es valida ;V ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addTipoDocumentos")
    public ResponseEntity<Any> addTipoDocumentos (@RequestBody List<TipoDocumento> tipoDocumentos){
        try {
            return new ResponseEntity(service.saveTipoDocumento(tipoDocumentos),HttpStatus.CREATED);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "La nformacion enviada no es valida V; ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<TipoDocumento>> findAllTipoDocumentos(){
        try {
            return new ResponseEntity(service.getTipoDocumento(),HttpStatus.OK);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "La nformacion enviada no es valida V; ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TipoDocumento> findTipoDocumentoById (@PathVariable Long id){
        try {
            return new ResponseEntity(service.getTipoDocumentoById(id),HttpStatus.OK);

        }catch (BusinessException e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<TipoDocumento> findTipoDocumendoByNombre (@PathVariable String nombre){
        try {
            return new ResponseEntity(service.getTipoDocumentoByNombre(nombre),HttpStatus.OK);

        }catch (BusinessException e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("")
    public ResponseEntity<Any> updateTipoDocumento (@RequestBody TipoDocumento tipoDocumento){
        try {
            service.updateTipoDocumento(tipoDocumento);
            return new ResponseEntity(tipoDocumento,HttpStatus.OK);

        }catch (BusinessException e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Any> deleteTipoDocumento (@PathVariable Long id){
        try {
            service.deleteTipoDocumento(id);
            return new ResponseEntity(HttpStatus.OK);

        }catch (BusinessException e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
