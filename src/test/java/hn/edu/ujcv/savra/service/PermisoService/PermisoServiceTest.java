package hn.edu.ujcv.savra.service.PermisoService;

import hn.edu.ujcv.savra.entity.Cargo;
import hn.edu.ujcv.savra.entity.Permiso;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.PermisoRepository;
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
    private PermisoRepository permisoRepository;
    @InjectMocks
    private PermisoService permisoService;
    private Permiso permiso;
    private Permiso setPermiso;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        permiso = new Permiso();
        setPermiso = new Permiso(1,"guardia","cuidar");
    }

    @Test
    void savePermiso() throws BusinessException {
        when(permisoRepository.save(any(Permiso.class))).thenReturn(permiso);
        assertNotNull(permisoService.savePermiso(setPermiso));
    }

    @Test
    void savePermisos() throws BusinessException {
        when(permisoRepository.saveAll(Arrays.asList(any(Permiso.class)))).thenReturn(Arrays.asList(permiso));
        assertNotNull(permisoService.savePermisos(Arrays.asList(setPermiso)));
    }

    @Test
    void getPermisos() throws BusinessException {
        when(permisoRepository.findAll()).thenReturn(Arrays.asList(permiso));
        assertNotNull(permisoService.getPermisos());
    }

    @Test
    void getPermisoById() throws BusinessException, NotFoundException {
        when(permisoRepository.findById(anyLong())).thenReturn(Optional.of(permiso));
        assertNotNull(permisoService.getPermisoById(new Long(1)));
    }

    @Test
    void getPermisoByNombre() throws BusinessException, NotFoundException {
        when(permisoRepository.findPermisoByNombre(anyString())).thenReturn(Optional.of(permiso));
        assertNotNull(permisoService.getPermisoByNombre(setPermiso.getNombre()));
    }

    @Test
    void deletePermiso() throws BusinessException, NotFoundException {
        getPermisoById();
        permisoService.deletePermiso(new Long(1));
    }

    @Test
    void updatePermiso() throws BusinessException, NotFoundException {
        getPermisoById();
        when(permisoRepository.save(any(Permiso.class))).thenReturn(permiso);
        assertNotNull(permisoService.updatePermiso(setPermiso));

    }
}