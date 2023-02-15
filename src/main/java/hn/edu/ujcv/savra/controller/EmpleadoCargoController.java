package hn.edu.ujcv.savra.controller;

import hn.edu.ujcv.savra.entity.EmpleadoCargo.EmpleadoCargo;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.service.EmpleadoCargo.EmpleadoCargoService;
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
@RequestMapping("/api/v1/empleado_cargos")
public class EmpleadoCargoController {

    @Autowired
    private EmpleadoCargoService service;

    @PostMapping("/addEmpleadoCargo")
    public ResponseEntity<Object> addEmpleadoCargo (@RequestBody EmpleadoCargo empleadoCargo){
        try {
            service.saveEmpleadoCargo(empleadoCargo);
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("location", Constants.URL_BASE_EMPLEADO_CARGO + empleadoCargo.getIdEmpleado());
            return new ResponseEntity(empleadoCargo,responseHeader, HttpStatus.CREATED);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "La nformacion enviada no es valida ;V ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/addEmpleadoCargos")
    public ResponseEntity<Any> addEmpleadoCargos (@RequestBody List<EmpleadoCargo> empleadoCargos){
        try {
            return new ResponseEntity(service.saveEmpleadoCargos(empleadoCargos),HttpStatus.CREATED);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "La nformacion enviada no es valida XD ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity <List<EmpleadoCargo>> findAllEmpleadoCargos(){
        try {
            return new ResponseEntity(service.getEmpleadoCargos(),HttpStatus.OK);

        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "La nformacion enviada no es valida XD ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/id/{id}/{fechaInicio}")
    public ResponseEntity <EmpleadoCargo> findEmpleadoCargoById (@PathVariable Long id, @PathVariable String fechaInicio){
        try {
            return new ResponseEntity(service.getEmpleadoCargoById(id,fechaInicio),HttpStatus.OK);

        }catch (BusinessException e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("")
    public ResponseEntity<Any> updateEmpleadoCargo (@RequestBody EmpleadoCargo empleadoCargo){
        try {
            service.updateEmpleadoCargo(empleadoCargo);
            return new ResponseEntity(empleadoCargo,HttpStatus.OK);

        }catch (BusinessException e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Empleado Cargo no válido",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);


        }catch (NotFoundException e) {
            RestApiError apiError = new RestApiError(HttpStatus.NOT_FOUND,
                    "No se encontró el Empleado cargo", e.getMessage());
            return new ResponseEntity(apiError, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete/{id}/{fechaInicio}")
    public  ResponseEntity<Any> deleteEmpleadoCargo (@PathVariable long id, @PathVariable String fechaInicio){
        try {
            service.deleteEmpleadoCargo(id,fechaInicio);
            return new ResponseEntity(HttpStatus.OK);

        }catch (BusinessException e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


}
