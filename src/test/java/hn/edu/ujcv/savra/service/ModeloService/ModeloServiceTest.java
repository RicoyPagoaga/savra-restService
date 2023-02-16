package hn.edu.ujcv.savra.service.ModeloService;

import hn.edu.ujcv.savra.entity.Cargo;
import hn.edu.ujcv.savra.entity.Marca;
import hn.edu.ujcv.savra.entity.Modelo;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.ModeloRepository;
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

class ModeloServiceTest {
    @Mock
    private ModeloRepository modeloRepository;
    @InjectMocks
    private ModeloService modeloService;
    private Modelo modelo;
    private Modelo setModelo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        modelo = new Modelo();
        Marca marca = new Marca();
        marca.setIdMarca(2);
        setModelo = new Modelo(1,"corolla",marca);
    }

    @Test
    void saveModelo() throws BusinessException {
        when(modeloRepository.save(any(Modelo.class))).thenReturn(modelo);
        assertNotNull(modeloService.saveModelo(setModelo));
    }

    @Test
    void saveModelos() throws BusinessException {
        when(modeloRepository.saveAll(Arrays.asList(any(Modelo.class)))).thenReturn(Arrays.asList(modelo));
        assertNotNull(modeloService.saveModelos(Arrays.asList(setModelo)));
    }

    @Test
    void getModelos() throws BusinessException {
        when(modeloRepository.findAll()).thenReturn(Arrays.asList(modelo));
        assertNotNull(modeloService.getModelos());
    }

    @Test
    void getModeloById() throws BusinessException, NotFoundException {
        when(modeloRepository.findById(anyLong())).thenReturn(Optional.of(modelo));
        assertNotNull(modeloService.getModeloById(new Long(1)));
    }

    @Test
    void getModeloByNombre() throws BusinessException, NotFoundException {
        when(modeloRepository.findFirstByNombre(anyString())).thenReturn(Optional.of(modelo));
        assertNotNull(modeloService.getModeloByNombre(setModelo.getNombre()));
    }

    @Test
    void deleteModelo() throws BusinessException, NotFoundException {
        getModeloById();
        modeloService.deleteModelo(new Long(1));
    }

    @Test
    void updateModelo() throws BusinessException, NotFoundException {
        getModeloById();
        when(modeloRepository.save(any(Modelo.class))).thenReturn(modelo);
        assertNotNull(modeloService.updateModelo(setModelo));
    }
}