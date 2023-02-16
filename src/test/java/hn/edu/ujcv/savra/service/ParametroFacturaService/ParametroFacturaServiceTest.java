package hn.edu.ujcv.savra.service.ParametroFacturaService;

import hn.edu.ujcv.savra.entity.Arqueo;
import hn.edu.ujcv.savra.entity.ParametroFactura;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.ParametroFacturaRepository;
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

class ParametroFacturaServiceTest {
    @Mock
    private ParametroFacturaRepository parametroFacturaRepository;

    @InjectMocks
    private ParametroFacturaService parametroFacturaService;

    private ParametroFactura parametroFactura;
    private ParametroFactura parametroFacturaEnviado;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        parametroFactura = new ParametroFactura();
        parametroFacturaEnviado = new ParametroFactura(1,"C1D1E9-342413-FC4180-82F25E-C52983-BA","020-001-01-01000000"
                ,"020-001-01-01060001", LocalDate.now().plusMonths(5),LocalDate.now(),15400);
    }

    @Test
    void saveParametro() throws BusinessException {
        when(parametroFacturaRepository.save(any(ParametroFactura.class))).thenReturn(parametroFactura);
        assertNotNull(parametroFacturaService.saveParametro(parametroFacturaEnviado));
    }

    @Test
    void updateParametro() throws BusinessException, NotFoundException {
        when(parametroFacturaRepository.findById(anyLong())).thenReturn(Optional.ofNullable(parametroFactura));
        when(parametroFacturaRepository.save(any(ParametroFactura.class))).thenReturn(parametroFactura);
        assertNotNull(parametroFacturaService.updateParametro(parametroFacturaEnviado));
    }

    @Test
    void getParametrosFactura() throws BusinessException {
        when(parametroFacturaRepository.findAll()).thenReturn(Arrays.asList(parametroFactura));
        assertNotNull(parametroFacturaService.getParametrosFactura());
    }

    @Test
    void deleteParametro() throws BusinessException, NotFoundException {
        when(parametroFacturaRepository.findById(anyLong())).thenReturn(Optional.ofNullable(parametroFactura));
        parametroFacturaService.deleteParametro(new Long(1));
    }

    @Test
    void getParametroByCai() throws BusinessException, NotFoundException {
        when(parametroFacturaRepository.findFirstByCai(anyString())).thenReturn(Optional.of(parametroFactura));
        assertNotNull(parametroFacturaService.getParametroByCai(parametroFacturaEnviado.getCai()));
    }

//    @Test
//    void validarParametro() {
//    }
}