package hn.edu.ujcv.savra.service.CategoriaClienteService;

import hn.edu.ujcv.savra.entity.CategoriaCliente;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.exceptions.SqlExceptions;

import java.util.List;

public interface ICategoriaClienteService {
    CategoriaCliente saveCategoriaCliente(CategoriaCliente pCategoriaCliente)throws BusinessException, SqlExceptions;
    List<CategoriaCliente> getCategoriaClientes()throws BusinessException;
    CategoriaCliente getCategoriaClienteById(long id)throws BusinessException, NotFoundException;
    CategoriaCliente getCategoriaClienteByNombre(String nombre)throws  BusinessException,NotFoundException;
    void deteleCategoriaCliente(long id)throws BusinessException,NotFoundException, SqlExceptions;
    CategoriaCliente updateCategoriaCliente(CategoriaCliente pCategoriaCliente)throws BusinessException,NotFoundException,SqlExceptions;
}
