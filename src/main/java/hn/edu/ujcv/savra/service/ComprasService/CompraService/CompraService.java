package hn.edu.ujcv.savra.service.ComprasService.CompraService;

import hn.edu.ujcv.savra.entity.Compra.Compra;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.Compra.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CompraService implements ICompraService {

    @Autowired
    private CompraRepository repository;

    @Override
    public Compra saveCompra(Compra compra, boolean detalle) throws BusinessException {
        try {
            validarCompra(compra, detalle);
            return repository.save(compra);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Compra> saveCompras(List<Compra> compras) throws BusinessException {
        try {
            for (Compra item: compras) {
                validarCompra(item, false);
            }
            return repository.saveAll(compras);
        } catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Compra> getCompras() throws BusinessException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Compra getCompraById(long id) throws BusinessException, NotFoundException {
        Optional<Compra> opt=null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró la compra: " + id);
        }
        return opt.get();
    }

    @Override
    public void deleteCompra(long id) throws BusinessException, NotFoundException {
        Optional<Compra> opt = null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró la compra: " + id);
        } else {
            try {
                repository.deleteById(id);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }

    @Override
    public Compra updateCompra(Compra compra, boolean detalle) throws BusinessException, NotFoundException {
        Optional<Compra> opt = null;
        try {
            if (compra.getIdCompra() <= 0) {
                throw new BusinessException("Id de Compra inválido");
            }
            opt = repository.findById(compra.getIdCompra());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró la compra: " + compra.getIdCompra());
        } else {
            try {
                validarCompra(compra, detalle);
                Compra compraExistente = new Compra(
                        compra.getIdCompra(),
                        compra.getIdEmpleado(),
                        compra.getFechaCompra(),
                        compra.getFechaDespacho(),
                        compra.getFechaRecibido(),
                        compra.getNoComprobante()
                );
                return repository.save(compraExistente);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void validarCompra(Compra compra, boolean detalle) throws BusinessException {
        //empleado
        if (compra.getIdEmpleado() <= 0) {
            throw new BusinessException("Indique empleado válido");
        }
        //fechas
        LocalDate fechaActual = LocalDate.now();
        //fechaCompra
        if (compra.getFechaCompra() == null) {
            throw new BusinessException("La fecha de compra no debe estar vacía");
        }
        if (!compra.getFechaCompra().equals(fechaActual)) {
            throw new BusinessException("La fecha de la compra debe ser la fecha de hoy");
        }
        //fechaDespacho
        /*if (compra.getFechaDespacho() == null) {
            throw new BusinessException("La fecha de despacho no debe estar vacía");
        }*/
        if (compra.getFechaDespacho() != null) {
            if (compra.getFechaDespacho().isBefore(fechaActual)) {
                throw new BusinessException("La fecha de despacho no puede ser una fecha anterior a la fecha de compra");
            }
            if (compra.getFechaDespacho().isAfter(fechaActual)) {
                throw new BusinessException("La fecha de despacho no puede ser a futuro");
            }
        }
        //fechaRecibido
        /*if (compra.getFechaRecibido() == null) {
            throw new BusinessException("La fecha de entrega no debe estar vacía");
        }*/
        if (compra.getFechaRecibido() != null) {
            if (compra.getFechaRecibido().isBefore(fechaActual)) {
                throw new BusinessException("La fecha de entrega no puede ser una fecha anterior a la fecha de compra");
            }
            if (compra.getFechaRecibido().isBefore(compra.getFechaDespacho())) {
                throw new BusinessException("La fecha de entrega no puede ser una fecha anterior a la fecha de despacho");
            }
            if (compra.getFechaRecibido().isAfter(fechaActual)) {
                throw new BusinessException("La fecha de entrega no puede ser a futuro");
            }
        }
        //no. Comprobante
        if(compra.getNoComprobante().isEmpty()) {
            throw new BusinessException("Número de comprobante es requerido");
        }
        if(compra.getNoComprobante().length() < 10) {
            throw new BusinessException("Número de comprobante debe tener más de diez digitos");
        }
        if(compra.getNoComprobante().length() > 20) {
            throw new BusinessException("Número de comprobante no debe exceder los veinte digitos");
        }
        Pattern pat = Pattern.compile("[\\d]*");
        Matcher mat = pat.matcher(compra.getNoComprobante().trim());
        if(!mat.matches()){
            throw new BusinessException("Número de comprobante debe ser númerico");
        }
        //detalle
        if(!detalle) {
            throw new BusinessException("Detalles de la compra no debe estar vacío");
        }
    }
}
