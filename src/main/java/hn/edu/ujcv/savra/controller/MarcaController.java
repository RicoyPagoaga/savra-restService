package hn.edu.ujcv.savra.controller;

import hn.edu.ujcv.savra.entity.Marca;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.service.Marca.MarcaService;
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
@RequestMapping("/api/v1/marcas")
public class MarcaController {

    @Autowired
    private MarcaService service;

    @PostMapping("/addMarca")
    public ResponseEntity<Object> addMarca(@RequestBody Marca marca) {
        try {
            service.saveMarca(marca);
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("location", Constants.URL_BASE_MARCAS + marca.getIdMarca());
            return new ResponseEntity(marca, responseHeader, HttpStatus.CREATED);
        } catch (BusinessException e) {
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.getMessage());
            return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addMarcas")
    public ResponseEntity<Any> addMarcas(@RequestBody List<Marca> marcas) {
        try {
            return new ResponseEntity(service.saveMarcas(marcas), HttpStatus.CREATED);
        } catch (BusinessException e) {
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.getMessage());
            return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Marca>> findAllMarcas() {
        try {
            return new ResponseEntity(service.getMarcas(), HttpStatus.OK);
        } catch (BusinessException e) {
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.getMessage());
            return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Marca> findMarcaById(@PathVariable long id) {
        try {
            return new ResponseEntity(service.getMarcaById(id), HttpStatus.OK);
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
    public ResponseEntity<Marca> findMarcaByNombre(@PathVariable String nombre) {
        try {
            return new ResponseEntity(service.getMarcaByNombre(nombre), HttpStatus.OK);
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
    public ResponseEntity<Any> updateMarca(@RequestBody Marca marca) {
        try {
            service.updateMarca(marca);
            return new ResponseEntity(marca, HttpStatus.OK);
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
    public ResponseEntity<Any> deleteMarca(@PathVariable long id) {
        try {
            service.deleteMarca(id);
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
