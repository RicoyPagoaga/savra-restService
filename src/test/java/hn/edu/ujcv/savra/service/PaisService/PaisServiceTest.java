package hn.edu.ujcv.savra.service.PaisService;

import hn.edu.ujcv.savra.entity.Cargo;
import hn.edu.ujcv.savra.entity.Pais;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.PaisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class PaisServiceTest {
    @Mock
    private PaisRepository paisRepository;
    @InjectMocks
    private PaisService paisService;
    private Pais pais;
    private Pais paisenviado;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        pais = new Pais();
        paisenviado = new Pais(1,"HN","Honduras","504");
    }

    @Test
    void savePais() throws BusinessException, SQLException {
        when(paisRepository.save(any(Pais.class))).thenReturn(pais);
        assertNotNull(paisService.savePais(paisenviado  ));
    }

    @Test
    void getPaises() throws BusinessException {
        when(paisRepository.findAll()).thenReturn(Arrays.asList(pais));
        assertNotNull(paisService.getPaises());
    }

    @Test
    void getPaisById() throws BusinessException, NotFoundException {
        when(paisRepository.findById(anyLong())).thenReturn(Optional.of(pais));
        assertNotNull(paisService.getPaisById(new Long(1)));
    }

    //@Test
    //void getPaisByNombre() {
       // when(paisRepository.findPByNombre(anyString())).thenReturn(Optional.of(pais));
        //assertNotNull(paisService.getPaisByNombre(paisenviado.getNombre()));
    //}

    @Test
    void detelePais() throws BusinessException, NotFoundException {
        getPaisById();
        paisService.detelePais(new Long(1));
    }

    @Test
    void updatePais() throws BusinessException, NotFoundException {
        getPaisById();
        when(paisRepository.save(any(Pais.class))).thenReturn(pais);
        assertNotNull(paisService.updatePais(paisenviado));
    }
}
