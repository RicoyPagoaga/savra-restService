package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.Accion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccionRepository extends JpaRepository<Accion, Long> {
    Optional<Accion> findAccionByNombre(String nombre);

    @Query(value = "Select a.idAccion, a.nombre\n" +
            "From rol as r\n" +
            "inner join permisos_rol as pr on pr.idRol = r.idRol\n" +
            "inner join modulo_accion as ma on ma.idModuloAccion = pr.idModuloAccion\n" +
            "inner join modulo as m on m.idModulo = ma.idModulo\n" +
            "inner join accion as a on a.idAccion = ma.idAccion\n" +
            "where r.idRol = :rol and m.nombre= :modulo",nativeQuery = true)
    List<Accion> accionesPorModuloRol(@Param("rol") long rol,@Param("modulo") String modulo);
}
