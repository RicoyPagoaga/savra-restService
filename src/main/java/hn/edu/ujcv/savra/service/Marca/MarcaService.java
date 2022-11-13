package hn.edu.ujcv.savra.service.Marca;

import hn.edu.ujcv.savra.entity.Marca;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarcaService implements IMarcaService {

    @Autowired
    private MarcaRepository repository;

    @Override
    public Marca saveMarca(Marca marca) throws BusinessException {
        try {
            validarMarca(marca);
            return repository.save(marca);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Marca> saveMarcas(List<Marca> marcas) throws BusinessException {
        try {
            for (Marca marca : marcas) {
                validarMarca(marca);
            }
            return repository.saveAll(marcas);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Marca> getMarcas() throws BusinessException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Marca getMarcaById(long id) throws BusinessException, NotFoundException {
        Optional<Marca> opt=null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró la marca " + id);
        }
        return opt.get();
    }

    @Override
    public Marca getMarcaByNombre(String nombre) throws BusinessException, NotFoundException {
        Optional<Marca> opt=null;
        try {
            opt = repository.findFirstByNombre(nombre);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró la marca " + nombre);
        }
        return opt.get();
    }

    @Override
    public void deleteMarca(long id) throws BusinessException, NotFoundException {
        Optional<Marca> opt=null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró la marca " + id);
        } else {
            try {
                repository.deleteById(id);
            } catch (Exception e1) {
                throw new BusinessException(e1.getMessage());
            }
        }
    }

    @Override
    public Marca updateMarca(Marca marca) throws BusinessException, NotFoundException {
        Optional<Marca> opt=null;
        try {
            if (String.valueOf(marca.getIdMarca()).isEmpty()) {
                throw new BusinessException("El id de la Marca no debe estar vacio");
            }
            if (marca.getIdMarca() < 0) {
                throw new BusinessException("Id de Marca invalido");
            }
            opt = repository.findById(marca.getIdMarca());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró la marca " + marca.getIdMarca());
        } else{
            try {
                validarMarca(marca);
                Marca marcaExistente = new Marca(
                        marca.getIdMarca(), marca.getNombre()
                );
                return repository.save(marcaExistente);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void validarMarca(Marca marca) throws BusinessException {
        if (marca.getNombre().isEmpty()) {
            throw new BusinessException("El nombre de la marca no debe estar vacío");
        }
        if (marca.getNombre().trim().length() < 3) {
            throw new BusinessException("Ingrese más de 3 caracteres en el nombre de la marca");
        }
        if (marca.getNombre().trim().length() > 50) {
            throw new BusinessException("El nombre de la marca no debe exceder los cincuenta caracteres");
        }
        List<Marca> marcas = getMarcas();
        for (Marca item : marcas) {
            if ((item.getNombre().equals(marca.getNombre())) && (item.getIdMarca() != marca.getIdMarca())) {
                throw new BusinessException("El nombre de la marca ya está en uso");
            }
        }
    }
}