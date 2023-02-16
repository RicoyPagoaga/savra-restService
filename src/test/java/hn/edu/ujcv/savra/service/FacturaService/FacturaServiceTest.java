package hn.edu.ujcv.savra.service.FacturaService;

import hn.edu.ujcv.savra.entity.*;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.FacturaRepository;
import hn.edu.ujcv.savra.repository.ParametroFacturaRepository;
import hn.edu.ujcv.savra.service.ParametroFacturaService.ParametroFacturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.jnlp.SingleInstanceListener;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class FacturaServiceTest {
    @Mock
    private FacturaRepository facturaRepository;

    @Mock
    private ParametroFacturaRepository parametroFacturaRepository;
    @InjectMocks
    private FacturaService facturaService;

    private Factura factura;
    private Factura facturaEnviada;
    private FacturaRecibo facturaRecibo;
    private ParametroFactura parametroFacturaEnviado;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        factura = new Factura();
        facturaEnviada = new Factura(1,1,"020-001-01-01000001",1,1, Timestamp.valueOf(LocalDateTime.now()),1
                ,2500.00,null,null,1,null,0.00,
                LocalDate.now(),LocalDate.now().plusDays(3));
        parametroFacturaEnviado = new ParametroFactura(1,"C1D1E9-342413-FC4180-82F25E-C52983-BA","020-001-01-01000000"
                ,"020-001-01-01060001", LocalDate.now().plusMonths(5),LocalDate.now(),15400);
        facturaRecibo = new FacturaRecibo() {
            @Override
            public String getCai() {
                return null;
            }

            @Override
            public String getNoFactura() {
                return null;
            }

            @Override
            public String getFechaLimite() {
                return null;
            }

            @Override
            public String getCliente() {
                return null;
            }

            @Override
            public String getFechaFactura() {
                return null;
            }

            @Override
            public String getEmpleado() {
                return null;
            }

            @Override
            public String getTipoEntrega() {
                return null;
            }

            @Override
            public String getShipper() {
                return null;
            }

            @Override
            public double getCostoEnvio() {
                return 0;
            }

            @Override
            public String getFechaDespacho() {
                return null;
            }

            @Override
            public String getFechaEntrega() {
                return null;
            }

            @Override
            public String getMetodoPago() {
                return null;
            }

            @Override
            public String getNoTarjeta() {
                return null;
            }

            @Override
            public double getEfectivo() {
                return 0;
            }

            @Override
            public String getCupon() {
                return null;
            }

            @Override
            public String getRangoInicial() {
                return null;
            }

            @Override
            public String getRangoFinal() {
                return null;
            }
        };
    }

    @Test
    void guardarFactura() throws BusinessException {
        when(parametroFacturaRepository.findAll()).thenReturn(Arrays.asList(parametroFacturaEnviado));
        when(facturaRepository.save(any(Factura.class))).thenReturn(factura);
        assertNotNull(facturaService.guardarFactura(facturaEnviada));
    }

    @Test
    void obtenerFacturas() throws BusinessException {
        when(facturaRepository.findAll()).thenReturn(Arrays.asList(factura));
        assertNotNull(facturaService.obtenerFacturas());
    }

    @Test
    void obtenerFacturaByNoFactura() throws BusinessException, NotFoundException {
        when(facturaRepository.findByNoFactura(anyString())).thenReturn(Optional.of(factura));
        assertNotNull(facturaService.obtenerFacturaByNoFactura(facturaEnviada.getNoFactura()));
    }

    @Test
    void obtenerRecibo() throws BusinessException, NotFoundException {
        when(facturaRepository.getReciboEncabezado(anyLong())).thenReturn(Optional.of(facturaRecibo));
        assertNotNull(facturaService.obtenerRecibo(1));
    }
}