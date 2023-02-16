package hn.edu.ujcv.savra.service.TransmisionService;

import hn.edu.ujcv.savra.entity.Arqueo;
import hn.edu.ujcv.savra.entity.Transmision;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.TransmisionRepository;
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

class TransmisionServiceTest {
    @Mock
    private TransmisionRepository transmisionRepository;
    @InjectMocks
    private TransmisionService transmisionService;

    private Transmision transmision;
    private Transmision transmisionEnviada;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        transmision = new Transmision();
        transmisionEnviada = new Transmision(1,"Automatica");
    }

    @Test
    void saveTransmision() throws BusinessException, SQLException {
        when(transmisionRepository.save(any(Transmision.class))).thenReturn(transmision);
        assertNotNull(transmisionService.saveTransmision(transmisionEnviada));

    }

    @Test
    void getTransmisiones() throws BusinessException {
        when(transmisionRepository.findAll()).thenReturn(Arrays.asList(transmision));
        assertNotNull(transmisionService.getTransmisiones());
    }

    @Test
    void getTransmisionById() throws BusinessException, NotFoundException {
        when(transmisionRepository.findById(anyLong())).thenReturn(Optional.of(transmision));
        assertNull(transmisionService.getTransmisionById(new Long(1)));
    }

    @Test
    void getTransmisionByNombre() throws BusinessException, NotFoundException {
        when(transmisionRepository.findByNombre(anyString())).thenReturn(Optional.of(transmision));
        assertNull(transmisionService.getTransmisionByNombre(transmisionEnviada.getNombre()));
    }

    @Test
    void deteleTransmision() throws BusinessException, NotFoundException {
        when(transmisionRepository.findById(anyLong())).thenReturn(Optional.of(transmision));
        transmisionService.deteleTransmision(new Long(1));
    }

    @Test
    void updateTransmision() throws BusinessException, NotFoundException {
        when(transmisionRepository.findById(anyLong())).thenReturn(Optional.of(transmision));
        when(transmisionRepository.save(any(Transmision.class))).thenReturn(transmision);
        assertNotNull(transmisionService.updateTransmision(transmisionEnviada));
    }
}