package hn.edu.ujcv.savra.service.TipoEntregaService;

import hn.edu.ujcv.savra.entity.TipoEntrega;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.TipoEntregaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class TipoEntregaService implements ITipoEntregaService {
    @Autowired
    private TipoEntregaRepository repository;

    @Override
    public TipoEntrega saveTipoEntrega(TipoEntrega tipoEntrega) throws BusinessException {
        try {
            validarTipoEntrega(tipoEntrega);
            return repository.save(tipoEntrega);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<TipoEntrega> saveTiposEntrega(List<TipoEntrega> tiposEntrega) throws BusinessException {
        try {
            for (TipoEntrega item: tiposEntrega) {
                validarTipoEntrega(item);
            }
            return repository.saveAll(tiposEntrega);
        } catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<TipoEntrega> getTiposEntrega() throws BusinessException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public TipoEntrega getTipoEntregaById(long id) throws BusinessException, NotFoundException {
        Optional<TipoEntrega> opt=null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el tipo de entrega: " + id);
        }
        return opt.get();
    }

    @Override
    public TipoEntrega getTipoEntregaByNombre(String nombre) throws BusinessException, NotFoundException {
        Optional<TipoEntrega> opt=null;
        try {
            opt = repository.findFirstByNombre(nombre);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el tipo de entrega: " + nombre);
        }
        return opt.get();
    }

    @Override
    public void deleteTipoEntrega(long id) throws BusinessException, NotFoundException {
        Optional<TipoEntrega> opt = null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el tipo de entrega: " + id);
        } else {
            try {
                repository.deleteById(id);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }

    @Override
    public TipoEntrega updateTipoEntrega(TipoEntrega tipoEntrega) throws BusinessException, NotFoundException {
        Optional<TipoEntrega> opt = null;
        try {
            if (String.valueOf(tipoEntrega.getIdTipoEntrega()).isEmpty()) {
                throw new BusinessException("El id de tipo de entrega no debe estar vacío");
            }
            if (tipoEntrega.getIdTipoEntrega() < 0) {
                throw new BusinessException("Id de tipo de entrega inválido");
            }
            opt = repository.findById(tipoEntrega.getIdTipoEntrega());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el tipo de entrega: " + tipoEntrega.getIdTipoEntrega());
        } else {
            try {
                validarTipoEntrega(tipoEntrega);
                TipoEntrega tipoEntregaExiste = new TipoEntrega(
                        tipoEntrega.getIdTipoEntrega(), tipoEntrega.getNombre(),
                        tipoEntrega.getDescripcion()
                );
                return repository.save(tipoEntregaExiste);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void validarTipoEntrega(TipoEntrega tipoEntrega) throws BusinessException {
        //nombre
        if (tipoEntrega.getNombre().trim().isEmpty()) {
            throw new BusinessException("El nombre del tipo de entrega no debe estar vacío");
        }
        if (tipoEntrega.getNombre().trim().length() < 3) {
            throw new BusinessException("Ingrese más de tres caracteres en el nombre de tipo de entrega");
        }
        if (tipoEntrega.getNombre().trim().length() > 50) {
            throw new BusinessException("El nombre del tipo de entrega no debe exceder los cincuenta caracteres");
        }
        Pattern patDoc = Pattern.compile("[0-9]+");
        boolean matDoc = patDoc.matcher(tipoEntrega.getNombre().trim()).find();
        if(matDoc){
            throw new BusinessException("Nombre de tipo de entrega no debe contener números ఠ_ఠ");
        }
        Pattern dobleEspacio = Pattern.compile("\\s{2,}");
        if (dobleEspacio.matcher(tipoEntrega.getNombre()).find()) {
            throw new BusinessException("Nombre de tipo de entrega no debe contener espacios dobles ఠ_ఠ");
        }
        List<TipoEntrega> tipos = getTiposEntrega();
        for (TipoEntrega item : tipos) {
            if ((item.getNombre().equals(tipoEntrega.getNombre().trim())) &&
                    (item.getIdTipoEntrega() != tipoEntrega.getIdTipoEntrega())) {
                throw new BusinessException("El nombre de tipo de entrega ya está en uso");
            }
        }
        //descripcion
        if (tipoEntrega.getDescripcion().trim().isEmpty()) {
            throw new BusinessException("La descripción es requerida");
        }
        if (tipoEntrega.getDescripcion().trim().length() < 5) {
            throw new BusinessException("La descripción debe contener mínimo cinco caracteres");
        }
        if (tipoEntrega.getDescripcion().trim().length() > 80) {
            throw new BusinessException("La descripción no debe exceder los ochenta caracteres");
        }
        if (dobleEspacio.matcher(tipoEntrega.getDescripcion()).find()) {
            throw new BusinessException("Descripción de tipo de entrega no debe contener espacios dobles ఠ_ఠ");
        }
    }
}