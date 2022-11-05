package hn.edu.ujcv.savra.service.ClienteService;

import hn.edu.ujcv.savra.entity.Cliente;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface IClienteService {
    Cliente saveCliente(Cliente pCliente)throws BusinessException, SQLException;
    List<Cliente> getClientes()throws BusinessException;
    Cliente getClienteById(long id)throws BusinessException, NotFoundException;
    Cliente getClientebyNombre(long id) throws BusinessException,NotFoundException;
    Cliente deleteCliente(long id)throws BusinessException, NotFoundException;
    Cliente UpdateCliente(Cliente pCliente)throws BusinessException,SQLException;
}
