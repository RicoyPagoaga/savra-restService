package hn.edu.ujcv.savra.service.ProveedorService;

import hn.edu.ujcv.savra.entity.Arqueo;
import hn.edu.ujcv.savra.entity.Pais;
import hn.edu.ujcv.savra.entity.Proveedor;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.ProveedorRepository;
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

class ProveedorServiceTest {
    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    private Proveedor proveedor;
    private Proveedor proveedorEnviado;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        proveedor = new Proveedor();
        proveedorEnviado = new Proveedor(1,"REASA","reasa@gmail.com"
                ,"98451236",new Pais(),"Juan Gonzales","www.reasa.hn");
    }

    @Test
    void saveProveedor() throws BusinessException {
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(proveedor);
        assertNotNull(proveedorService.saveProveedor(proveedorEnviado));
    }

    @Test
    void saveProveedores() throws BusinessException {
        when(proveedorRepository.saveAll(Arrays.asList(any(Proveedor.class)))).thenReturn(Arrays.asList(proveedor));
        assertNotNull(proveedorService.saveProveedores(Arrays.asList(proveedorEnviado)));
    }

    @Test
    void getProveedores() throws BusinessException {
        when(proveedorRepository.findAll()).thenReturn(Arrays.asList(proveedor));
        assertNotNull(proveedorService.getProveedores());
    }

    @Test
    void getProveedorById() throws BusinessException, NotFoundException {
        when(proveedorRepository.findById(anyLong())).thenReturn(Optional.of(proveedor));
        assertNotNull(proveedorService.getProveedorById(new Long(1)));
    }

    @Test
    void getProveedorByNombre() throws BusinessException, NotFoundException {
        when(proveedorRepository.findFirstByNombre(anyString())).thenReturn(Optional.of(proveedor));
        assertNotNull(proveedorService.getProveedorByNombre(proveedorEnviado.getNombre()));
    }

    @Test
    void deleteProveedor() throws BusinessException, NotFoundException {
        getProveedorById();
        proveedorService.deleteProveedor(new Long(1));
    }

    @Test
    void updateProveedor() throws BusinessException, NotFoundException {
        getProveedorById();
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(proveedor);
        assertNotNull(proveedorService.updateProveedor(proveedorEnviado));
    }
}