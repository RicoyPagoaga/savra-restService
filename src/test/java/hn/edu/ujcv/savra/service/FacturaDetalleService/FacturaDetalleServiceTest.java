package hn.edu.ujcv.savra.service.FacturaDetalleService;

import hn.edu.ujcv.savra.entity.Arqueo;
import hn.edu.ujcv.savra.entity.Empleado;
import hn.edu.ujcv.savra.entity.FacturaDetalle;
import hn.edu.ujcv.savra.entity.FacturaDetalleRecibo;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.FacturaDetalleRepository;
import hn.edu.ujcv.savra.repository.FacturaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class FacturaDetalleServiceTest {
    @Mock
    private FacturaDetalleRepository facturaRepository;
    @InjectMocks
    private FacturaDetalleService facturaDetalleService;

    private FacturaDetalle facturaDetalle;
    private FacturaDetalle facturaDetalleEnviado;
    private FacturaDetalleRecibo facturaDetalleRecibo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        facturaDetalle =new FacturaDetalle();
        facturaDetalleEnviado = new FacturaDetalle(1,2,3,5,2);
    }

    @Test
    void guardarFacturaDetalle() throws BusinessException {
        when(facturaRepository.save(any(FacturaDetalle.class))).thenReturn(facturaDetalle);
        assertNotNull(facturaDetalleService.guardarFacturaDetalle(facturaDetalleEnviado));

    }

    @Test
    void guardarFacturaDetalles() throws BusinessException {
        when(facturaRepository.saveAll(Arrays.asList(any(FacturaDetalle.class)))).thenReturn(Arrays.asList(facturaDetalle));
        assertNotNull(facturaDetalleService.guardarFacturaDetalles(Arrays.asList(facturaDetalleEnviado)));
    }

    @Test
    void obtenerFacturaDetalles() throws BusinessException {
        when(facturaRepository.findAll()).thenReturn(Arrays.asList(facturaDetalle));
        assertNotNull(facturaDetalleService.obtenerFacturaDetalles());
    }

    @Test
    void obtenerFacturaDetallesByIdFactura() throws BusinessException {
        when(facturaRepository.findById(anyLong())).thenReturn(Optional.of(facturaDetalle));
        assertNotNull(facturaDetalleService.obtenerFacturaDetallesByIdFactura(new Long(1)));
    }

    @Test
    void obtenerDetallesRecibo() throws BusinessException, NotFoundException {
        when(facturaRepository.getReciboDetalle(anyLong())).thenReturn(Arrays.asList(facturaDetalleRecibo));
        assertNotNull(facturaDetalleService.obtenerDetallesRecibo(1));
    }


}