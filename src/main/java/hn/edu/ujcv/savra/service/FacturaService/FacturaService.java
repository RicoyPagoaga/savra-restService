package hn.edu.ujcv.savra.service.FacturaService;

import hn.edu.ujcv.savra.entity.*;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.CuponRepository;
import hn.edu.ujcv.savra.repository.FacturaRepository;
import hn.edu.ujcv.savra.repository.ParametroFacturaRepository;
import hn.edu.ujcv.savra.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FacturaService implements IFacturaService{

    private Log mi_log = new Log();
    private final String clase = this.getClass().getSimpleName();
    @Autowired
    private FacturaRepository repository;
    @Override
    public Factura guardarFactura(Factura pFactura,int pDetalles,double total) throws BusinessException {
        try{
            validarFactura(pFactura,pDetalles,total);
            return repository.save(pFactura);
        }catch (Exception e){
            mi_log.CrearArchivo(clase);
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Factura> obtenerFacturas() throws BusinessException {
        try{
            return repository.findAll();
        }catch (Exception e){
            mi_log.CrearArchivo(clase);
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Factura obtenerFacturaByNoFactura(String idFactura) throws BusinessException, NotFoundException {
        Optional<Factura> opt = null;
        try{
            opt = repository.findByNoFactura(idFactura);
        }catch (Exception e){
            mi_log.CrearArchivo(clase);
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            mi_log.CrearArchivo(clase);
            mi_log.logger.severe("No se encontró la factura número: " + idFactura);
            throw new NotFoundException("No se encontró la factura número: " + idFactura);
        }
        return opt.get();
    }

    @Override
    public Object obtenerRecibo(long idFactura) throws BusinessException, NotFoundException {
        Optional<FacturaRecibo> opt = null;
        try{
            opt = repository.getReciboEncabezado(idFactura);
        }catch (Exception e){
            mi_log.CrearArchivo(clase);
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if(!opt.isPresent()){
            mi_log.CrearArchivo(clase);
            mi_log.logger.severe("No se pudo enoncontrar esta Factura: " + idFactura);
            throw new NotFoundException("No se pudo enoncontrar esta Factura: " + idFactura);
        }
        return opt.get();
    }

    private void validarFactura(Factura pFactura, int detalles,double total)throws BusinessException,NotFoundException{
        if(detalles == 0){
            throw new BusinessException("Agregue un repuesto/artículo antes de facturar!");
        }
        if (pFactura.getParametroFactura() == null){
            throw new BusinessException("Parámetro de factura esta vacío");
        }
        if (pFactura.getFechaFactura() == null){
            throw new BusinessException("Fecha factura esta vacía!");
        }
        if (pFactura.getCliente() == null){
            throw new BusinessException("Cliente esta vacío!");
        }
        if (pFactura.getEmpleado() == null){
            throw new BusinessException("Empleado esta vacío!");
        }
        if (pFactura.getMetodoPago() == null){
            throw new BusinessException("Forma de Pago esta vacío!");
        }
        if(pFactura.getMetodoPago().getNombre().contains("EFECTIVO")){
            if(pFactura.getEfectivo() <= 0){
                throw new BusinessException("Efectivo no puede ser menor o igual a 0!");
            }
            if(pFactura.getEfectivo() < total){
                throw new BusinessException("Efectivo digitado insuficiente!");
            }
        }
        if (pFactura.getMetodoPago().getNombre().contains("TARJETA")){
            if (pFactura.getTarjeta().trim().isEmpty()){
                throw new BusinessException("No. de Tarjeta vacío!");
            }
            if (pFactura.getTarjeta().trim().length() < 13 ){
                throw new BusinessException("No. de Tarjeta Inválida!");
            }
            Pattern pat = Pattern.compile("[\\d]*");
            Matcher mat = pat.matcher(pFactura.getTarjeta().trim());
            if(!mat.matches()){
                throw new BusinessException("No. de Tarjeta debe ser Númerica");
            }
            pFactura.setTarjeta(pFactura.getTarjeta().substring(pFactura.getTarjeta().length()-4));
        }
        if (pFactura.getMetodoPago().getNombre().contains("MIXTO")){
            if(pFactura.getEfectivo() <= 0){
                throw new BusinessException("Efectivo no puede ser menor o igual a 0!");
            }
            if(pFactura.getEfectivo() >= total){
                throw new BusinessException("Efectivo no puede ser mayor o igual al total!");
            }
            if (pFactura.getTarjeta().trim().isEmpty()){
                throw new BusinessException("No. de Tarjeta vacío!");
            }
            if (pFactura.getTarjeta().trim().length() < 13 ){
                throw new BusinessException("No. de Tarjeta Inválida!");
            }
            Pattern pat = Pattern.compile("[\\d]*");
            Matcher mat = pat.matcher(pFactura.getTarjeta().trim());
            if(!mat.matches()){
                throw new BusinessException("No. de Tarjeta debe ser Númerica");
            }
            pFactura.setTarjeta(pFactura.getTarjeta().substring(pFactura.getTarjeta().length()-4));
        }
        if (pFactura.getTipoEntrega() == null){
            throw new BusinessException("Tipo de Entrega esta vacío!");
        }
        if (pFactura.getCupon() != null){
            Cupon miCupon = validarCupon(pFactura.getCupon().getIdCupon());
            if (miCupon.getFechaCaducidad().isBefore(LocalDate.now())){
                throw new BusinessException("Código del cupón ha caducido :(");
            }
        }
        if (pFactura.getTipoEntrega().getNombre().contains("ENVIO")){
            if(pFactura.getShipper() == null){
                throw new BusinessException("Shipper esta vacío");
            }
            if(pFactura.getCostoEnvio() < 50){
                throw new BusinessException("Costo de Envío no puede ser menor de L. 50.00");
            }
            if(pFactura.getFechaDespacho() == null){
                throw new BusinessException("Fecha de despacho Esta vacío");
            }
            if(pFactura.getFechaDespacho().isBefore(LocalDate.now())){
                throw new BusinessException("Fecha de Despacho no puede ser menor a hoy");
            }
            if(pFactura.getFechaDespacho().isAfter(LocalDate.now().plusDays(3))){
                throw new BusinessException("Fecha de Despacho no puede ser mayor de 3 días de hoy");
            }
            if(pFactura.getFechaEntrega().isBefore(pFactura.getFechaDespacho())){
                throw new BusinessException("Fecha de entrega no puede ser menor de la fecha de despacho");
            }
            if(pFactura.getFechaEntrega().isAfter(pFactura.getFechaDespacho().plusMonths(1))){
                throw new BusinessException("Fecha de entrega no puede ser mayor del mes de la fecha de despacho");
            }
        }
        ParametroFactura param = validarParametro(pFactura.getParametroFactura().getIdParametro());

        if(param.getFechaLimiteEmision().isBefore(pFactura.getFechaFactura().toLocalDateTime().toLocalDate())){
            throw new BusinessException("se ha sobrepasado la fecha límite de emisión del parámetro seleccionado");
        }
        if(pFactura.getNoFactura() != null){
            String[] item = pFactura.getNoFactura().split("-");
            String[] item2 = param.getRangoFinal().split("-");

            if(Integer.parseInt(item[3]) > Integer.parseInt(item2[3])){
                throw new BusinessException("No se puede generar la factura, ha alcanzado el máximo de facturas permitidas por el parámetro seleccionado");
            }
        }
    }
    @Autowired
    private ParametroFacturaRepository repositoryParametro;
    private ParametroFactura validarParametro(long idParametro)throws BusinessException, NotFoundException{
        Optional<ParametroFactura> opt = null;
        try{
            opt = repositoryParametro.findById(idParametro);
        }catch (Exception e){
            mi_log.CrearArchivo(clase);
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            mi_log.CrearArchivo(clase);
            mi_log.logger.severe("No se encontró el parámetro seleccionado");
            throw new NotFoundException("No se encontró el parámetro seleccionado");
        }
        return opt.get();
    }

    @Autowired
    private CuponRepository repositoryCupon;
    private Cupon validarCupon(long idCupon)throws BusinessException,NotFoundException{
        Optional<Cupon> opt = null;
        try{
            opt = repositoryCupon.findById(idCupon);
        }catch (Exception e){
            mi_log.CrearArchivo(clase);
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            mi_log.CrearArchivo(clase);
            mi_log.logger.severe("No se encontró el parámetro seleccionado");
            throw new NotFoundException("No se encontró el parámetro seleccionado");
        }
        return opt.get();
    }
}
