package hn.edu.ujcv.savra.service.AccionService;

import hn.edu.ujcv.savra.entity.Accion;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.AccionRepository;
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

class PermisoServiceTest {
    @Mock
    private AccionRepository accionRepository;
    @InjectMocks
    private AccionService permisoService;
    private Accion accion;
    private Accion setAccion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        accion = new Accion();
        setAccion = new Accion(1,"guardia","Vigilancia");
    }

    @Test
    void savePermiso() throws BusinessException, NotFoundException {
        setAccion.setNombre("Conserje");
        when(accionRepository.saveAll(Arrays.asList(any(Accion.class)))).thenReturn(Arrays.asList(setAccion));
        when(accionRepository.save(any(Accion.class))).thenReturn(accion);
        assertNotNull(permisoService.saveAccion(setAccion));
    }

    @Test
    void savePermisos() throws BusinessException, NotFoundException {
        getPermisoByNombre();
        when(accionRepository.saveAll(Arrays.asList(any(Accion.class)))).thenReturn(Arrays.asList(accion));
        assertNotNull(permisoService.saveAccions(Arrays.asList(setAccion)));
    }

    @Test
    void getPermisos() throws BusinessException {
        when(accionRepository.findAll()).thenReturn(Arrays.asList(accion));
        assertNotNull(permisoService.getAccion());
    }

    @Test
    void getPermisoById() throws BusinessException, NotFoundException {
        when(accionRepository.findById(anyLong())).thenReturn(Optional.of(accion));
        assertNotNull(permisoService.getAccionById(new Long(1)));
    }

    @Test
    void getPermisoByNombre() throws BusinessException, NotFoundException {
        when(accionRepository.findAccionByNombre(anyString())).thenReturn(Optional.of(accion));
        assertNotNull(permisoService.getAccionByNombre(setAccion.getNombre()));
    }

    @Test
    void deletePermiso() throws BusinessException, NotFoundException {
        getPermisoById();
        permisoService.deleteAccion(new Long(1));
    }

    @Test
    void updatePermiso() throws BusinessException, NotFoundException {
        getPermisoById();
        when(accionRepository.save(any(Accion.class))).thenReturn(accion);
        assertNotNull(permisoService.updateAccion(setAccion));

    }
}