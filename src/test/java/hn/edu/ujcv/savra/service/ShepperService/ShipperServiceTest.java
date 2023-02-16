package hn.edu.ujcv.savra.service.ShepperService;

import hn.edu.ujcv.savra.entity.Arqueo;
import hn.edu.ujcv.savra.entity.Empleado;
import hn.edu.ujcv.savra.entity.Shipper;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.ShipperRepository;
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

class ShipperServiceTest {
    @Mock
    private ShipperRepository shipperRepository;
    @InjectMocks
    private ShipperService shipperService;

    private Shipper shipper;
    private Shipper shipperEnviado;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        shipper = new Shipper();
        shipperEnviado = new Shipper(1,"MotoExpressHN","88752142","MotoExpres@gmail.com","www.motoexpres.com",LocalDate.now());
    }

    @Test
    void saveShipper() throws BusinessException {
        when(shipperRepository.save(any(Shipper.class))).thenReturn(shipper);
        assertNotNull(shipperService.saveShipper(shipperEnviado));
    }

    @Test
    void saveShippers() throws BusinessException {
        when(shipperRepository.saveAll(Arrays.asList(any(Shipper.class)))).thenReturn(Arrays.asList(shipper));
        assertNotNull(shipperService.saveShippers(Arrays.asList(shipperEnviado)));
    }

    @Test
    void getShippers() throws BusinessException {
        when(shipperRepository.findAll()).thenReturn(Arrays.asList(shipper));
        assertNotNull(shipperService.getShippers());
    }

    @Test
    void getShipperById() throws BusinessException, NotFoundException {
        when(shipperRepository.findById(anyLong())).thenReturn(Optional.of(shipper));
        assertNotNull(shipperService.getShipperById(new Long(1)));
    }

    @Test
    void getShipperByNombre() throws BusinessException, NotFoundException {
        when(shipperRepository.findShipperByCorreo(anyString())).thenReturn(Optional.of(shipper));
        assertNotNull(shipperService.getShipperByNombre(shipperEnviado.getNombre()));
    }

    @Test
    void deleteShipper() throws BusinessException, NotFoundException {
        getShipperById();
        shipperService.deleteShipper(new Long(1));
    }

    @Test
    void updateShipper() throws BusinessException, NotFoundException {
        getShipperById();
        when(shipperRepository.save(any(Shipper.class))).thenReturn(shipper);
        assertNotNull(shipperService.saveShipper(shipperEnviado));
    }
}