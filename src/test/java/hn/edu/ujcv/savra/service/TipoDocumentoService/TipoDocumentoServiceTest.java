package hn.edu.ujcv.savra.service.TipoDocumentoService;

import hn.edu.ujcv.savra.entity.TipoDocumento;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.TipoDocumentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class TipoDocumentoServiceTest {

    @Mock
    private TipoDocumentoRepository TipoDocumentorepository;
    @InjectMocks
    private TipoDocumentoService tipoDocumentoService;

    private TipoDocumento tipoDocumento;
    private TipoDocumento tipoDocumentoEsperado;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        tipoDocumento = new TipoDocumento();
        tipoDocumentoEsperado = new TipoDocumento();
        tipoDocumento.setNombreDocumento("NIF");
        tipoDocumentoEsperado.setNombreDocumento("NIF");
    }

    @Test
    void saveTipoDocumento() throws BusinessException {
        when(TipoDocumentorepository.save(any(TipoDocumento.class))).thenReturn(tipoDocumento);
        assertNotNull(tipoDocumentoService.saveTipoDocumento(tipoDocumentoEsperado));
    }

    @Test
    void saveTipoDocumentos() throws BusinessException {
        when(TipoDocumentorepository.saveAll(Arrays.asList(any(TipoDocumento.class)))).thenReturn(Arrays.asList(tipoDocumento));
        assertNotNull(tipoDocumentoService.saveTipoDocumentos(Arrays.asList(tipoDocumentoEsperado)));
    }

    @Test
    void getTipoDocumentos() throws BusinessException {
        when(TipoDocumentorepository.findAll()).thenReturn(Arrays.asList(tipoDocumento));
        assertNotNull(tipoDocumentoService.getTipoDocumentos());
    }

    @Test
    void getTipoDocumentoById() throws BusinessException, NotFoundException {
        when(TipoDocumentorepository.findById(anyLong())).thenReturn(Optional.of(tipoDocumento));
        assertNotNull(tipoDocumentoService.getTipoDocumentoById(new Long(1)));
    }

    @Test
    void getTipoDocumentoByNombre() throws BusinessException, NotFoundException {
        when(TipoDocumentorepository.findByNombreDocumento(anyString())).thenReturn(Optional.of(tipoDocumento));
        assertNotNull(tipoDocumentoService.getTipoDocumentoByNombre(tipoDocumentoEsperado.getNombreDocumento()));
    }

    @Test
    void deleteTipoDocumento() throws BusinessException, NotFoundException {
        getTipoDocumentoById();
        tipoDocumentoService.deleteTipoDocumento(new Long(1));
    }

    @Test
    void updateTipoDocumento() throws BusinessException, NotFoundException {
        getTipoDocumentoById();
        when(TipoDocumentorepository.save(any(TipoDocumento.class))).thenReturn(tipoDocumento);
        assertNotNull(tipoDocumentoService.updateTipoDocumento(tipoDocumentoEsperado));
    }
}