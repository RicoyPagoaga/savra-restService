package hn.edu.ujcv.savra.service.RepuestosService.PrecioHistoricoRepuestoService;

import hn.edu.ujcv.savra.entity.Arqueo;
import hn.edu.ujcv.savra.entity.Empleado;
import hn.edu.ujcv.savra.entity.Repuesto.PrecioHistoricoRepuesto.PrecioHistoricoRepuesto;
import hn.edu.ujcv.savra.entity.Repuesto.PrecioHistoricoRepuesto.PrecioHistoricoRepuestoPK;
import hn.edu.ujcv.savra.entity.Repuesto.Repuesto;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.Repuesto.PrecioHistoricoRepuestoRepository;
import hn.edu.ujcv.savra.repository.Repuesto.RepuestoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class PrecioHistoricoRepuestoServiceTest {

    @Mock
    private PrecioHistoricoRepuestoRepository repository;
    @Mock
    private RepuestoRepository repuestoRepository;
    @InjectMocks
    private PrecioHistoricoRepuestoService phService;

    private PrecioHistoricoRepuesto precioHistoricoRepuesto;
    private PrecioHistoricoRepuesto precioHistoricoRepuestoEnviado;
    private PrecioHistoricoRepuestoPK pk;
    private Repuesto repuesto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        precioHistoricoRepuesto = new PrecioHistoricoRepuesto();
        precioHistoricoRepuestoEnviado = new PrecioHistoricoRepuesto(1,
                LocalDate.now(),null,250);
        pk = new PrecioHistoricoRepuestoPK(1,LocalDate.now());
        repuesto = new Repuesto();
        repuesto.setIdRepuesto(1);
    }

    @Test
    void savePrecioRepuesto() throws BusinessException {
        when(repuestoRepository.findAll()).thenReturn(Arrays.asList(repuesto));
        when(repository.findAll()).thenReturn(Arrays.asList(precioHistoricoRepuestoEnviado));
        when(repository.save(any(PrecioHistoricoRepuesto.class))).thenReturn(precioHistoricoRepuestoEnviado);
        assertNotNull(phService.savePrecioRepuesto(precioHistoricoRepuestoEnviado));

    }

    @Test
    void getPreciosRepuesto() throws BusinessException {
        when(repository.findAll()).thenReturn(Arrays.asList(precioHistoricoRepuestoEnviado));
        assertNotNull(phService.getPreciosRepuesto());
    }

    @Test
    void getPrecioRepuestoById() throws BusinessException, NotFoundException {
        when(repository.findById(any(PrecioHistoricoRepuestoPK.class))).thenReturn(Optional.of(precioHistoricoRepuesto));
        assertNotNull(phService.getPrecioRepuestoById(1,LocalDate.now()));
    }

    @Test
    void deletePrecioRepuesto() throws BusinessException, NotFoundException {
        getPrecioRepuestoById();
        phService.deletePrecioRepuesto(1,LocalDate.now());
    }

    @Test
    void updatePrecioRepuesto() throws BusinessException, NotFoundException {
        getPreciosRepuesto();
        getPrecioRepuestoById();
        when(repuestoRepository.findAll()).thenReturn(Arrays.asList(repuesto));
        when(repository.save(any(PrecioHistoricoRepuesto.class))).thenReturn(precioHistoricoRepuesto);
        assertNotNull(phService.updatePrecioRepuesto(precioHistoricoRepuestoEnviado));

    }
}