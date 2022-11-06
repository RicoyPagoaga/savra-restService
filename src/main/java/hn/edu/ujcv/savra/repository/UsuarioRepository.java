package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findFirstByUsername(String username);
}
