package hn.edu.ujcv.savra.service.RepuestosService.PrecioHistoricoRepuestoService;

import hn.edu.ujcv.savra.entity.Repuesto.PrecioHistoricoRepuesto.PrecioHistoricoRepuesto;
import hn.edu.ujcv.savra.entity.Repuesto.PrecioHistoricoRepuesto.PrecioHistoricoRepuestoPK;
import hn.edu.ujcv.savra.entity.Repuesto.Repuesto;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.Repuesto.PrecioHistoricoRepuestoRepository;
import hn.edu.ujcv.savra.repository.Repuesto.RepuestoRepository;
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
                actualizarPrecioHistorico(precioRepuesto);
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

    private void actualizarPrecioHistorico(PrecioHistoricoRepuesto precioHistorico) throws BusinessException{
        List<PrecioHistoricoRepuesto> precios = repository.findAll();
        LocalDate fechaActual = LocalDate.now(); //maxdate
        LocalDate fechaAyer = LocalDate.now().minusDays(1); //mindate

        try {
            if(precioHistorico.getFechaFinal() != null){
                if (precioHistorico.getFechaFinal().isEqual(fechaAyer) || precioHistorico.getFechaFinal().isEqual(fechaActual)) {
                    //Actualizar fecha de Inicio de siguiente registro
                    LocalDate fechaActualizar = precioHistorico.getFechaFinal().plusDays(1); //para fecha inicio
                    PrecioHistoricoRepuesto precioActualizar;
                    for (PrecioHistoricoRepuesto item : precios) {
                        if (item.getIdRepuesto() == precioHistorico.getIdRepuesto() && item.getFechaFinal() == null) {
                            precioActualizar = new PrecioHistoricoRepuesto(item.getIdRepuesto(), fechaActualizar,
                                    null, item.getPrecio());
                            repository.save(precioActualizar);
                            if (!item.getFechaInicio().isEqual(fechaActualizar)) {
                                repository.delete(item);
                            }
                        }
                    }
                } else {
                    throw new BusinessException("Fecha Final inválida");
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    private void setearPrecioHistorico(PrecioHistoricoRepuesto precioHistorico) {
        List<PrecioHistoricoRepuesto> precios = repository.findAll();
        LocalDate fechaActual = LocalDate.now(); //maxdate
        LocalDate fechaActualizar = fechaActual.plusDays(1); //para fecha inicio

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
        boolean encontrado = false;
        if (!condicion) {
            if (precioHistorico.getIdRepuesto() == 0)
                precioHistorico.setIdRepuesto(getRepuesto()); //setearle el id Repuesto
            if (!precioHistorico.getFechaInicio().equals(fechaActual) && !precioHistorico.getFechaInicio().equals(fechaActualizar)) {
                for (PrecioHistoricoRepuesto item: precios) {
                    if (item.getIdRepuesto() == precioHistorico.getIdRepuesto()) {
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    precioHistorico.setFechaInicio(fechaActual);
                } else {
                    precioHistorico.setFechaInicio(fechaActualizar);
                }
            }
            //actualizar (fecha final) registro anterior
            PrecioHistoricoRepuesto precioActualizar;
            for (PrecioHistoricoRepuesto item: precios) {
                if (item.getIdRepuesto() == precioHistorico.getIdRepuesto() &&
                        item.getFechaInicio() != precioHistorico.getFechaInicio()) {

                    if (item.getFechaFinal() == null) {
                        precioActualizar = new PrecioHistoricoRepuesto(item.getIdRepuesto(),
                                item.getFechaInicio(),
                                fechaActual,
                                item.getPrecio());
                        repository.save(precioActualizar);
                    }
                }
            }
        }

    }

    private void validarPrecioHistorico(PrecioHistoricoRepuesto precioHistorico) throws BusinessException {
        //fecha
        LocalDate fechaActual = LocalDate.now();
        //fecha final
        if (precioHistorico.getFechaFinal() != null && precioHistorico.getFechaFinal().isAfter(fechaActual)) {
            throw new BusinessException("La fecha final es inválida");
        }
        //precio
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
