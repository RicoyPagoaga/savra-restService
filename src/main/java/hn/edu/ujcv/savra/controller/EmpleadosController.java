package hn.edu.ujcv.savra.controller;
import hn.edu.ujcv.savra.entity.Empleado;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.service.EmpleadoService.EmpleadosService;
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
@RequestMapping("/api/v1/empleados")
public class EmpleadosController {

    @Autowired
    private EmpleadosService service;

    @PostMapping("/addEmpleado")
    public ResponseEntity<Object> addEmpleado (@RequestBody Empleado empleado){
        try {
            service.saveEmpleados(empleado);
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("location", Constants.URL_BASE_EMPLEADOS + empleado.getIdEmpleado());
            return new ResponseEntity(empleado,responseHeader, HttpStatus.CREATED);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "La nformacion enviada no es valida ;V ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addEmpleados")
    public ResponseEntity<Any> addEmpleados (@RequestBody List<Empleado> empleado){
        try {
            return new ResponseEntity(service.saveEmpleados(empleado),HttpStatus.CREATED);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "La nformacion enviada no es valida XD ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Empleado>> findAllEmpleados(){
        try {
            return new ResponseEntity(service.getEmpleados(),HttpStatus.OK);

        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "La nformacion enviada no es valida XD ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Empleado> findEmpleadoById (@PathVariable Long id){
        try {
            return new ResponseEntity(service.getEmpleadosById(id),HttpStatus.OK);

        }catch (BusinessException e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Empleado> findEmpleadoByNombre (@PathVariable String nombre){
        try {
            return new ResponseEntity(service.getEmpleadoByNombre(nombre),HttpStatus.OK);

        }catch (BusinessException e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("")
    public ResponseEntity<Any> updateEmpleado (@RequestBody Empleado empleado){
        try {
            service.updateEmpleados(empleado);
            return new ResponseEntity(empleado,HttpStatus.OK);

        }catch (BusinessException e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Any> deleteEmpleado (@PathVariable Long id){
        try {
            service.deleteEmpleados(id);
            return new ResponseEntity(HttpStatus.OK);

        }catch (BusinessException e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
