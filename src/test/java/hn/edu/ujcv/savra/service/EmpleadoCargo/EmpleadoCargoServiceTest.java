package hn.edu.ujcv.savra.service.EmpleadoCargo;

import hn.edu.ujcv.savra.entity.Arqueo;
import hn.edu.ujcv.savra.entity.EmpleadoCargo.EmpleadoCargo;
import hn.edu.ujcv.savra.entity.EmpleadoCargo.EmpleadoCargoPK;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.EmpleadoCargoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class EmpleadoCargoServiceTest {
    @Mock
    private EmpleadoCargoRepository empleadoCargoRepository;

    @InjectMocks
    private EmpleadoCargoService empleadoCargoService;

    private EmpleadoCargo empleadoCargo;
    private EmpleadoCargo empleadoCargoEnviado;
    private EmpleadoCargoPK pk;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        empleadoCargo = new EmpleadoCargo();
        empleadoCargoEnviado = new EmpleadoCargo(Timestamp.valueOf(LocalDateTime.now()),1,
                LocalDate.now().minusDays(1),2);
        pk = new EmpleadoCargoPK(1, Timestamp.valueOf(LocalDateTime.now()));
    }

    @Test
    void saveEmpleadoCargo() throws BusinessException {
        when(empleadoCargoRepository.save(any(EmpleadoCargo.class))).thenReturn(empleadoCargo);
        assertNotNull(empleadoCargoService.saveEmpleadoCargo(empleadoCargoEnviado));
    }

    @Test
    void saveEmpleadoCargos() throws BusinessException {
        when(empleadoCargoRepository.saveAll(Arrays.asList(any(EmpleadoCargo.class)))).thenReturn(Arrays.asList(empleadoCargo));
        assertNotNull(empleadoCargoService.saveEmpleadoCargos(Arrays.asList(empleadoCargoEnviado)));
    }

    @Test
    void getEmpleadoCargos() throws BusinessException {
        when(empleadoCargoRepository.findAll()).thenReturn(Arrays.asList(empleadoCargo));
        assertNotNull(empleadoCargoService.getEmpleadoCargos());
    }

    @Test
    void getEmpleadoCargoById() throws BusinessException, NotFoundException {
        when(empleadoCargoRepository.findById(any(EmpleadoCargoPK.class))).thenReturn(Optional.of(empleadoCargo));
        assertNotNull(empleadoCargoService.getEmpleadoCargoById(1,"1676505956"));
    }

    @Test
    void deleteEmpleadoCargo() throws BusinessException, NotFoundException {
        getEmpleadoCargoById();
        empleadoCargoService.deleteEmpleadoCargo(1,"1676505956");
    }

    @Test
    void updateEmpleadoCargo() throws BusinessException, NotFoundException {
        getEmpleadoCargoById();
        when(empleadoCargoRepository.save(any(EmpleadoCargo.class))).thenReturn(empleadoCargo);
        assertNotNull(empleadoCargoService.updateEmpleadoCargo(empleadoCargoEnviado));
    }
}