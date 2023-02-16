package hn.edu.ujcv.savra.service.RolService;

import hn.edu.ujcv.savra.entity.Arqueo;
import hn.edu.ujcv.savra.entity.Rol;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.RolRepository;
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

class RolServiceTest {
    @Mock
    private RolRepository rolRepository;
    @InjectMocks
    private RolService rolService;

    private Rol rol;
    private Rol rolEnviado;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        rol = new Rol();
        rolEnviado = new Rol(1,"Supervisor","Gestion del almacen");
    }

    @Test
    void saveRol() throws BusinessException {
        when(rolRepository.save(any(Rol.class))).thenReturn(rol);
        assertNotNull(rolService.saveRol(rolEnviado));
    }

    @Test
    void saveRoles() throws BusinessException {
        when(rolRepository.saveAll(Arrays.asList(any(Rol.class)))).thenReturn(Arrays.asList(rol));
        assertNotNull(rolService.saveRoles(Arrays.asList(rolEnviado)));
    }

    @Test
    void getRol() throws BusinessException {
        when(rolRepository.findAll()).thenReturn(Arrays.asList(rol));
        assertNotNull(rolService.getRol());
    }

    @Test
    void getRolById() throws BusinessException, NotFoundException {
        when(rolRepository.findById(anyLong())).thenReturn(Optional.of(rol));
        assertNotNull(rolService.getRolById(new Long(1)));
    }

    @Test
    void deleteRol() throws BusinessException, NotFoundException {
        getRolById();
        rolService.deleteRol(new Long(1));
    }

    @Test
    void updateRol() throws BusinessException, NotFoundException {
        getRolById();
        when(rolRepository.save(any(Rol.class))).thenReturn(rol);
        assertNotNull(rolService.updateRol(rolEnviado));
    }
}