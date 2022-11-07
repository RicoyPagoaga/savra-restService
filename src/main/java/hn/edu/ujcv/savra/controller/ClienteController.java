package hn.edu.ujcv.savra.controller;

import hn.edu.ujcv.savra.entity.Cliente;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.exceptions.SqlExceptions;
import hn.edu.ujcv.savra.service.ClienteService.ClienteService;
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
@RequestMapping("/api/v1/clientes")
public class ClienteController {
    @Autowired
    private ClienteService service;

    @PostMapping("/addCliente")
    public ResponseEntity<Object> agregarCliente(@RequestBody Cliente pCliente){
        try{
            service.saveCliente(pCliente);
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("location", Constants.URL_BASE_CLIENTES + pCliente.getIdCliente());
            return new ResponseEntity(pCliente,responseHeader, HttpStatus.CREATED);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Información enviada no valida!",e.getMessage());
            return new ResponseEntity<>(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping()
    public ResponseEntity<List<Cliente>> listarClientes(){
        try{
            return new ResponseEntity(service.getClientes(),HttpStatus.OK);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Lista no válida.",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable long id){
        return null;
    }
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Cliente> buscarClientePorNombre(@PathVariable String nombre){
        return null;
    }
    @PutMapping("")
    public ResponseEntity<Any> actualizarCliente(@RequestBody Cliente pCliente){
        try{
            service.UpdateCliente(pCliente);
            return new ResponseEntity(pCliente,HttpStatus.OK);
        }catch (SqlExceptions e){
            RestApiError apiError = new RestApiError(HttpStatus.CONFLICT,
                    "Error SQL!",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.CONFLICT);
        } catch (BusinessException e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Cliente no válido!",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (NotFoundException e){
            RestApiError apiError = new RestApiError(HttpStatus.NOT_FOUND,
                    "No se encontró el Cliente!",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Any> eliminarCliente(@PathVariable long id){
        try{
            service.deleteCliente(id);
            return new ResponseEntity(HttpStatus.OK);
        }catch (BusinessException e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Cliente no Válido",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (NotFoundException e){
            RestApiError apiError = new RestApiError(HttpStatus.NOT_FOUND,
                    "No se encontró el cliente",e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.NOT_FOUND);
        }
    }
}