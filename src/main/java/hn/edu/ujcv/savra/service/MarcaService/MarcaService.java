package hn.edu.ujcv.savra.service.MarcaService;

import hn.edu.ujcv.savra.entity.Marca;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MarcaService implements IMarcaService {

    @Autowired
    private MarcaRepository repository;

    @Override
    public Marca saveMarca(Marca marca) throws BusinessException {
        try {
            marca.setNombre(marca.getNombre().trim());
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
                        marca.getIdMarca(), marca.getNombre().trim()
                );
                return repository.save(marcaExistente);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void validarMarca(Marca marca) throws BusinessException {
        if (marca.getNombre().trim().isEmpty()) {
            throw new BusinessException("El nombre de la marca no debe estar vacío");
        }
        if (marca.getNombre().trim().length() < 3) {
            throw new BusinessException("Ingrese más de 3 caracteres en el nombre de la marca");
        }
        if (marca.getNombre().trim().length() > 50) {
            throw new BusinessException("El nombre de la marca no debe exceder los cincuenta caracteres");
        }
        Pattern dobleEspacio = Pattern.compile("\\s{2,}");
        if (dobleEspacio.matcher(marca.getNombre().trim()).find()) {
            throw new BusinessException("Nombre de marca no debe contener espacios dobles ఠ_ఠ");
        }
        Pattern pat = Pattern.compile("[\\d]*");
        Matcher mat_ = pat.matcher(marca.getNombre().trim());
        if(mat_.matches()) {
            throw new BusinessException("El nombre de marca debe contener letras ఠ_ఠ");
        }
        String[] nombre = marca.getNombre().trim().split(" ");
        for (String item: nombre) {
            if(item.matches("(.)\\1{2,}")) {
                throw new BusinessException("El nombre no debe tener tantas letras repetidas ఠ_ఠ");
            }
            if(item.length()==1) {
                throw new BusinessException("Nombre de marca inválido");
            }
        }
        List<Marca> marcas = getMarcas();
        for (Marca item : marcas) {
            if ((item.getNombre().equals(marca.getNombre().trim())) && (item.getIdMarca() != marca.getIdMarca())) {
                throw new BusinessException("El nombre de la marca ya está en uso");
            }
        }
    }
}
