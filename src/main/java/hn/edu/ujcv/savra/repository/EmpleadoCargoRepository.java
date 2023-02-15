package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.EmpleadoCargo.EmpleadoCargo;
import hn.edu.ujcv.savra.entity.EmpleadoCargo.EmpleadoCargoPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoCargoRepository extends JpaRepository<EmpleadoCargo, EmpleadoCargoPK> {
    //Optional<EmpleadoCargo> findEmpleadoCargoByiOrderBydEmpleado  (String empleado);
}
