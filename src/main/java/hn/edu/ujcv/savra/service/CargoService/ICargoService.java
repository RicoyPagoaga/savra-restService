package hn.edu.ujcv.savra.service.CargoService;

import hn.edu.ujcv.savra.entity.Cargo;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import java.util.List;

public interface ICargoService {
    Cargo         saveCargo(Cargo cargo) throws BusinessException;
    List <Cargo>  saveCargos(List<Cargo> cargos) throws BusinessException;
    List <Cargo>  getCargos() throws BusinessException;
    Cargo         getCargoById(long id) throws BusinessException, NotFoundException;
    Cargo         getCargoByNombre(String nombre) throws BusinessException,NotFoundException;
    void          deleteCargo(long id) throws BusinessException,NotFoundException;
    Cargo         updateCargo(Cargo cargo) throws BusinessException,NotFoundException;
}