package hn.edu.ujcv.savra.service.ComprasService.CompraDetalleService;

import hn.edu.ujcv.savra.entity.Arqueo;
import hn.edu.ujcv.savra.entity.Compra.Compra;
import hn.edu.ujcv.savra.entity.Compra.CompraDetalle;
import hn.edu.ujcv.savra.entity.Repuesto.Repuesto;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.Compra.CompraDetalleRepository;
import hn.edu.ujcv.savra.repository.Compra.CompraRepository;
import hn.edu.ujcv.savra.repository.Repuesto.RepuestoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class CompraDetalleServiceTest {

    @Mock
    private CompraDetalleRepository compraDetalleRepository;
    @Mock
    private RepuestoRepository repuestoRepository;
    @Mock
    private CompraRepository compraRepository;

    @InjectMocks
    private CompraDetalleService compraDetalleService;

    private CompraDetalle compraDetalle;
    private CompraDetalle compraDetalleEnviado;
    private Repuesto repuesto;
    private Compra compra;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        compraDetalle = new CompraDetalle();
        compraDetalleEnviado = new CompraDetalle(1,2,3,5,25.63);
        repuesto = new Repuesto(3,"Bujia",2000,2010,1,
                500,80,800,1,1,1,1);
        compra = new Compra(2,2, LocalDate.now(),LocalDate.now()
                ,LocalDate.now().plusDays(6),"12345678941");
    }

    @Test
    void saveCompraDetalle() throws BusinessException {
        when(repuestoRepository.findAll()).thenReturn(Arrays.asList(repuesto));
        when(compraDetalleRepository.findAll()).thenReturn(Arrays.asList(compraDetalle));
        when(compraRepository.findAll()).thenReturn(Arrays.asList(compra));
        when(compraDetalleRepository.save(any(CompraDetalle.class))).thenReturn(compraDetalle);
        assertNotNull(compraDetalleService.saveCompraDetalle(compraDetalleEnviado));
    }

    @Test
    void saveComprasDetalle() throws BusinessException {
        when(repuestoRepository.findAll()).thenReturn(Arrays.asList(repuesto));
        when(compraDetalleRepository.findAll()).thenReturn(Arrays.asList(compraDetalle));
        when(compraRepository.findAll()).thenReturn(Arrays.asList(compra));
        when(compraDetalleRepository.saveAll(Arrays.asList(any(CompraDetalle.class)))).thenReturn(Arrays.asList(compraDetalle));
        assertNotNull(compraDetalleService.saveComprasDetalle(Arrays.asList(compraDetalleEnviado)));
    }


    @Test
    void getComprasDetalle() throws BusinessException {
        when(compraDetalleRepository.findAll()).thenReturn(Arrays.asList(compraDetalle));
        assertNotNull(compraDetalleService.getComprasDetalle());
    }

    @Test
    void getCompraDetalleById() throws BusinessException, NotFoundException {
        when(compraDetalleRepository.findById(anyLong())).thenReturn(Optional.of(compraDetalle));
        assertNotNull(compraDetalleService.getCompraDetalleById(new Long(1)));
    }

    @Test
    void deleteCompraDetalle() throws BusinessException, NotFoundException {
        getCompraDetalleById();
        compraDetalleService.deleteCompraDetalle(new Long(1));
    }

    @Test
    void updateCompraDetalle() throws BusinessException, NotFoundException {
        getCompraDetalleById();
        when(repuestoRepository.findAll()).thenReturn(Arrays.asList(repuesto));
        when(compraDetalleRepository.save(any(CompraDetalle.class))).thenReturn(compraDetalle);
        assertNotNull(compraDetalleService.updateCompraDetalle(compraDetalleEnviado));
    }
}