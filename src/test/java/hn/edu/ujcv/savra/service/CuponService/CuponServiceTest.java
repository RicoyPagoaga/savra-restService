package hn.edu.ujcv.savra.service.CuponService;

import hn.edu.ujcv.savra.entity.Cargo;
import hn.edu.ujcv.savra.entity.Cupon;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.CuponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class CuponServiceTest {
    @Mock
    private CuponRepository cuponRepository;
    @InjectMocks
    private CuponService cuponService;
    private Cupon cupon;
    private Cupon cuponEnviado;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        cupon = new Cupon();
        cuponEnviado = new Cupon(1,"NAVIDAD", LocalDate.now(),LocalDate.now().plusMonths(1),20,10,1,10);
    }

    @Test
    void saveCupon() throws BusinessException {
        when(cuponRepository.save(any(Cupon.class))).thenReturn(cupon);
        assertNotNull(cuponService.saveCupon(cuponEnviado));
    }

    @Test
    void actualizarCupon() throws BusinessException, NotFoundException {
        when(cuponRepository.findById(anyLong())).thenReturn(Optional.of(cupon));
        when(cuponRepository.save(any(Cupon.class))).thenReturn(cupon);
        assertNotNull(cuponService.actualizarCupon(cuponEnviado));
    }

    @Test
    void obtenerCupones() throws BusinessException {
        when(cuponRepository.findAll()).thenReturn(Arrays.asList(cupon));
        assertNotNull(cuponService.obtenerCupones());
    }

    @Test
    void eliminarCupon() throws BusinessException, NotFoundException {
        when(cuponRepository.findById(anyLong())).thenReturn(Optional.of(cupon));
        cuponService.eliminarCupon(new Long(1));
    }

    @Test
    void getCuponByCodigo() throws BusinessException, NotFoundException {
        when(cuponRepository.findByCodigo(anyString())).thenReturn(Optional.of(cupon));
        assertNotNull(cuponService.getCuponByCodigo(cuponEnviado.getCodigo()));
    }

    @Test
    void activarDesactivarcupon() throws BusinessException, NotFoundException {
        when(cuponRepository.findById(anyLong())).thenReturn(Optional.of(cupon));
        cuponService.activarDesactivarcupon(0,cuponEnviado.getIdCupon());

    }
}