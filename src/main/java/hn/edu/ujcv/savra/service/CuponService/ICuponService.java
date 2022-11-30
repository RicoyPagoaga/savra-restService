package hn.edu.ujcv.savra.service.CuponService;

import hn.edu.ujcv.savra.entity.Cupon;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
public interface ICuponService {
    Cupon saveCupon(Cupon pCupon)throws BusinessException;
    Cupon actualizarCupon(Cupon pCupon)throws BusinessException, NotFoundException;
    List<Cupon> obtenerCupones()throws BusinessException;
    void eliminarCupon(long id)throws BusinessException,NotFoundException;
    Cupon getCuponById(long ig)throws BusinessException,NotFoundException;
    Cupon getCuponByCodigo(String codigo)throws BusinessException,NotFoundException;
    void activarDesactivarcupon(int estado , long id)throws BusinessException,NotFoundException;
}
