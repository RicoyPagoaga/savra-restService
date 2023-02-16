package hn.edu.ujcv.savra.service.ClienteService;

import hn.edu.ujcv.savra.entity.*;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.exceptions.SqlExceptions;
import hn.edu.ujcv.savra.repository.ClienteRepository;
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


class ClienteServiceTest {
    @Mock
    private ClienteRepository clienteRepository;
    @InjectMocks
    private ClienteService clienteService;
    private Cliente cliente;
    private Cliente clienteEnviado;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        cliente = new Cliente();
        TipoDocumento documento = new TipoDocumento();
        documento.setNombreDocumento("DNI");
        documento.setIdTipoDocumento(1);

        clienteEnviado = new Cliente(1,"pedro","0801199704607",documento,"99886754","sabana grande",
                new CategoriaCliente());
    }

    @Test
    void saveCliente() throws BusinessException, SQLException {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        assertNotNull(clienteService.saveCliente(clienteEnviado));
    }

    @Test
    void getClientes() throws BusinessException {
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente));
        assertNotNull(clienteService.getClientes());
    }

    @Test
    void deleteCliente() throws BusinessException, NotFoundException {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        clienteService.deleteCliente(new Long(1));
    }

    @Test
    void updateCliente() throws BusinessException, NotFoundException, SqlExceptions {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        assertNotNull(clienteService.UpdateCliente(clienteEnviado));
    }
}