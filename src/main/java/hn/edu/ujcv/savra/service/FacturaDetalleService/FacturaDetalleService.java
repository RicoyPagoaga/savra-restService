package hn.edu.ujcv.savra.service.FacturaDetalleService;

import hn.edu.ujcv.savra.entity.FacturaDetalle;
import hn.edu.ujcv.savra.entity.FacturaDetalleRecibo;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.FacturaDetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaDetalleService implements IFacturaDetalleService{
    @Autowired
    FacturaDetalleRepository repository;
    @Override
    public FacturaDetalle guardarFacturaDetalle(FacturaDetalle pFacturaDetalle) throws BusinessException {
        try{
            validarFacturaDetalle(pFacturaDetalle);
            return repository.save(pFacturaDetalle);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<FacturaDetalle> guardarFacturaDetalles(List<FacturaDetalle> pFacturaDetalles) throws BusinessException {
        try{
            for (FacturaDetalle item: pFacturaDetalles) {
                validarFacturaDetalle(item);
            }
            return repository.saveAll(pFacturaDetalles);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<FacturaDetalle> obtenerFacturaDetalles() throws BusinessException {
        try{
            return repository.findAll();
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<FacturaDetalle> obtenerFacturaDetallesByIdFactura(long idFactura) throws BusinessException {
        //Optional<List<FacturaDetalle>> opt = null;
        try {
            return repository.findByIdFactura(idFactura);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<FacturaDetalleRecibo> obtenerDetallesRecibo(long idFactura) throws BusinessException, NotFoundException {
        try{
            return repository.getReciboDetalle(idFactura);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    void validarFacturaDetalle(FacturaDetalle pFactura){

    }
}
