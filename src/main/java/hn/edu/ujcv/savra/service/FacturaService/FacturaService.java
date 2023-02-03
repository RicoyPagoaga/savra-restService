package hn.edu.ujcv.savra.service.FacturaService;

import hn.edu.ujcv.savra.entity.Cupon;
import hn.edu.ujcv.savra.entity.Factura;
import hn.edu.ujcv.savra.entity.FacturaRecibo;
import hn.edu.ujcv.savra.entity.ParametroFactura;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.CuponRepository;
import hn.edu.ujcv.savra.repository.FacturaRepository;
import hn.edu.ujcv.savra.repository.ParametroFacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FacturaService implements IFacturaService{
    @Autowired
    private FacturaRepository repository;
    @Override
    public Factura guardarFactura(Factura pFactura) throws BusinessException {
        try{
            validarFactura(pFactura);
            System.out.println("Hola " + pFactura.getFechaFactura());
            return repository.save(pFactura);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Factura> obtenerFacturas() throws BusinessException {
        try{
            return repository.findAll();
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Factura obtenerFacturaByNoFactura(String idFactura) throws BusinessException, NotFoundException {
        Optional<Factura> opt = null;
        try{
            opt = repository.findByNoFactura(idFactura);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
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
            throw new BusinessException(e.getMessage());
        }
        if(!opt.isPresent()){
            throw new NotFoundException("No se pudo enoncontrar esta Factura: " + idFactura);
        }
        return opt.get();
    }

    private void validarFactura(Factura pFactura)throws BusinessException{
        if (pFactura.getIdParametroFactura() < 1){
            throw new BusinessException("Parámetro de factura esta vacío");
        }
        if (pFactura.getFechaFactura() == null){
            throw new BusinessException("Fecha factura esta vacía!");
        }
        if (pFactura.getIdCliente() < 1){
            throw new BusinessException("Cliente esta vacío!");
        }
        if (pFactura.getIdEmpleado() < 1){
            throw new BusinessException("Empleado esta vacío!");
        }
        if (pFactura.getIdMetodoPago() < 1){
            throw new BusinessException("Forma de Pago esta vacío!");
        }
        if (pFactura.getIdMetodoPago() == 2){
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
        if (pFactura.getIdMetodoPago() == 3){
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
        if (pFactura.getIdTipoEntrega()< 1){
            throw new BusinessException("Tipo de Entrega esta vacío!");
        }
        if (pFactura.getIdCupon() != null){
            Cupon miCupon = validarCupon(pFactura.getIdCupon());
            if (miCupon.getFechaCaducidad().isBefore(LocalDate.now())){
                throw new BusinessException("Código del cupón ha caducido :(");
            }
        }
        if (pFactura.getIdTipoEntrega() == 2){
            if(pFactura.getIdShipper().toString().isEmpty()){
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
        ParametroFactura param = validarParametro(pFactura.getIdParametroFactura());

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
    private ParametroFactura validarParametro(long idParametro)throws BusinessException{
        List<ParametroFactura> parametros = repositoryParametro.findAll();
        for (ParametroFactura item:
             parametros) {
            if(item.getIdParametro() == idParametro){
                return item;
            }
        }
        return null;
    }

    @Autowired
    private CuponRepository repositoryCupon;
    private Cupon validarCupon(long idCupon)throws BusinessException{
        List<Cupon> cupones = repositoryCupon.findAll();
        for (Cupon item:cupones) {
            if(item.getIdCupon() == idCupon){
                return item;
            }
        }
        return null;
    }
}
