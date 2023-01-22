package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    @Modifying
    @Transactional
    @Query(value = "update usuario set bloqueado= :estado where username = :user",nativeQuery = true)
    void bloqueoUsuario(@Param("estado") int bloqueado,
                        @Param("user") String username);

    @Modifying
    @Transactional
    @Query(value = "update usuario set activo= :estado where idUsuario = :id",nativeQuery = true)
    void activoUsuario(@Param("estado") int activo,
                     @Param("id") long idUsuario);
}
