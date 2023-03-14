package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.Modulo;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ModuloRepository extends JpaRepository<Modulo,Long> {
    Optional<Modulo> findByNombre(String nombre);

}
