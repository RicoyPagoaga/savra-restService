package hn.edu.ujcv.savra.service.TipoEntregaService;

import hn.edu.ujcv.savra.entity.Arqueo;
import hn.edu.ujcv.savra.entity.TipoEntrega;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.TipoDocumentoRepository;
import hn.edu.ujcv.savra.repository.TipoEntregaRepository;
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

class TipoEntregaServiceTest {

    @Mock
    private TipoEntregaRepository tipoEntregaRepository;
    @InjectMocks
    private TipoEntregaService tipoEntregaService;

    private TipoEntrega tipoEntrega;
    private TipoEntrega tipoEntregaEnviado;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        tipoEntrega = new TipoEntrega();
        tipoEntregaEnviado = new TipoEntrega(1,"ENVIO","entrega por envio a domicilio");
    }

    @Test
    void saveTipoEntrega() throws BusinessException {
        when(tipoEntregaRepository.save(any(TipoEntrega.class))).thenReturn(tipoEntrega);
        assertNotNull(tipoEntregaService.saveTipoEntrega(tipoEntregaEnviado));
    }

    @Test
    void saveTiposEntrega() throws BusinessException {
        when(tipoEntregaRepository.saveAll(Arrays.asList(any(TipoEntrega.class)))).thenReturn(Arrays.asList(tipoEntrega));
        assertNotNull(tipoEntregaService.saveTiposEntrega(Arrays.asList(tipoEntregaEnviado)));
    }

    @Test
    void getTiposEntrega() throws BusinessException {
        when(tipoEntregaRepository.findAll()).thenReturn(Arrays.asList(tipoEntrega));
        assertNotNull(tipoEntregaService.getTiposEntrega());
    }

    @Test
    void getTipoEntregaById() throws BusinessException, NotFoundException {
        when(tipoEntregaRepository.findById(anyLong())).thenReturn(Optional.of(tipoEntrega));
        assertNotNull(tipoEntregaService.getTipoEntregaById(new Long(1)));
    }

    @Test
    void getTipoEntregaByNombre() throws BusinessException, NotFoundException {
        when(tipoEntregaRepository.findFirstByNombre(anyString())).thenReturn(Optional.of(tipoEntrega));
        assertNotNull(tipoEntregaService.getTipoEntregaByNombre(tipoEntregaEnviado.getNombre()));
    }

    @Test
    void deleteTipoEntrega() throws BusinessException, NotFoundException {
        getTipoEntregaById();
        tipoEntregaService.deleteTipoEntrega(new Long(1));
    }

    @Test
    void updateTipoEntrega() throws BusinessException, NotFoundException {
        getTipoEntregaById();
        when(tipoEntregaRepository.save(any(TipoEntrega.class))).thenReturn(tipoEntrega);
        assertNotNull(tipoEntregaService.updateTipoEntrega(tipoEntregaEnviado));
    }
}