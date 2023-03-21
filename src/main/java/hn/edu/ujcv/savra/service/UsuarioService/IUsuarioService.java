package hn.edu.ujcv.savra.service.UsuarioService;

import hn.edu.ujcv.savra.entity.Usuario;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface IUsuarioService {
    Usuario       saveUsuario(Usuario usuario, boolean coinciden) throws BusinessException;
    List<Usuario> saveUsuarios(List<Usuario> usuarios) throws BusinessException;
    List<Usuario> getUsuarios() throws BusinessException;
    Usuario       getUsuarioById(long id) throws BusinessException, NotFoundException;
    Usuario       getUsuarioByUsername(String username) throws BusinessException, NotFoundException;
    void          deleteUsuario(long id) throws BusinessException, NotFoundException;
    Usuario       updateUsuario(Usuario usuario, boolean coinciden) throws BusinessException, NotFoundException;
    void          activarDesactivarUsuario(int estado , long id)throws BusinessException,NotFoundException;
    void          bloquearUsuario(int estado , String username)throws BusinessException,NotFoundException;
    void          cerrarSesion(int clientes, String ultimaV, String nombreUsuario)throws BusinessException;
    List<String> getModulos(long idRol)throws BusinessException;
}
