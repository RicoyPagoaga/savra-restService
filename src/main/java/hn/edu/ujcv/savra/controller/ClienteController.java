package hn.edu.ujcv.savra.controller;

import hn.edu.ujcv.savra.entity.Cliente;
import hn.edu.ujcv.savra.service.ClienteService.ClienteService;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
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
        return null;
    }
    @GetMapping()
    public ResponseEntity<List<Cliente>> listarClientes(){
        return null;
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
        return null;
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Any> eliminarCliente(@PathVariable long id){
        return null;
    }
}
