package hn.edu.ujcv.savra.service.UsuarioService;

import hn.edu.ujcv.savra.entity.Arqueo;
import hn.edu.ujcv.savra.entity.Rol;
import hn.edu.ujcv.savra.entity.Usuario;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private Usuario usuarioEnviado;
    //ricoy123: contrasenia
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        usuario = new Usuario();
        usuarioEnviado = new Usuario(1,"Npagoaga","cmljb3kxMjM=","Ricoy","Pagoaga",
                1,0,new Rol(),2, Timestamp.valueOf(LocalDateTime.now()),2,2);
    }

    @Test
    void saveUsuario() throws BusinessException {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        assertNotNull(usuarioService.saveUsuario(usuarioEnviado,true));

    }

    @Test
    void saveUsuarios() throws BusinessException {
        when(usuarioRepository.saveAll(Arrays.asList(any(Usuario.class)))).thenReturn(Arrays.asList(usuario));
        assertNotNull(usuarioService.saveUsuarios(Arrays.asList(usuarioEnviado)));
    }

        @Test
        void getUsuarios() throws BusinessException {
            when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario));
            assertNotNull(usuarioService.getUsuarios());
        }

    @Test
    void getUsuarioById() throws BusinessException, NotFoundException{
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        assertNotNull(usuarioService.getUsuarioById(new Long(1)));
    }


    @Test
    void getUsuarioByUsername() throws BusinessException, NotFoundException {
        when(usuarioRepository.findPorUsername(anyString())).thenReturn(Optional.of(usuarioEnviado));
        assertNotNull(usuarioService.getUsuarioByUsername(usuarioEnviado.getUsername()));
    }

    @Test
    void deleteUsuario() throws BusinessException, NotFoundException {
        getUsuarioById();
        usuarioService.deleteUsuario(new Long(1));
    }


    @Test
    void updateUsuario() throws BusinessException, NotFoundException {
        getUsuarioById();
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        assertNotNull(usuarioService.updateUsuario(usuarioEnviado,true));
    }

    @Test
    void activarDesactivarUsuario() throws BusinessException, NotFoundException {
        getUsuarioById();
        usuarioService.activarDesactivarUsuario(0,1);
    }

    @Test
    void bloquearUsuario() throws BusinessException, NotFoundException {
        getUsuarioByUsername();
        usuarioService.bloquearUsuario(1,usuarioEnviado.getUsername());
    }

    @Test
    void validarLogin() throws BusinessException, NotFoundException {
        getUsuarioByUsername();
        assertTrue(usuarioService.validarLogin(usuarioEnviado.getUsername(),"ricoy123"));
    }

    @Test
    void validarUsuarioBloqueado() throws BusinessException, NotFoundException {
        getUsuarioByUsername();
        usuarioEnviado.setBloqueado(1);
        assertTrue(usuarioService.validarUsuarioBloqueado(usuarioEnviado.getUsername()));

    }
}