package hn.edu.ujcv.savra.service.MetodoPagoService;

import hn.edu.ujcv.savra.entity.Cargo;
import hn.edu.ujcv.savra.entity.MetodoPago;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.MetodoPagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class MetodoPagoServiceTest {
    @Mock
    private MetodoPagoRepository metodoPagoRepository;
    @InjectMocks
    private MetodoPagoService metodoPagoService;
    private MetodoPago metodoPago;
    private MetodoPago setpago;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        metodoPago = new MetodoPago();
        setpago = new MetodoPago(1,"efectivo");

    }

    @Test
    void saveMetodoPago() throws BusinessException {
        when(metodoPagoRepository.save(any(MetodoPago.class))).thenReturn(metodoPago);
        assertNotNull(metodoPagoService.saveMetodoPago(setpago));
    }

    @Test
    void saveMetodosPago() throws BusinessException {
        when(metodoPagoRepository.saveAll(Arrays.asList(any(MetodoPago.class)))).thenReturn(Arrays.asList(metodoPago));
        assertNotNull(metodoPagoService.saveMetodosPago(Arrays.asList(setpago)));
    }

    @Test
    void getMetodosPago() throws BusinessException {
        when(metodoPagoRepository.findAll()).thenReturn(Arrays.asList(metodoPago));
        assertNotNull(metodoPagoService.getMetodosPago());
    }

    @Test
    void getMetodoPagoById() throws BusinessException, NotFoundException {
        when(metodoPagoRepository.findById(anyLong())).thenReturn(Optional.of(metodoPago));
        assertNotNull(metodoPagoService.getMetodoPagoById(new Long(1)));
    }

    @Test
    void getMetodoPagoByNombre() throws BusinessException, NotFoundException {
        when(metodoPagoRepository.findFirstByNombre(anyString())).thenReturn(Optional.of(metodoPago));
        assertNotNull(metodoPagoService.getMetodoPagoByNombre(setpago.getNombre()));
    }

    @Test
    void deleteMetodoPago() throws BusinessException, NotFoundException {
        getMetodoPagoById();
        metodoPagoService.deleteMetodoPago(new Long(1));
    }

    @Test
    void updateMetodoPago() throws BusinessException, NotFoundException {
        getMetodoPagoById();
        when(metodoPagoRepository.save(any(MetodoPago.class))).thenReturn(metodoPago);
        assertNotNull(metodoPagoService.updateMetodoPago(setpago));
    }
}
