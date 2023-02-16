package hn.edu.ujcv.savra.service.CuponService;

import hn.edu.ujcv.savra.entity.Cupon;
import hn.edu.ujcv.savra.entity.TipoDocumento;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.CuponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CuponService implements ICuponService{

    @Autowired
    private CuponRepository repository;
    @Override
    public Cupon saveCupon(Cupon pCupon) throws BusinessException {
        try {
            pCupon.setCodigo(pCupon.getCodigo().toUpperCase());
            Optional<Cupon> opt = null;
            opt = repository.findByCodigo(pCupon.getCodigo());
            if (opt.isPresent()){
                throw new BusinessException("Código de Cupón ya se encuentra en uso");
            }
            validarCupon(pCupon);
            return repository.save(pCupon);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }



    @Override
    public Cupon actualizarCupon(Cupon pCupon) throws BusinessException, NotFoundException {
        Optional<Cupon> opt = null;
        try {
            opt = repository.findById(pCupon.getIdCupon());
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if(!opt.isPresent()){
          throw new NotFoundException("No se encontró el cupón: " + pCupon.getIdCupon());
        }else {
            try {
                validarCupon(pCupon);
                Cupon newCupon = new Cupon(pCupon.getIdCupon(),pCupon.getCodigo(),pCupon.getFechaEmision(),
                        pCupon.getFechaCaducidad(),pCupon.getCantidadMaxima(), pCupon.getCantidadDisponible(), pCupon.getActivo(), pCupon.getPorcentajeDescuento());
                return repository.save(newCupon);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
    }

    @Override
    public List<Cupon> obtenerCupones() throws BusinessException {
        return repository.findAll();
    }

    @Override
    public void eliminarCupon(long id) throws BusinessException, NotFoundException {
        Optional<Cupon> opt = null;
        try{
            opt = repository.findById(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("No se encontró el cupón: "+ id);
        }else {
            try {
                repository.deleteById(id);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
    }

    @Override
    public Cupon getCuponById(long ig) throws BusinessException, NotFoundException {
        return null;
    }

    @Override
    public Cupon getCuponByCodigo(String codigo) throws BusinessException, NotFoundException {
        return null;
    }

    @Override
    public void activarDesactivarcupon(int estado,long id) throws BusinessException, NotFoundException {
        Optional<Cupon> opt = null;
        try{
            opt = repository.findById(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("No se encontró el cupón: "+ id);
        }else {
            try {
                repository.activoCupon(estado,id);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void validarCupon(Cupon pCupon) throws BusinessException {
        //codigo
        if (pCupon.getCodigo().trim().isEmpty()){
            throw new BusinessException("Código del cupon esta vacío");
        }
        if (pCupon.getCodigo().trim().length() > 25){
            throw new BusinessException("Código del cupon no debe ser mayor a 25 caracteres");
        }
        if (pCupon.getCodigo().trim().length() < 5){
            throw new BusinessException("Código del cupon no debe ser menor de 5 caracteres");
        }
        Pattern pat = Pattern.compile("[a-zA-Z0-9]*");
        Matcher mat = pat.matcher(pCupon.getCodigo().trim());
        if (!mat.matches()){
            throw new BusinessException("Código del cupon no debe tener espacios ni caracteres especiales");
        }
        List<Cupon> metodos = obtenerCupones();
        for (Cupon item : metodos) {
            if ((item.getCodigo().equals(pCupon.getCodigo().trim())) && (item.getIdCupon() != pCupon.getIdCupon())) {
                throw new BusinessException("El nombre del cupón de pago ya está en uso");
            }
        }
        //fechaEmision
        if (pCupon.getFechaEmision() == null){
            throw new BusinessException("Fecha de emisión esta vacía");
        }
        if (pCupon.getFechaEmision().isBefore(LocalDate.now())){
            throw new BusinessException("Fecha de emisión no puede ser menor de hoy");
        }
        if (pCupon.getFechaEmision().isAfter(LocalDate.now())){
            throw new BusinessException("Fecha de emisión no puede ser mayor de hoy");
        }
        //fechaCaducidad
        if (pCupon.getFechaCaducidad() == null){
            throw new BusinessException("Fecha de caducidad esta vacía");
        }
        if (pCupon.getFechaCaducidad().isBefore(pCupon.getFechaEmision().plusDays(15))){
            throw new BusinessException("Fecha de caducidad no puede ser menor de 15 dias de la fecha de emisión");
        }
        if (pCupon.getFechaCaducidad().isAfter(pCupon.getFechaEmision().plusMonths(1))){
           throw new BusinessException("Fecha de caducidad no puede ser mayor de 1 mes de la fecha de emisión");}
        //cantidadMaxima
        if (pCupon.getCantidadMaxima()<=0){
            throw new BusinessException("Cantidad Máxima no puede ser menor o igual cero");
        }
        if (pCupon.getCantidadMaxima() > 150){
            throw new BusinessException("Cantidad Máxima no puede ser mayor a 150");
        }
        //cantidadDisponible
        if (pCupon.getCantidadDisponible()< 0){
            throw new BusinessException("Cantidad disponible no puede ser menor a cero");
        }
        if (pCupon.getCantidadDisponible() > pCupon.getCantidadMaxima()){
            throw new BusinessException("Cantidad disponible no puede ser mayor a la cantidad máxima ");
        }
        //porcentajeDescuento
        if (String.valueOf(pCupon.getPorcentajeDescuento()) == ""){
            throw new BusinessException("Porcentaje de descuento esta vacio");
        }
        if (pCupon.getPorcentajeDescuento() <= 0){
            throw new BusinessException("Porcentaje de descuento no debe ser menor o igual a 0");
        }
        if (pCupon.getPorcentajeDescuento() > 50){
            throw new BusinessException("Porcentaje de descuento no debe ser mayor o igual a 50");
        }
    }
}
