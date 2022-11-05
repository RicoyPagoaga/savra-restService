package hn.edu.ujcv.savra.service.CategoriaClienteService;

import hn.edu.ujcv.savra.entity.CategoriaCliente;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.exceptions.SqlExceptions;
import hn.edu.ujcv.savra.repository.CategoriaClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaClienteService implements ICategoriaClienteService{
    @Autowired
    private CategoriaClienteRepository repository;

    @Override
    public CategoriaCliente saveCategoriaCliente(CategoriaCliente pCategoriaCliente) throws BusinessException, SqlExceptions {
        return repository.save(pCategoriaCliente);
    }

    @Override
    public List<CategoriaCliente> getCategoriaClientes() throws BusinessException {
        return repository.findAll();
    }

    @Override
    public CategoriaCliente getCategoriaClienteById(long id) throws BusinessException, NotFoundException {
        Optional<CategoriaCliente> opt = null;
        try{
            opt = repository.findById(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }if(!opt.isPresent()){
            throw new NotFoundException("No se encontró la categoria: "+ id);
        }
        return opt.get();
    }

    @Override
    public CategoriaCliente getCategoriaClienteByNombre(String nombre) throws BusinessException, NotFoundException {
        return null;
    }

    @Override
    public void deteleCategoriaCliente(long id) throws BusinessException, NotFoundException, SqlExceptions {
        Optional<CategoriaCliente> opt = null;
        try{
            opt = repository.findById(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("No se encontró la categoria: " + id);
        }else{
            try {
                repository.deleteById(id);
            }catch (Exception e1){
                throw new BusinessException(e1.getMessage());
            }
        }
    }

    @Override
    public CategoriaCliente updateCategoriaCliente(CategoriaCliente pCategoriaCliente) throws BusinessException,NotFoundException , SqlExceptions {
        Optional<CategoriaCliente> opt = null;
        try{
            opt = repository.findById(pCategoriaCliente.getIdCategoria());
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("No se encontró la categoria:" + pCategoriaCliente.getIdCategoria());
        }else {
            try {
                CategoriaCliente newCategoria = new CategoriaCliente();
                newCategoria.setIdCategoria(pCategoriaCliente.getIdCategoria());
                newCategoria.setNombre(pCategoriaCliente.getNombre());
                newCategoria.setDescripcion(pCategoriaCliente.getDescripcion());
                return repository.save(newCategoria);
            }catch (Exception e1){
                throw new BusinessException(e1.getMessage());
            }
        }
    }
}
