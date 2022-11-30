package hn.edu.ujcv.savra.service.PrecioHistoricoRepuestoService;

import hn.edu.ujcv.savra.entity.PrecioHistoricoRepuesto.PrecioHistoricoRepuesto;
import hn.edu.ujcv.savra.entity.PrecioHistoricoRepuesto.PrecioHistoricoRepuestoPK;
import hn.edu.ujcv.savra.entity.Repuesto;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.PrecioHistoricoRepuestoRepository;
import hn.edu.ujcv.savra.repository.RepuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PrecioHistoricoRepuestoService implements IPrecioHistoricoRepuestoService {

    @Autowired
    private PrecioHistoricoRepuestoRepository repository;

    @Override
    public PrecioHistoricoRepuesto savePrecioRepuesto(PrecioHistoricoRepuesto precioRepuesto) throws BusinessException {
        try {
            validarPrecioHistorico(precioRepuesto);
            //setear precio
            setearPrecioHistorico(precioRepuesto);
            return repository.save(precioRepuesto);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<PrecioHistoricoRepuesto> savePreciosRepuesto(List<PrecioHistoricoRepuesto> preciosRepuesto) throws BusinessException {
        try {
            for (PrecioHistoricoRepuesto item : preciosRepuesto) {
                validarPrecioHistorico(item);
            }
            return repository.saveAll(preciosRepuesto);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<PrecioHistoricoRepuesto> getPreciosRepuesto() throws BusinessException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public PrecioHistoricoRepuesto getPrecioRepuestoById(long id, LocalDate fechaInicio) throws BusinessException, NotFoundException {
        Optional<PrecioHistoricoRepuesto> opt=null;
        PrecioHistoricoRepuestoPK pk = new PrecioHistoricoRepuestoPK(id, fechaInicio);
        try {
            opt = repository.findById(pk);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el precio histórico del repuesto " + id +
                    ", con fecha de inicio: " + fechaInicio);
        }
        return opt.get();
    }

    @Override
    public void deletePrecioRepuesto(long id, LocalDate fechaInicio) throws BusinessException, NotFoundException {
        Optional<PrecioHistoricoRepuesto> opt=null;
        PrecioHistoricoRepuestoPK pk = new PrecioHistoricoRepuestoPK(id, fechaInicio);
        try {
            opt = repository.findById(pk);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el precio histórico del repuesto " + id +
                    ", con fecha de inicio: " + fechaInicio);
        } else {
            try {
                repository.deleteById(pk);
            } catch (Exception e1) {
                throw new BusinessException(e1.getMessage());
            }
        }
    }

    @Override
    public PrecioHistoricoRepuesto updatePrecioRepuesto(PrecioHistoricoRepuesto precioRepuesto) throws BusinessException, NotFoundException {
        Optional<PrecioHistoricoRepuesto> opt=null;
        PrecioHistoricoRepuestoPK id = new PrecioHistoricoRepuestoPK(precioRepuesto.getIdRepuesto(), precioRepuesto.getFechaInicio());
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el precio histórico del repuesto " + precioRepuesto.getIdRepuesto() +
                    ", con fecha de inicio: " + precioRepuesto.getFechaInicio());
        } else{
            try {
                validarPrecioHistorico(precioRepuesto);
                PrecioHistoricoRepuesto precioExistente = new PrecioHistoricoRepuesto(
                        precioRepuesto.getIdRepuesto(),
                        precioRepuesto.getFechaInicio(),
                        precioRepuesto.getFechaFinal(),
                        precioRepuesto.getPrecio()
                );
                return repository.save(precioExistente);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void setearPrecioHistorico(PrecioHistoricoRepuesto precioHistorico) {
        List<PrecioHistoricoRepuesto> precios = repository.findAll();
        LocalDate fechaActual = LocalDate.now(); //para fecha inicio
        LocalDate fecha = LocalDate.now().minusDays(1); //para fecha final
        precioHistorico.setFechaFinal(null);
        boolean condicion = false;

        //evitar crear otro registro cuando es mismo precio
        for (PrecioHistoricoRepuesto item: precios) {
            if (item.getIdRepuesto() == precioHistorico.getIdRepuesto()) {

                if (item.getFechaFinal() == null && item.getPrecio() == precioHistorico.getPrecio()) {
                    condicion = true;
                    break;
                }
            }
        }
        if (!condicion) {
            if (precioHistorico.getIdRepuesto() == 0)
                precioHistorico.setIdRepuesto(getRepuesto()); //setearle el id Repuesto
            precioHistorico.setFechaInicio(fechaActual);
            //actualizar (fecha final) registro anterior
            PrecioHistoricoRepuesto precioActualizar;
            for (PrecioHistoricoRepuesto item: precios) {
                if (item.getIdRepuesto() == precioHistorico.getIdRepuesto() &&
                        item.getFechaInicio() != precioHistorico.getFechaInicio()) {

                    if (item.getFechaFinal() == null) {
                        precioActualizar = new PrecioHistoricoRepuesto(item.getIdRepuesto(),
                                item.getFechaInicio(),
                                fecha,
                                item.getPrecio());
                        repository.save(precioActualizar);
                    }
                }
            }
        }

    }

    private void validarPrecioHistorico(PrecioHistoricoRepuesto precioHistorico) throws BusinessException {
        //fechas
        LocalDate fechaActual = LocalDate.now();
        //fecha inicio
        if (precioHistorico.getFechaInicio().isAfter(fechaActual)) {
            throw new BusinessException("La fecha de inicio es inválida");
        }
        //fecha final
        if (precioHistorico.getFechaFinal().isAfter(fechaActual)) {
            throw new BusinessException("La fecha final es inválida");
        }
        //precio
        if (String.valueOf(precioHistorico.getPrecio()).isEmpty()) {
            throw new BusinessException("El precio no debe estar vacío");
        }
        if (precioHistorico.getPrecio() <= 0) {
            throw new BusinessException("El precio no puede ser menor o igual a cero");
        }
    }

    @Autowired
    private RepuestoRepository repuestoRepository;
    private long getRepuesto() {
        ArrayList<Repuesto> repuestos = (ArrayList<Repuesto>) repuestoRepository.findAll();
        int x = repuestos.size() - 1;
        return repuestos.get(x).getIdRepuesto();
    }
}
