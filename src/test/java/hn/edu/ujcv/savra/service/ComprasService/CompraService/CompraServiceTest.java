package hn.edu.ujcv.savra.service.ComprasService.CompraService;

import hn.edu.ujcv.savra.entity.Arqueo;
import hn.edu.ujcv.savra.entity.Compra.Compra;
import hn.edu.ujcv.savra.entity.Empleado;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.Compra.CompraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class CompraServiceTest {
    @Mock
    private CompraRepository compraRepository;

    @InjectMocks
    private CompraService compraService;

    private Compra compra;
    private Compra compraEnviada;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        compra = new Compra();
        compraEnviada = new Compra(1,new Empleado(), LocalDate.now(),LocalDate.now()
        ,LocalDate.now().plusDays(6),"12345678941");
    }

    @Test
    void saveCompra() throws BusinessException {
        when(compraRepository.save(any(Compra.class))).thenReturn(compra);
        assertNotNull(compraService.saveCompra(compraEnviada,true));
    }

    @Test
    void saveCompras() throws BusinessException {
        when(compraRepository.saveAll(Arrays.asList(any(Compra.class)))).thenReturn(Arrays.asList(compra));
        assertNotNull(compraService.saveCompras(Arrays.asList(compraEnviada)));
    }

    @Test
    void getCompras() throws BusinessException {
        when(compraRepository.findAll()).thenReturn(Arrays.asList(compra));
        assertNotNull(compraService.getCompras());
    }

    @Test
    void getCompraById() throws BusinessException, NotFoundException {
        when(compraRepository.findById(anyLong())).thenReturn(Optional.of(compra));
        assertNotNull(compraService.getCompraById(new Long(1)));
    }

    @Test
    void deleteCompra() throws BusinessException, NotFoundException {
        getCompraById();
        compraService.deleteCompra(new Long(1));
    }

    @Test
    void updateCompra() throws BusinessException, NotFoundException {
        getCompraById();
        when(compraRepository.save(any(Compra.class))).thenReturn(compra);
        assertNotNull(compraService.updateCompra(compraEnviada,true));
    }
}