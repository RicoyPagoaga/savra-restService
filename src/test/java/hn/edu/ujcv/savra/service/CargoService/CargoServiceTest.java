package hn.edu.ujcv.savra.service.CargoService;

import hn.edu.ujcv.savra.entity.Arqueo;
import hn.edu.ujcv.savra.entity.Cargo;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.CargoRepository;
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

class CargoServiceTest {
    @Mock
    private CargoRepository cargoRepository;
    @InjectMocks
    private CargoService cargoService;
    private Cargo cargo;
    private Cargo cargoEnviado;

    @BeforeEach
    void     setUp() {
        MockitoAnnotations.initMocks(this);
        cargo = new Cargo();
        cargoEnviado = new Cargo(1,"cajero",7000,"Jugar lol");
    }

    @Test
    void saveCargo() throws BusinessException {
        when(cargoRepository.save(any(Cargo.class))).thenReturn(cargo);
        assertNotNull(cargoService.saveCargo(cargoEnviado));
    }

    @Test
    void saveCargos() throws BusinessException {
        when(cargoRepository.saveAll(Arrays.asList(any(Cargo.class)))).thenReturn(Arrays.asList(cargo));
        assertNotNull(cargoService.saveCargos(Arrays.asList(cargoEnviado)));
    }

    @Test
    void getCargos() throws BusinessException {
        when(cargoRepository.findAll()).thenReturn(Arrays.asList(cargo));
        assertNotNull(cargoService.getCargos());
    }

    @Test
    void getCargoById() throws BusinessException, NotFoundException {
        when(cargoRepository.findById(anyLong())).thenReturn(Optional.of(cargo));
        assertNotNull(cargoService.getCargoById(new Long(1)));
    }

    @Test
    void getCargoByNombre() throws BusinessException, NotFoundException {
        when(cargoRepository.findCargoByNombre(anyString())).thenReturn(Optional.of(cargo));
        assertNotNull(cargoService.getCargoByNombre(cargoEnviado.getNombre()));
    }

    @Test
    void deleteCargo() throws BusinessException, NotFoundException {
        getCargoById();
        cargoService.deleteCargo(new Long(1));
    }

    @Test
    void updateCargo() throws BusinessException, NotFoundException {
        getCargoById();
        when(cargoRepository.save(any(Cargo.class))).thenReturn(cargo);
        assertNotNull(cargoService.updateCargo(cargoEnviado));
    }
}