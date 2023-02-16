package hn.edu.ujcv.savra.service.ArqueoService;

import hn.edu.ujcv.savra.entity.Arqueo;
import hn.edu.ujcv.savra.entity.Empleado;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.ArqueoRepository;
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

class ArqueoServiceTest {

    @Mock
    private ArqueoRepository arqueoRepository;

    @InjectMocks
    private ArqueoService arqueoService;

    private Arqueo arqueo;
    private Arqueo arqueoEnviado;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        arqueo = new Arqueo();
        arqueoEnviado = new Arqueo(7,LocalDate.now(),new Empleado(),2578,"N/A");
    }

    @Test
    void saveArqueo() throws BusinessException {
        when(arqueoRepository.save(any(Arqueo.class))).thenReturn(arqueo);
        assertNotNull(arqueoService.saveArqueo(arqueoEnviado));
    }

    @Test
    void saveArqueos() throws BusinessException {
        when(arqueoRepository.saveAll(Arrays.asList(any(Arqueo.class)))).thenReturn(Arrays.asList(arqueo));
        assertNotNull(arqueoService.saveArqueos(Arrays.asList(arqueoEnviado)));
    }

    @Test
    void getArqueos() throws BusinessException {
        when(arqueoRepository.findAll()).thenReturn(Arrays.asList(arqueo));
        assertNotNull(arqueoService.getArqueos());
    }

    @Test
    void getArqueoById() throws BusinessException, NotFoundException {
        when(arqueoRepository.findById(anyLong())).thenReturn(Optional.of(arqueo));
        assertNotNull(arqueoService.getArqueoById(new Long(1)));
    }

    @Test
    void getArqueoByObservacion() throws BusinessException, NotFoundException {
        when(arqueoRepository.findArqueoByObservacion(anyString())).thenReturn(Optional.of(arqueo));
        assertNotNull(arqueoService.getArqueoByObservacion(arqueoEnviado.getObservacion()));
    }

    @Test
    void deleteArqueo() throws BusinessException, NotFoundException {
        getArqueoById();
        arqueoService.deleteArqueo(new Long(1));
    }

    @Test
    void udateArqueo() throws BusinessException, NotFoundException {
        getArqueoById();
        when(arqueoRepository.save(any(Arqueo.class))).thenReturn(arqueo);
        assertNotNull(arqueoService.udateArqueo(arqueoEnviado));
    }
}