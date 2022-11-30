package hn.edu.ujcv.savra.service.Usuario;

import hn.edu.ujcv.savra.entity.Usuario;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface IUsuarioService {
    Usuario       saveUsuario(Usuario usuario) throws BusinessException;
    List<Usuario> saveUsuarios(List<Usuario> usuarios) throws BusinessException;
    List<Usuario> getUsuarios() throws BusinessException;
    Usuario       getUsuarioById(long id) throws BusinessException, NotFoundException;
    Usuario       getUsuarioByUsername(String username) throws BusinessException, NotFoundException;
    void          deleteUsuario(long id) throws BusinessException, NotFoundException;
    Usuario       updateUsuario(Usuario usuario) throws BusinessException, NotFoundException;
}
