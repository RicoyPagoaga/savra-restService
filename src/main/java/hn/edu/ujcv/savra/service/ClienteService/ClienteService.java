package hn.edu.ujcv.savra.service.ClienteService;

import hn.edu.ujcv.savra.entity.Cliente;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ClienteService implements IClienteService{
    @Autowired
    private ClienteRepository repository;
    @Override
    public Cliente saveCliente(Cliente pCliente) throws BusinessException, SQLException {
        return null;
    }

    @Override
    public List<Cliente> getClientes() throws BusinessException {
        return null;
    }

    @Override
    public Cliente getClienteById(long id) throws BusinessException, NotFoundException {
        return null;
    }

    @Override
    public Cliente getClientebyNombre(long id) throws BusinessException, NotFoundException {
        return null;
    }

    @Override
    public Cliente deleteCliente(long id) throws BusinessException, NotFoundException {
        return null;
    }

    @Override
    public Cliente UpdateCliente(Cliente pCliente) throws BusinessException, SQLException {
        return null;
    }
}
