package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.Cupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface CuponRepository extends JpaRepository<Cupon,Long> {
    Optional<Cupon> findByCodigo(String codigo);
    @Modifying
    @Transactional
    @Query(value = "update cupon set activo= :estado where idCupon = :id",nativeQuery = true)
    void activoCupon(@Param("estado") int activo,
                                @Param("id") long idCupon);
}
