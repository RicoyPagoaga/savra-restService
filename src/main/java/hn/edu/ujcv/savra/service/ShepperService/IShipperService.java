package hn.edu.ujcv.savra.service.ShepperService;

import hn.edu.ujcv.savra.entity.Shipper;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface IShipperService {
    Shipper       saveShipper(Shipper shipper) throws BusinessException;
    List<Shipper> saveShippers(List<Shipper> shippers) throws BusinessException;
    List<Shipper> getShippers() throws BusinessException;
    Shipper       getShipperById(long id) throws BusinessException,NotFoundException;
    Shipper       getShipperByNombre(String nombre) throws BusinessException, NotFoundException;
    void          deleteShipper(long id) throws BusinessException,NotFoundException;
    Shipper       updateShipper(Shipper shipper) throws BusinessException,NotFoundException;
}
