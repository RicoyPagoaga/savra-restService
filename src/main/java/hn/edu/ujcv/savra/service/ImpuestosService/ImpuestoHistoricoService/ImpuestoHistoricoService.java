package hn.edu.ujcv.savra.service.ImpuestosService.ImpuestoHistoricoService;

import hn.edu.ujcv.savra.entity.Impuesto.Impuesto;
import hn.edu.ujcv.savra.entity.Impuesto.ImpuestoHistorico.ImpuestoHistorico;
import hn.edu.ujcv.savra.entity.Impuesto.ImpuestoHistorico.ImpuestoHistoricoPK;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.Impuesto.ImpuestoHistoricoRepository;
import hn.edu.ujcv.savra.repository.Impuesto.ImpuestoRepository;
import hn.edu.ujcv.savra.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImpuestoHistoricoService implements IImpuestoHistoricoService {

    private Log mi_log = new Log();

    @Autowired
    private ImpuestoHistoricoRepository repository;

    @Override
    public ImpuestoHistorico saveImpuestoHistorico(ImpuestoHistorico impuestoHistorico) throws BusinessException {
        try {
            validarImpuestoHistorico(impuestoHistorico);
            //setear impuesto
            setearImpuestoHistorico(impuestoHistorico);
            return repository.save(impuestoHistorico);
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<ImpuestoHistorico> getImpuestosHistorico() throws BusinessException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public ImpuestoHistorico updateImpuestoHistorico(ImpuestoHistorico impuestoHistorico) throws BusinessException, NotFoundException {
        Optional<ImpuestoHistorico> opt=null;
        ImpuestoHistoricoPK id = new ImpuestoHistoricoPK(impuestoHistorico.getIdImpuesto(), impuestoHistorico.getFechaInicio());
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró el impuesto " + impuestoHistorico.getIdImpuesto() +
                    ", con fecha de inicio: " + impuestoHistorico.getFechaInicio());
            throw new NotFoundException("No se encontró el impuesto " + impuestoHistorico.getIdImpuesto() +
                    ", con fecha de inicio: " + impuestoHistorico.getFechaInicio());
        } else{
            try {
                validarImpuestoHistorico(impuestoHistorico);
                actualizarImpuestoHistorico(impuestoHistorico);
                ImpuestoHistorico impuestoHistoricoExistente = new ImpuestoHistorico(
                        impuestoHistorico.getIdImpuesto(),
                        impuestoHistorico.getFechaInicio(),
                        impuestoHistorico.getFechaFinal(),
                        impuestoHistorico.getValor()
                );
                return repository.save(impuestoHistoricoExistente);
            } catch (Exception e) {
                mi_log.CrearArchivo(this.getClass().getSimpleName());
                mi_log.logger.severe(e.getMessage());
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void validarImpuestoHistorico(ImpuestoHistorico impuestoHistorico) throws BusinessException {
        //fecha
        LocalDate fechaActual = LocalDate.now();
        //fecha final
        if (impuestoHistorico.getFechaFinal() != null &&impuestoHistorico.getFechaFinal().isAfter(fechaActual)) {
            throw new BusinessException("La fecha final es inválida");
        }
        //valor
        if (impuestoHistorico.getValor() < 0) {
            throw new BusinessException("El valor no puede ser menor a cero");
        }
        if (impuestoHistorico.getValor() > 100) {
            throw new BusinessException("El valor no puede ser mayor a cien");
        }
    }

    private void setearImpuestoHistorico(ImpuestoHistorico impuestoHistorico) {
        List<ImpuestoHistorico> impuestos = repository.findAll();
        LocalDate fechaActual = LocalDate.now(); //maxdate
        LocalDate fechaActualizar = fechaActual.plusDays(1); //para fecha inicio

        impuestoHistorico.setFechaFinal(null);
        boolean condicion = false;

        //evitar crear otro registro cuando es mismo precio
        for (ImpuestoHistorico item: impuestos) {
            if (item.getIdImpuesto() == impuestoHistorico.getIdImpuesto()) {

                if (item.getFechaFinal() == null && item.getValor() == impuestoHistorico.getValor()) {
                    condicion = true;
                    break;
                }
            }
        }
        boolean encontrado = false;
        if (!condicion) {
            if (impuestoHistorico.getIdImpuesto() == 0)
                impuestoHistorico.setIdImpuesto(getImpuestoID()); //setearle el id
            if (!impuestoHistorico.getFechaInicio().equals(fechaActual) && !impuestoHistorico.getFechaInicio().equals(fechaActualizar)) {
                for (ImpuestoHistorico item: impuestos) {
                    if (item.getIdImpuesto() == impuestoHistorico.getIdImpuesto()) {
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    impuestoHistorico.setFechaInicio(fechaActual);
                } else {
                    impuestoHistorico.setFechaInicio(fechaActualizar);
                }
            }
            //actualizar (fecha final) registro anterior
            ImpuestoHistorico impuestoHistoricoActualizar;
            for (ImpuestoHistorico item: impuestos) {
                if (item.getIdImpuesto() == impuestoHistorico.getIdImpuesto() &&
                        item.getFechaInicio() != impuestoHistorico.getFechaInicio()) {

                    if (item.getFechaFinal() == null) {
                        impuestoHistoricoActualizar = new ImpuestoHistorico(
                                item.getIdImpuesto(),
                                item.getFechaInicio(),
                                fechaActual,
                                item.getValor());
                        repository.save(impuestoHistoricoActualizar);
                    }
                }
            }
        }
    }

    @Autowired
    private ImpuestoRepository impuestoRepository;
    private long getImpuestoID() {
        ArrayList<Impuesto> impuestos = (ArrayList<Impuesto>) impuestoRepository.findAll();
        int x = impuestos.size() - 1;
        return impuestos.get(x).getIdImpuesto();
    }

    private void actualizarImpuestoHistorico(ImpuestoHistorico impuestoHistorico) throws BusinessException {
        List<ImpuestoHistorico> impuestos = repository.findAll();
        LocalDate fechaActual = LocalDate.now(); //maxdate
        LocalDate fechaAyer = LocalDate.now().minusDays(1); //mindate

        try {
            if (impuestoHistorico.getFechaFinal() != null){
                if (impuestoHistorico.getFechaFinal().isEqual(fechaAyer) || impuestoHistorico.getFechaFinal().isEqual(fechaActual)) {
                    //Actualizar fecha de Inicio de siguiente registro
                    LocalDate fechaActualizar = impuestoHistorico.getFechaFinal().plusDays(1); //para fecha inicio
                    ImpuestoHistorico impuestoHistoricoActualizar;
                    for (ImpuestoHistorico item : impuestos) {
                        if (item.getIdImpuesto() == impuestoHistorico.getIdImpuesto() && item.getFechaFinal() == null) {
                            impuestoHistoricoActualizar = new ImpuestoHistorico(
                                    item.getIdImpuesto(),
                                    fechaActualizar,
                                    null,
                                    item.getValor());
                            repository.save(impuestoHistoricoActualizar);
                            if (!item.getFechaInicio().isEqual(fechaActualizar)) {
                                repository.delete(item);
                            }
                        }
                    }
                } else {
                    mi_log.CrearArchivo(this.getClass().getSimpleName());
                    mi_log.logger.severe("Fecha Final inválida");
                    throw new BusinessException("Fecha Final inválida");
                }
            }
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }
}
