package hn.edu.ujcv.savra.controller;

import hn.edu.ujcv.savra.entity.Pais;
import hn.edu.ujcv.savra.service.PaisService.PaisService;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/paises")
public class PaisController {
    @Autowired
    private PaisService service;

    @PostMapping("/addPais")
    public ResponseEntity<Object> agregarPais(@RequestBody Pais pPais){
        return null;
    }
    @GetMapping()
    public ResponseEntity<List<Pais>> listarPaises(){
        return null;
    }
    @GetMapping("/iso/{iso}")
    public ResponseEntity<Pais> buscarPaisPorIso(@PathVariable String iso){
        return null;
    }
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Pais> buscarPaisPorNombre(@PathVariable String nombre){
        return null;
    }
    @PutMapping("")
    public ResponseEntity<Any> actualizarPais(@RequestBody Pais pPais){
        return null;
    }
    @DeleteMapping("/delete/{iso}")
    public ResponseEntity<Any> eliminarPais(@PathVariable String iso){
        return null;
    }
}
