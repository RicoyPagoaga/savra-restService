package hn.edu.ujcv.savra.controller;

import hn.edu.ujcv.savra.entity.Shipper;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.service.ShepperService.ShipperService;
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
@RequestMapping("/api/v1/shippers")
public class ShipperController {
    @Autowired
    private ShipperService service;

    @PostMapping("/addShipper")
    public ResponseEntity<Object> addShipper (@RequestBody Shipper shipper){
        try {
            service.saveShipper(shipper);
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("location", Constants.URL_BASE_SHIPPER + shipper.getIdShipper());
            return new ResponseEntity(shipper,responseHeader, HttpStatus.CREATED);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "La nformacion enviada no es valida ;V ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/addShippers")
    public ResponseEntity<Any> addArqueos (@RequestBody List<Shipper> shippers){
        try {
            return new ResponseEntity(service.saveShippers(shippers),HttpStatus.CREATED);
        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "La nformacion enviada no es valida XD ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity <List<Shipper>> findAllShippers(){
        try {
            return new ResponseEntity(service.getShippers(),HttpStatus.OK);

        }catch (Exception e){
            RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "La nformacion enviada no es valida XD ",
                    e.getMessage());
            return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity <Shipper> findShipperById (@PathVariable Long id) {
        try {
            return new ResponseEntity(service.getShipperById(id), HttpStatus.OK);

        } catch (BusinessException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("")
    public ResponseEntity<Any> updateShipper (@RequestBody Shipper shipper){
        try {
            service.updateShipper(shipper);
            return new ResponseEntity(shipper,HttpStatus.OK);

        }catch (BusinessException e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<Any> deleteShipper (@PathVariable Long id){
        try {
            service.deleteShipper(id);
            return new ResponseEntity(HttpStatus.OK);

        }catch (BusinessException e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


}
