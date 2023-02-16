package hn.edu.ujcv.savra.service.EmpleadoService;

import hn.edu.ujcv.savra.entity.Cargo;
import hn.edu.ujcv.savra.entity.Empleado;
import hn.edu.ujcv.savra.entity.TipoDocumento;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.EmpleadosRepository;
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

class EmpleadosServiceTest {
    @Mock
    private EmpleadosRepository empleadosRepository;
    @InjectMocks
    private EmpleadosService empleadosService;
    private Empleado empleado;
    private Empleado empleadoenviado;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        empleado = new Empleado();
        TipoDocumento documento = new TipoDocumento();
        documento.setNombreDocumento("DNI");
        documento.setIdTipoDocumento(1);
        empleadoenviado = new Empleado(1,"carlos","0801199804607",documento, LocalDate.now().minusYears(19),"96299779",LocalDate.now(),"erlinmejia@gmail.com"
                ,"cerro grande");
    }

    @Test
    void saveEmpleados() throws BusinessException {
        when(empleadosRepository.save(any(Empleado.class))).thenReturn(empleado);
        assertNotNull(empleadosService.saveEmpleados(empleadoenviado));
    }

    @Test
    void testSaveEmpleados() throws BusinessException {
        when(empleadosRepository.saveAll(Arrays.asList(any(Empleado.class)))).thenReturn(Arrays.asList(empleado));
        assertNotNull(empleadosService.saveEmpleados(Arrays.asList(empleadoenviado)));
    }

    @Test
    void getEmpleados() throws BusinessException {
        when(empleadosRepository.findAll()).thenReturn(Arrays.asList(empleado));
        assertNotNull(empleadosService.getEmpleados());
    }

    @Test
    void getEmpleadosById() throws BusinessException, NotFoundException {
        when(empleadosRepository.findById(anyLong())).thenReturn(Optional.of(empleado));
        assertNotNull(empleadosService.getEmpleadosById(new Long(1)));
    }

    @Test
    void getEmpleadoByNombre() throws BusinessException, NotFoundException {
        when(empleadosRepository.findByNombre(anyString())).thenReturn(Optional.of(empleado));
        assertNotNull(empleadosService.getEmpleadoByNombre(empleadoenviado.getNombre()));
    }

    @Test
    void deleteEmpleados() throws BusinessException, NotFoundException {
        getEmpleadosById();
        empleadosService.deleteEmpleados(new Long(1));
    }

    @Test
    void updateEmpleados() throws BusinessException, NotFoundException {
        getEmpleadosById();
        when(empleadosRepository.save(any(Empleado.class))).thenReturn(empleado);
        assertNotNull(empleadosService.updateEmpleados(empleadoenviado));
    }
}