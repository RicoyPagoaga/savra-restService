package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query(value = "select * from usuario where username = :username COLLATE SQL_Latin1_General_CP1_CS_AS",nativeQuery = true)
    Optional<Usuario> findPorUsername(String username);
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

    @Modifying
    @Transactional
    @Query(value = "Update usuario\n" +
            "set  clientesVista= :clientes , ultimaVisita = :ultimaV\n" +
            "where usuario.username= :nombreU",nativeQuery = true)
    void cierreSesion(@Param("clientes") int clienteVista,
                      @Param("ultimaV") LocalDateTime ultimaVisita, @Param("nombreU") String nombreUsuario);
}
