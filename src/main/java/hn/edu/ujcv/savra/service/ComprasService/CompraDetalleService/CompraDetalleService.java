package hn.edu.ujcv.savra.service.ComprasService.CompraDetalleService;

import hn.edu.ujcv.savra.entity.Compra.Compra;
import hn.edu.ujcv.savra.entity.Compra.CompraDetalle;
import hn.edu.ujcv.savra.entity.Repuesto.Repuesto;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.Compra.CompraDetalleRepository;
import hn.edu.ujcv.savra.repository.Compra.CompraRepository;
import hn.edu.ujcv.savra.repository.Repuesto.RepuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompraDetalleService implements ICompraDetalleService {

    @Autowired
    private CompraDetalleRepository repository;

    @Override
    public CompraDetalle saveCompraDetalle(CompraDetalle compraDetalle) throws BusinessException {
        try {
            compraDetalle.setIdCompra(getCompraID());
            validarCompraDetalle(compraDetalle);
            return repository.save(compraDetalle);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<CompraDetalle> saveComprasDetalle(List<CompraDetalle> comprasDetalle) throws BusinessException {
        try {
            for (CompraDetalle item: comprasDetalle) {
                item.setIdCompra(getCompraID());
                validarCompraDetalle(item);
            }
            return repository.saveAll(comprasDetalle);
        } catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<CompraDetalle> updateComprasDetalle(List<CompraDetalle> comprasDetalle) throws BusinessException {
        try {
            //List<CompraDetalle> lista = repository.findAll();
            for (CompraDetalle item: comprasDetalle) {
                validarCompraDetalle(item);
            }
            return repository.saveAll(comprasDetalle);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<CompraDetalle> getComprasDetalle() throws BusinessException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public CompraDetalle getCompraDetalleById(long id) throws BusinessException, NotFoundException {
        Optional<CompraDetalle> opt=null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el detalle de compra: " + id);
        }
        return opt.get();
    }

    @Override
    public void deleteCompraDetalle(long id) throws BusinessException, NotFoundException {
        Optional<CompraDetalle> opt = null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el detalle de compra: " + id);
        } else {
            try {
                repository.deleteById(id);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }

    @Override
    public CompraDetalle updateCompraDetalle(CompraDetalle compraDetalle) throws BusinessException, NotFoundException {
        Optional<CompraDetalle> opt = null;
        try {
            if (compraDetalle.getIdCompraDetalle() <= 0) {
                throw new BusinessException("Id de Detalle de Compra inválido");
            }
            opt = repository.findById(compraDetalle.getIdCompraDetalle());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el detalle de compra: " + compraDetalle.getIdCompraDetalle());
        } else {
            try {
                validarCompraDetalle(compraDetalle);
                CompraDetalle compraDetalleExistente = new CompraDetalle(
                        compraDetalle.getIdCompraDetalle(),
                        compraDetalle.getIdCompra(),
                        compraDetalle.getIdRepuesto(),
                        compraDetalle.getCantidad()
                );
                return repository.save(compraDetalleExistente);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void validarCompraDetalle(CompraDetalle compraDetalle) throws BusinessException {
        //id compra
        if (compraDetalle.getIdCompra() <= 0) {
            throw new BusinessException("Indique Compra válida");
        }
        //id repuesto
        if (compraDetalle.getIdRepuesto() <= 0) {
            throw new BusinessException("Indique repuesto válido");
        }
        if(validarRepuesto(compraDetalle)) {
            throw new BusinessException("El repuesto ya se encuentra en el detalle de compra");
        }
        //cantidad
        if (compraDetalle.getCantidad() <= 0) {
            throw new BusinessException("La cantidad no puede ser menor o igual a cero");
        }
        int valor = validarStockMaximo(compraDetalle.getIdRepuesto());
        if (compraDetalle.getCantidad() > valor) {
            throw new BusinessException("No puede comprar una cantidad mayor a la del stock máximo del repuesto "
                    + compraDetalle.getIdRepuesto());
        }
    }

    @Autowired
    private RepuestoRepository repuestoRepository;
    private int validarStockMaximo(long id) {
        int condicion=0;
        List<Repuesto> lista = repuestoRepository.findAll();
        for (Repuesto item : lista) {
            if (item.getIdRepuesto() == id ) {
                condicion = item.getStockMaximo()- item.getStockActual();
                break;
            }
        }
        return condicion;
    }

    private boolean validarRepuesto(CompraDetalle compraDetalle) {
        boolean condicion=false;
        List<CompraDetalle> list = repository.findAll();
        for (CompraDetalle item : list) {
            if ((item.getIdCompra() == compraDetalle.getIdCompra()
                    && item.getIdRepuesto() == compraDetalle.getIdRepuesto())
                    && item.getIdCompraDetalle() != compraDetalle.getIdCompraDetalle()) {
                condicion=true;
                break;
            }
        }
        return condicion;
    }

    //get compra id
    @Autowired
    private CompraRepository compraRepository;
    private long getCompraID() {
        ArrayList<Compra> compras = (ArrayList<Compra>) compraRepository.findAll();
        int x = compras.size() - 1;
        return compras.get(x).getIdCompra();
    }
}
