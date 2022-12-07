package hn.edu.ujcv.savra.service.FacturaService;

import hn.edu.ujcv.savra.entity.Factura;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaService implements IFacturaService{
    @Autowired
    FacturaRepository repository;
    @Override
    public Factura guardarFactura(Factura pFactura) throws BusinessException {
        try{
            validarFactura(pFactura);
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
    void validarFactura(Factura pFactura){

    }
}
