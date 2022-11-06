package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.CategoriaCliente;
import hn.edu.ujcv.savra.entity.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoDocumentoRepository extends JpaRepository <TipoDocumento ,Long>{
    Optional<TipoDocumento> findByNombre(String nombre);
}
