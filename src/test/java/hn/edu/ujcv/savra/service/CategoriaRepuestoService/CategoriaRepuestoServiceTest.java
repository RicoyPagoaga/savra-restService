package hn.edu.ujcv.savra.service.CategoriaRepuestoService;

import hn.edu.ujcv.savra.entity.Cargo;
import hn.edu.ujcv.savra.entity.CategoriaRepuesto;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.CategoriaClienteRepository;
import hn.edu.ujcv.savra.repository.CategoriaRepuestoRepository;
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

class CategoriaRepuestoServiceTest {
    @Mock
    private CategoriaRepuestoRepository categoriaRepuestoRepository;
    @InjectMocks
    private CategoriaRepuestoService categoriaRepuestoService;
    private CategoriaRepuesto categoriaRepuesto;
    private CategoriaRepuesto categoriaRepuestoEnviado;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoriaRepuesto = new CategoriaRepuesto();
        categoriaRepuestoEnviado = new CategoriaRepuesto(1,"radiador","radiador");
    }

    @Test
    void saveCategoriaRepuesto() throws BusinessException {
        when(categoriaRepuestoRepository.save(any(CategoriaRepuesto.class))).thenReturn(categoriaRepuesto);
        assertNotNull(categoriaRepuestoService.saveCategoriaRepuesto(categoriaRepuestoEnviado));
    }

    @Test
    void saveCategoriasRepuesto() throws BusinessException {
        when(categoriaRepuestoRepository.saveAll(Arrays.asList(any(CategoriaRepuesto.class)))).thenReturn(Arrays.asList(categoriaRepuesto));
        assertNotNull(categoriaRepuestoService.saveCategoriasRepuesto(Arrays.asList(categoriaRepuestoEnviado)));
    }


    @Test
    void getCategoriasRepuesto() throws BusinessException {
        when(categoriaRepuestoRepository.saveAll(Arrays.asList(any(CategoriaRepuesto.class)))).thenReturn(Arrays.asList(categoriaRepuesto));
        assertNotNull(categoriaRepuestoService.saveCategoriasRepuesto(Arrays.asList(categoriaRepuestoEnviado)));
    }

    @Test
    void getCategoriaRepuestoById() throws BusinessException, NotFoundException {
        when(categoriaRepuestoRepository.findById(anyLong())).thenReturn(Optional.of(categoriaRepuesto));
        assertNotNull(categoriaRepuestoService.getCategoriaRepuestoById(new Long(1)));
    }

    @Test
    void getCategoriaRepuestoByNombre() throws BusinessException, NotFoundException {
        when(categoriaRepuestoRepository.findByNombre(anyString())).thenReturn(Optional.of(categoriaRepuesto));
        assertNotNull(categoriaRepuestoService.getCategoriaRepuestoByNombre(categoriaRepuestoEnviado.getNombre()));
    }

    @Test
    void deleteCategoriaRepuesto() throws BusinessException, NotFoundException {
        getCategoriaRepuestoById();
        categoriaRepuestoService.deleteCategoriaRepuesto(new Long(1));
    }

    @Test
    void updateCategoriaRepuesto() throws BusinessException, NotFoundException {
        getCategoriaRepuestoById();
        when(categoriaRepuestoRepository.save(any(CategoriaRepuesto.class))).thenReturn(categoriaRepuesto);
        assertNotNull(categoriaRepuestoService.updateCategoriaRepuesto(categoriaRepuestoEnviado));
    }
}