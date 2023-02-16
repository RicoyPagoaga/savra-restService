package hn.edu.ujcv.savra.service.MarcaService;

import hn.edu.ujcv.savra.entity.Cargo;
import hn.edu.ujcv.savra.entity.Marca;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.MarcaRepository;
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

class MarcaServiceTest {
    @Mock
    private MarcaRepository marcaRepository;
    @InjectMocks
    private MarcaService marcaService;
    private Marca marca;
    private Marca marcaEnviada;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        marca = new Marca();
        marcaEnviada = new Marca(1,"Datsun");
    }

    @Test
    void saveMarca() throws BusinessException {
        when(marcaRepository.save(any(Marca.class))).thenReturn(marca);
        assertNotNull(marcaService.saveMarca(marcaEnviada));
    }

    @Test
    void saveMarcas() throws BusinessException {
        when(marcaRepository.saveAll(Arrays.asList(any(Marca.class)))).thenReturn(Arrays.asList(marca));
        assertNotNull(marcaService.saveMarcas(Arrays.asList(marcaEnviada)));
    }

    @Test
    void getMarcas() throws BusinessException {
        when(marcaRepository.findAll()).thenReturn(Arrays.asList(marca));
        assertNotNull(marcaService.getMarcas());
    }

    @Test
    void getMarcaById() throws BusinessException, NotFoundException {
        when(marcaRepository.findById(anyLong())).thenReturn(Optional.of(marca));
        assertNotNull(marcaService.getMarcaById(new Long(1)));
    }

    @Test
    void getMarcaByNombre() throws BusinessException, NotFoundException {
        when(marcaRepository.findFirstByNombre(anyString())).thenReturn(Optional.of(marca));
        assertNotNull(marcaService.getMarcaByNombre(marcaEnviada.getNombre()));
    }

    @Test
    void deleteMarca() throws BusinessException, NotFoundException {
        getMarcaById();
        marcaService.deleteMarca(new Long(1));
    }

    @Test
    void updateMarca() throws BusinessException, NotFoundException {
        getMarcaById();
        when(marcaRepository.save(any(Marca.class))).thenReturn(marca);
        assertNotNull(marcaService.updateMarca(marcaEnviada));
    }
}