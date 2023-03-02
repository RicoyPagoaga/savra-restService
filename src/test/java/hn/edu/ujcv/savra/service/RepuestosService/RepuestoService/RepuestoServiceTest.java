package hn.edu.ujcv.savra.service.RepuestosService.RepuestoService;

import hn.edu.ujcv.savra.entity.*;
import hn.edu.ujcv.savra.entity.Impuesto.Impuesto;
import hn.edu.ujcv.savra.entity.Repuesto.Repuesto;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.CategoriaRepuestoRepository;
import hn.edu.ujcv.savra.repository.Impuesto.ImpuestoRepository;
import hn.edu.ujcv.savra.repository.ModeloRepository;
import hn.edu.ujcv.savra.repository.ProveedorRepository;
import hn.edu.ujcv.savra.repository.Repuesto.RepuestoRepository;
import hn.edu.ujcv.savra.repository.TransmisionRepository;
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

class RepuestoServiceTest {
    @Mock
    private RepuestoRepository repuestoRepository;
    @Mock
    private CategoriaRepuestoRepository categoriaRepuestoRepository;
    @Mock
    private ProveedorRepository proveedorRepository;
    @Mock
    private ModeloRepository modeloRepository;
    @Mock
    private TransmisionRepository transmisionRepository;
    @Mock
    private ImpuestoRepository impuestoRepository;

    @InjectMocks
    private RepuestoService repuestoService;

    private Repuesto repuesto;
    private CategoriaRepuesto categoria;
    private Proveedor proveedor;
    private Modelo modelo;
    private Transmision transmision;
    private Impuesto impuesto;
    private Repuesto repuestoEnviado;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        repuesto = new Repuesto();
        repuestoEnviado = new Repuesto(1,"Bujia",2000,2010,1,
                500,80,800,1,1,1,1);
        categoria = new CategoriaRepuesto();
        categoria.setIdCategoria(1);
        proveedor = new Proveedor();
        proveedor.setIdProveedor(1);
        modelo = new Modelo();
        modelo.setIdModelo(1);
        transmision = new Transmision();
        transmision.setIdTransmision(1);
        impuesto = new Impuesto();
        impuesto.setIdImpuesto(1);
    }
    @Test
    void saveRepuesto() throws BusinessException {
        getRepuestos();
        when(categoriaRepuestoRepository.findAll()).thenReturn(Arrays.asList(categoria));
        when(proveedorRepository.findAll()).thenReturn(Arrays.asList(proveedor));
        when(modeloRepository.findAll()).thenReturn(Arrays.asList(modelo));
        when(transmisionRepository.findAll()).thenReturn(Arrays.asList(transmision));
        when(impuestoRepository.findAll()).thenReturn(Arrays.asList(impuesto));
        when(repuestoRepository.save(any(Repuesto.class))).thenReturn(repuesto);
        assertNotNull(repuestoService.saveRepuesto(repuestoEnviado,false,false, false,2500));
    }

    @Test
    void saveRepuestos() throws BusinessException {
        getRepuestos();
        when(categoriaRepuestoRepository.findAll()).thenReturn(Arrays.asList(categoria));
        when(proveedorRepository.findAll()).thenReturn(Arrays.asList(proveedor));
        when(modeloRepository.findAll()).thenReturn(Arrays.asList(modelo));
        when(transmisionRepository.findAll()).thenReturn(Arrays.asList(transmision));
        when(impuestoRepository.findAll()).thenReturn(Arrays.asList(impuesto));
        when(repuestoRepository.save(any(Repuesto.class))).thenReturn(repuesto);
        when(repuestoRepository.saveAll(Arrays.asList(any(Repuesto.class)))).thenReturn(Arrays.asList(repuesto));
        assertNotNull(repuestoService.saveRepuestos(Arrays.asList(repuestoEnviado)));
    }

    @Test
    void getRepuestos() throws BusinessException {
        when(repuestoRepository.findAll()).thenReturn(Arrays.asList(repuesto));
        assertNotNull(repuestoService.getRepuestos());
    }

    @Test
    void getRepuestoById() throws BusinessException, NotFoundException {
        when(repuestoRepository.findById(anyLong())).thenReturn(Optional.of(repuesto));
        assertNotNull(repuestoService.getRepuestoById(new Long(1)));
    }

    @Test
    void getRepuestoByNombre() throws BusinessException, NotFoundException {
        when(repuestoRepository.findFirstByNombre(anyString())).thenReturn(Optional.of(repuesto));
        assertNotNull(repuestoService.getRepuestoByNombre(repuestoEnviado.getNombre()));
    }

    @Test
    void deleteRepuesto() throws BusinessException, NotFoundException {
        getRepuestoById();
        repuestoService.deleteRepuesto(new Long(1));
    }

    @Test
    void updateRepuesto() throws BusinessException, NotFoundException {
        getRepuestos();
        when(categoriaRepuestoRepository.findAll()).thenReturn(Arrays.asList(categoria));
        when(proveedorRepository.findAll()).thenReturn(Arrays.asList(proveedor));
        when(modeloRepository.findAll()).thenReturn(Arrays.asList(modelo));
        when(transmisionRepository.findAll()).thenReturn(Arrays.asList(transmision));
        when(impuestoRepository.findAll()).thenReturn(Arrays.asList(impuesto));
        when(repuestoRepository.save(any(Repuesto.class))).thenReturn(repuesto);
        getRepuestoById();
        when(repuestoRepository.save(any(Repuesto.class))).thenReturn(repuesto);
        assertNotNull(repuestoService.updateRepuesto(repuestoEnviado,false,false,
                false,2600));
    }
}