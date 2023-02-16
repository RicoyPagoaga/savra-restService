package hn.edu.ujcv.savra.service.ImpuestosService.ImpuestoHistoricoService;

import hn.edu.ujcv.savra.entity.Arqueo;
import hn.edu.ujcv.savra.entity.Impuesto.Impuesto;
import hn.edu.ujcv.savra.entity.Impuesto.ImpuestoHistorico.ImpuestoHistorico;
import hn.edu.ujcv.savra.entity.Impuesto.ImpuestoHistorico.ImpuestoHistoricoPK;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.Impuesto.ImpuestoHistoricoRepository;
import hn.edu.ujcv.savra.repository.Impuesto.ImpuestoRepository;
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
import static org.mockito.Mockito.when;

class ImpuestoHistoricoServiceTest {
    @Mock
    private ImpuestoHistoricoRepository repository;
    @Mock
    private ImpuestoRepository impuestoRepository;
    @InjectMocks
    private ImpuestoHistoricoService service;

    private ImpuestoHistoricoPK pk;
    private ImpuestoHistorico impuestoHistorico;
    private ImpuestoHistorico impuestoHistoricoEnviado;
    private Impuesto impuesto;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        impuestoHistorico = new ImpuestoHistorico();
        impuestoHistoricoEnviado = new ImpuestoHistorico(1, LocalDate.now(),null,12);
        impuesto = new Impuesto(1,"isv","impuesto sobre la venta",1);
    }

    @Test
    void saveImpuestoHistorico() throws BusinessException {
        when(impuestoRepository.findAll()).thenReturn(Arrays.asList(impuesto));
        when(repository.save(any(ImpuestoHistorico.class))).thenReturn(impuestoHistorico);
        assertNotNull(service.saveImpuestoHistorico(impuestoHistoricoEnviado));
    }

    @Test
    void getImpuestosHistorico() throws BusinessException {
        when(repository.findAll()).thenReturn(Arrays.asList(impuestoHistorico));
        assertNotNull(service.getImpuestosHistorico());
    }

    @Test
    void updateImpuestoHistorico() throws BusinessException, NotFoundException {
        pk = new ImpuestoHistoricoPK(1,LocalDate.now());
        when(impuestoRepository.findAll()).thenReturn(Arrays.asList(impuesto));
        when(repository.findById(pk)).thenReturn(Optional.ofNullable(impuestoHistoricoEnviado));
        when(repository.save(any(ImpuestoHistorico.class))).thenReturn(impuestoHistorico);
        assertNotNull(service.updateImpuestoHistorico(impuestoHistoricoEnviado));
    }
}