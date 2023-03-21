package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @Query(value = "Select m.nombre\n" +
            "From rol as r\n" +
            "inner join permisos_rol as pr on pr.idRol = r.idRol\n" +
            "inner join modulo_accion as ma on ma.idModuloAccion = pr.idModuloAccion\n" +
            "inner join modulo as m on m.idModulo = ma.idModulo\n" +
            "where r.idRol= :rol\n" +
            "group by m.nombre",nativeQuery = true)
    List<String> obtenerModulos (@Param("rol") long rolUsuario);
}
