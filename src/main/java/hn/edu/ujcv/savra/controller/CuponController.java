package hn.edu.ujcv.savra.controller;

import hn.edu.ujcv.savra.entity.Cupon;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.service.CuponService.CuponService;
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
@RequestMapping("/api/v1/cupones")
public class    CuponController {
    @Autowired
    private CuponService cuponService;
    @PostMapping("/addCupon")
    public ResponseEntity<Object> agregarCupon(@RequestBody Cupon pCupon){
        try{
            cuponService.saveCupon(pCupon);
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("location", Constants.URL_BASE_CATEGORIA_CLIENTES + pCupon.getIdCupon());
            return new ResponseEntity(pCupon,responseHeader, HttpStatus.CREATED);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Información enviada no valida!",e.getMessage());
            return new ResponseEntity<>(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("")
    public ResponseEntity<List<Cupon>> listarCupons(){
        try{
            return new ResponseEntity(cuponService.obtenerCupones(),HttpStatus.OK);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Lista no válida.",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Cupon> buscarCuponPorId(@PathVariable long id){
        try{
            return new ResponseEntity(cuponService.getCuponById(id),HttpStatus.OK);
        }catch (BusinessException e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Cupón no válido",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (NotFoundException e){
            RestApiError apiError = new RestApiError(HttpStatus.NOT_FOUND,
                    "No se encontró el cupón",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Cupon> buscarCuponPorCodigo(@PathVariable String nombre){
        return null;
    }
    @PutMapping("")
    public ResponseEntity<Any> actualizarCupon(@RequestBody Cupon pCupon){
        try{
            cuponService.actualizarCupon(pCupon);
            return new ResponseEntity(pCupon,HttpStatus.OK);
        }catch (BusinessException e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Cupón no válido!",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (NotFoundException e){
            RestApiError apiError = new RestApiError(HttpStatus.NOT_FOUND,
                    "No se encontró el cupon!",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/activarDesactivar/{estado}/{id}")
    public ResponseEntity<Any> activarDesactivarCupon(@PathVariable int estado,@PathVariable long id){
        try{
            cuponService.activarDesactivarcupon(estado,id);
            return new ResponseEntity(HttpStatus.OK);
        }catch (BusinessException e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Cupón no válido!",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (NotFoundException e){
            RestApiError apiError = new RestApiError(HttpStatus.NOT_FOUND,
                    "No se encontró el cupon!",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Any> eliminarCupon(@PathVariable long id){
        try{
            cuponService.eliminarCupon(id);
            return new ResponseEntity(HttpStatus.OK);
        }catch (BusinessException e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Cupón no Válido",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (NotFoundException e){
            RestApiError apiError = new RestApiError(HttpStatus.NOT_FOUND,
                    "No se encontró el cupón",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.NOT_FOUND);
        }
    }
}
