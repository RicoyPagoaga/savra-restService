package hn.edu.ujcv.savra.service.PaisService;

import hn.edu.ujcv.savra.entity.Pais;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class PaisService implements IPaisService{
    @Autowired
    private PaisRepository repository;
    @Override
    public Pais savePais(Pais pPais) throws BusinessException, SQLException {
        return null;
    }

    @Override
    public List<Pais> getPaises() throws BusinessException {
        return null;
    }

    @Override
    public Pais getPaisByIso(String iso) throws BusinessException, NotFoundException {
        return null;
    }

    @Override
    public Pais getPaisByNombre(String nombre) throws BusinessException, NotFoundException {
        return null;
    }

    @Override
    public void detelePais(String iso) throws BusinessException, NotFoundException {

    }

    @Override
    public Pais updatePais(Pais pPais) throws BusinessException, SQLException {
        return null;
    }
}
