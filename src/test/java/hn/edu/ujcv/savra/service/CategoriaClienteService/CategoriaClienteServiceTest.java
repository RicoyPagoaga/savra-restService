package hn.edu.ujcv.savra.service.CategoriaClienteService;

import hn.edu.ujcv.savra.entity.Cargo;
import hn.edu.ujcv.savra.entity.CategoriaCliente;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.exceptions.SqlExceptions;
import hn.edu.ujcv.savra.repository.CategoriaClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class CategoriaClienteServiceTest {
    @Mock
    private CategoriaClienteRepository categoriaClienteRepository;
    @InjectMocks
    private CategoriaClienteService categoriaClienteService;
    private CategoriaCliente categoriaCliente;
    private CategoriaCliente categoriaenviada;

    private long idCategoria;
    private String nombre;
    private String descripcion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoriaCliente = new CategoriaCliente();
        categoriaenviada = new CategoriaCliente(1,"erlin","Venta de carburador");
    }

    @Test
    void saveCategoriaCliente() throws BusinessException, SqlExceptions {
        when(categoriaClienteRepository.save(any(CategoriaCliente.class))).thenReturn(categoriaCliente);
        assertNotNull(categoriaClienteService.saveCategoriaCliente(categoriaenviada));
    }

   // @Test
    //void getCategoriaClientes() {
    //    when(categoriaClienteRepository.saveAll(Arrays.asList(any(CategoriaCliente.class)))).thenReturn(Arrays.asList(categoriaCliente));
    //    assertNotNull(categoriaClienteService.saveCategoriaCliente(Arrays.asList(categoriaenviada)));
   // }

    @Test
    void getCategoriaClienteById() throws BusinessException, NotFoundException {
        when(categoriaClienteRepository.findById(anyLong())).thenReturn(Optional.of(categoriaCliente));
        assertNotNull(categoriaClienteService.getCategoriaClienteById(new Long(1)));
    }

    @Test
    void getCategoriaClienteByNombre() {
    }

    @Test
    void deteleCategoriaCliente() throws BusinessException, NotFoundException, SqlExceptions {
        getCategoriaClienteById();
        categoriaClienteService.deteleCategoriaCliente(new Long(1));
    }

    @Test
    void updateCategoriaCliente() throws BusinessException, NotFoundException, SqlExceptions {
        getCategoriaClienteById();
        when(categoriaClienteRepository.save(any(CategoriaCliente.class))).thenReturn(categoriaCliente);
        assertNotNull(categoriaClienteService.updateCategoriaCliente(categoriaenviada));
    }
}