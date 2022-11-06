package hn.edu.ujcv.savra.service.Proveedor;

import hn.edu.ujcv.savra.entity.Proveedor;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface IProveedorService {
    Proveedor saveProveedor(Proveedor proveedor) throws BusinessException;
    List<Proveedor> saveProveedores(List<Proveedor> proveedores) throws BusinessException;
    List<Proveedor> getProveedores() throws BusinessException;
    Proveedor       getProveedorById(long id) throws BusinessException, NotFoundException;
    Proveedor       getProveedorByNombre(String nombre) throws BusinessException, NotFoundException;
    void            deleteProveedor(long id) throws BusinessException, NotFoundException;
    Proveedor       updateProveedor(Proveedor proveedor) throws BusinessException, NotFoundException;
}
