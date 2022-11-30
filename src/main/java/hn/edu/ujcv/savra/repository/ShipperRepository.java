package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShipperRepository extends JpaRepository<Shipper ,Long> {
    Optional<Shipper> findShipperByCorreo (String correo);
}
