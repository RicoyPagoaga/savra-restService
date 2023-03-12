package hn.edu.ujcv.savra.service.ComprasService.DevolucionCompraService;

import hn.edu.ujcv.savra.entity.Compra.Compra;
import hn.edu.ujcv.savra.entity.Compra.CompraDetalle;
import hn.edu.ujcv.savra.entity.Compra.DevolucionCompra;
import hn.edu.ujcv.savra.entity.Repuesto.Repuesto;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.Compra.CompraDetalleRepository;
import hn.edu.ujcv.savra.repository.Compra.CompraRepository;
import hn.edu.ujcv.savra.repository.Compra.DevolucionCompraRepository;
import hn.edu.ujcv.savra.repository.Repuesto.RepuestoRepository;
import hn.edu.ujcv.savra.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class DevolucionCompraService implements IDevolucionCompraService{

    private Log mi_log = new Log();

    @Autowired
    private DevolucionCompraRepository repository;

    @Override
    public DevolucionCompra saveDevolucionCompra(DevolucionCompra devolucionCompra) throws BusinessException {
        try {
            devolucionCompra.setMotivo(devolucionCompra.getMotivo().trim());
            validarDevolucion(devolucionCompra);
            return repository.save(devolucionCompra);
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<DevolucionCompra> saveDevolucionesCompra(List<DevolucionCompra> devolucionesCompra) throws BusinessException {
        try {
            for (DevolucionCompra item: devolucionesCompra) {
                item.setMotivo(item.getMotivo().trim());
                validarDevolucion(item);
            }
            return repository.saveAll(devolucionesCompra);
        } catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<DevolucionCompra> getDevolucionesCompra() throws BusinessException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public DevolucionCompra getDevolucionCompraById(long id) throws BusinessException, NotFoundException {
        Optional<DevolucionCompra> opt=null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró la devolución de compra: " + id);
            throw new NotFoundException("No se encontró la devolución de compra: " + id);
        }
        return opt.get();
    }

    @Override
    public void deleteDevolucionCompra(long id) throws BusinessException, NotFoundException {
        Optional<DevolucionCompra> opt = null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró la devolución de compra: " + id);
            throw new NotFoundException("No se encontró la devolución de compra: " + id);
        } else {
            try {
                repository.deleteById(id);
            } catch (Exception e) {
                mi_log.CrearArchivo(this.getClass().getSimpleName());
                mi_log.logger.severe(e.getMessage());
                throw new BusinessException(e.getMessage());
            }
        }
    }

    @Override
    public DevolucionCompra updateDevolucionCompra(DevolucionCompra devolucionCompra) throws BusinessException, NotFoundException {
        Optional<DevolucionCompra> opt = null;
        try {
            if (devolucionCompra.getIdDevolucion() <= 0) {
                throw new BusinessException("Id de Devolución de Compra inválido");
            }
            opt = repository.findById(devolucionCompra.getIdDevolucion());
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró la devolución de compra: " + devolucionCompra.getIdDevolucion());
            throw new NotFoundException("No se encontró la devolución de compra: " + devolucionCompra.getIdDevolucion());
        } else {
            try {
                validarDevolucion(devolucionCompra);
                DevolucionCompra devolucionExistente = new DevolucionCompra(
                        devolucionCompra.getIdDevolucion(),
                        devolucionCompra.getCompraDetalle(),
                        devolucionCompra.getFecha(),
                        devolucionCompra.getCantidad(),
                        devolucionCompra.getMotivo().trim()
                );
                return repository.save(devolucionExistente);
            } catch (Exception e) {
                mi_log.CrearArchivo(this.getClass().getSimpleName());
                mi_log.logger.severe(e.getMessage());
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void validarDevolucion(DevolucionCompra devolucionCompra) throws BusinessException {
        //id detalle
        if(devolucionCompra.getCompraDetalle()==null) {
            throw new BusinessException("Indique detalle de compra válido");
        }
        //fecha
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaEntrega = validarFecha(devolucionCompra);
        if(devolucionCompra.getFecha() == null) {
            throw new BusinessException("La fecha no debe estar vacía");
        }
        if(devolucionCompra.getIdDevolucion()<=0) {
            if(!devolucionCompra.getFecha().equals(fechaActual)) {
                throw new BusinessException("La fecha de la devolución en el detalle "+devolucionCompra.getCompraDetalle().getIdCompraDetalle()+
                        " debe ser la fecha de hoy");
            }
        }
        if(devolucionCompra.getFecha().isAfter(fechaEntrega.plusDays(30))) {
            throw new BusinessException("La fecha de la devolución en el detalle "+devolucionCompra.getCompraDetalle().getIdCompraDetalle()+
                    " no debe exceder los treinta días de su fecha de Entrega");
        }
        //cantidad
        if(devolucionCompra.getCantidad()<=0) {
            throw new BusinessException("La cantidad en el detalle " +devolucionCompra.getCompraDetalle().getIdCompraDetalle() +
                    " es requerida, no puede ser menor o igual a cero");
        }
        if(validarCantidad(devolucionCompra)) {
            throw new BusinessException("La cantidad en el detalle "+devolucionCompra.getCompraDetalle().getIdCompraDetalle()+
                    " no puede ser mayor a la cantidad que se compró");
        }
        int x = validarStock(devolucionCompra);
        if(x!=-1) {
            if(devolucionCompra.getCantidad()>x) {
                throw new BusinessException("La cantidad de devolución en el detalle "+devolucionCompra.getCompraDetalle().getIdCompraDetalle()+
                        " excede el Stock Actual del Repuesto!");
            }
        }
        //motivo
        if(devolucionCompra.getMotivo().trim().isEmpty()) {
            throw new BusinessException("El motivo de devolución en el detalle "+devolucionCompra.getCompraDetalle().getIdCompraDetalle()+
                    " es requerido");
        }
        if(devolucionCompra.getMotivo().trim().length() < 5) {
            throw new BusinessException("Ingrese más de cinco caracteres en el motivo de devolución en el detalle "+devolucionCompra.getCompraDetalle().getIdCompraDetalle());
        }
        if(devolucionCompra.getMotivo().trim().length() > 80) {
            throw new BusinessException("El motivo de devolución en el detalle "+devolucionCompra.getCompraDetalle().getIdCompraDetalle()+
                    " no debe exceder los ochenta caracteres");
        }
        Pattern dobleEspacio = Pattern.compile("\\s{2,}");
        if (dobleEspacio.matcher(devolucionCompra.getMotivo().trim()).find()) {
            throw new BusinessException("Motivo de Devolución en el detalle "+devolucionCompra.getCompraDetalle().getIdCompraDetalle()+" no debe contener espacios dobles ఠ_ఠ");
        }
        String[] motivo = devolucionCompra.getMotivo().trim().split(" ");
        for (String item: motivo) {
            if(item.matches("(.)\\1{2,}")) {
                throw new BusinessException("Motivo de Devolución en el detalle "+devolucionCompra.getCompraDetalle().getIdCompraDetalle()+
                        " no debe tener tantas letras repetidas ఠ_ఠ");
            }
        }
    }

    @Autowired
    private CompraDetalleRepository detalleRepository;
    private boolean validarCantidad(DevolucionCompra devolucionCompra) {
        List<CompraDetalle> lista = detalleRepository.findAll();
        boolean condicion=false;
        for(CompraDetalle item: lista) {
            if(devolucionCompra.getCompraDetalle().getIdCompraDetalle() == item.getIdCompraDetalle() &&
                    devolucionCompra.getCantidad() > item.getCantidad()) {
                condicion=true;
            }
        }
        return condicion;
    }

    @Autowired
    private RepuestoRepository repuestoRepository;
    private int validarStock(DevolucionCompra devolucionCompra) {
        int x=-1;
        List<CompraDetalle> detalles = detalleRepository.findAll();
        List<Repuesto> repuestos = repuestoRepository.findAll();
        for(Repuesto repuesto: repuestos) {
            for(CompraDetalle detalle: detalles) {
                if(devolucionCompra.getCompraDetalle().getIdCompraDetalle()==detalle.getIdCompraDetalle() &&
                detalle.getIdRepuesto()==repuesto.getIdRepuesto()) {
                    x=repuesto.getStockActual();
                    break;
                }
            }
        }
        return x;
    }

    @Autowired
    private CompraRepository compraRepository;
    private LocalDate validarFecha(DevolucionCompra devolucionCompra) {
        List<Compra> lista = compraRepository.findAll();
        List<CompraDetalle> detalles = detalleRepository.findAll();
        LocalDate fecha=null;
        for(CompraDetalle detalle: detalles) {
            for(Compra compra: lista) {
                if(devolucionCompra.getCompraDetalle().getIdCompraDetalle()==detalle.getIdCompraDetalle() &&
                detalle.getIdCompra()==compra.getIdCompra()) {
                    fecha=compra.getFechaRecibido();
                }
            }
        }
        return fecha;
    }
}
