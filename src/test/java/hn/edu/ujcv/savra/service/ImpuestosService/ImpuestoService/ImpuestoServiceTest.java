package hn.edu.ujcv.savra.service.ImpuestosService.ImpuestoService;

import hn.edu.ujcv.savra.entity.Cargo;
import hn.edu.ujcv.savra.entity.Impuesto.Impuesto;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.Impuesto.ImpuestoRepository;
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

class ImpuestoServiceTest {
    @Mock
    private ImpuestoRepository impuestoRepository;
    @InjectMocks
    private ImpuestoService impuestoService;
    private Impuesto impuesto;
    private Impuesto impuestoEnviado;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        impuesto = new Impuesto();
        impuestoEnviado = new Impuesto(1,"isv","impuesto sobre la venta",1);
    }

    @Test
    void saveImpuesto() throws BusinessException {
        when(impuestoRepository.save(any(Impuesto.class))).thenReturn(impuesto);
        assertNotNull(impuestoService.saveImpuesto(impuestoEnviado,30,false));
    }

    @Test
    void saveImpuestos() throws BusinessException {
        when(impuestoRepository.saveAll(Arrays.asList(any(Impuesto.class)))).thenReturn(Arrays.asList(impuesto));
        assertNotNull(impuestoService.saveImpuestos(Arrays.asList(impuestoEnviado)));
    }

    @Test
    void getImpuestos() throws BusinessException {
        when(impuestoRepository.findAll()).thenReturn(Arrays.asList(impuesto));
        assertNotNull(impuestoService.getImpuestos());
    }

    @Test
    void getImpuestoById() throws BusinessException, NotFoundException {
        when(impuestoRepository.findById(anyLong())).thenReturn(Optional.of(impuesto));
        assertNotNull(impuestoService.getImpuestoById(new Long(1)));
    }

    @Test
    void getImpuestoByNombre() throws BusinessException, NotFoundException {
        when(impuestoRepository.findFirstByNombre(anyString())).thenReturn(Optional.of(impuesto));
        assertNotNull(impuestoService.getImpuestoByNombre(impuestoEnviado.getNombre()));
    }

    @Test
    void deleteImpuesto() throws BusinessException, NotFoundException {
       getImpuestoById();
       impuestoService.deleteImpuesto(new Long(1));
    }

    @Test
    void updateImpuesto() throws BusinessException, NotFoundException {
        getImpuestoById();
        when(impuestoRepository.save(any(Impuesto.class))).thenReturn(impuesto);
        assertNotNull(impuestoService.updateImpuesto(impuestoEnviado,30,false));
    }
}