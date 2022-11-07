package hn.edu.ujcv.savra.service.ClienteService;

import hn.edu.ujcv.savra.entity.Cliente;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.exceptions.SqlExceptions;

import java.sql.SQLException;
import java.util.List;

public interface IClienteService {
    Cliente saveCliente(Cliente pCliente)throws BusinessException, SQLException;
    List<Cliente> getClientes()throws BusinessException;
    Cliente getClientebyNombre(long id) throws BusinessException,NotFoundException;
    void deleteCliente(long id)throws BusinessException, NotFoundException;
    Cliente UpdateCliente(Cliente pCliente)throws BusinessException,NotFoundException, SqlExceptions;
}