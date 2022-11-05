package hn.edu.ujcv.savra.controller;

import hn.edu.ujcv.savra.entity.CategoriaCliente;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.exceptions.SqlExceptions;
import hn.edu.ujcv.savra.service.CategoriaClienteService.CategoriaClienteService;
import hn.edu.ujcv.savra.utils.Constants;
import hn.edu.ujcv.savra.utils.RestApiError;
import org.aspectj.weaver.ast.Not;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categoriaClientes")
public class CategoriaClienteController {
    @Autowired
    private CategoriaClienteService service;

    @PostMapping("/addCategoriaCliente")
    public ResponseEntity<Object> agregarCategoriaCliente(@RequestBody CategoriaCliente pCategoriaCliente){
        try{
            service.saveCategoriaCliente(pCategoriaCliente);
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("location", Constants.URL_BASE_CATEGORIA_CLIENTES + pCategoriaCliente.getIdCategoria());
            return new ResponseEntity(pCategoriaCliente,responseHeader, HttpStatus.CREATED);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Información enviada no valida!",e.getMessage());
            return new ResponseEntity<>(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("")
    public ResponseEntity<List<CategoriaCliente>> listarCategoriaClientes(){
        try{
            return new ResponseEntity(service.getCategoriaClientes(),HttpStatus.OK);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Lista no válida.",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<CategoriaCliente> buscarCategoriaClientePorId(@PathVariable long id){
        try{
            return new ResponseEntity(service.getCategoriaClienteById(id),HttpStatus.OK);
        }catch (BusinessException e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Categoria no válida",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (NotFoundException e){
            RestApiError apiError = new RestApiError(HttpStatus.NOT_FOUND,
                    "No se encontró la categoria",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<CategoriaCliente> buscarCategoriaClientePorNombre(@PathVariable String nombre){
        return null;
    }
    @PutMapping("")
    public ResponseEntity<Any> actualizarCategoriaCliente(@RequestBody CategoriaCliente pCategoriaCliente){
        try{
            service.updateCategoriaCliente(pCategoriaCliente);
            return new ResponseEntity(pCategoriaCliente,HttpStatus.OK);
        }catch (SqlExceptions e){
            RestApiError apiError = new RestApiError(HttpStatus.CONFLICT,
                    "Error SQL!",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.CONFLICT);
        } catch (BusinessException e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Categoria no válida!",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (NotFoundException e){
            RestApiError apiError = new RestApiError(HttpStatus.NOT_FOUND,
                    "No se encontró la categoria!",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Any> eliminarCategoriaCliente(@PathVariable long id){
        try{
            service.deteleCategoriaCliente(id);
            return new ResponseEntity(HttpStatus.OK);
        }catch(SqlExceptions e){
            RestApiError apiError = new RestApiError(HttpStatus.CONFLICT,
                    "Conflicto SQL", e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.CONFLICT);
        }catch (BusinessException e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Categoria no Válida",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (NotFoundException e){
            RestApiError apiError = new RestApiError(HttpStatus.NOT_FOUND,
                    "No se encontró la categoria",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.NOT_FOUND);
        }
    }
}
