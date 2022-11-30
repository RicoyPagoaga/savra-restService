package hn.edu.ujcv.savra.service.Usuario;

import hn.edu.ujcv.savra.entity.Usuario;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService  {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public Usuario saveUsuario(Usuario usuario) throws BusinessException {
        try {
            return repository.save(usuario);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Usuario> saveUsuarios(List<Usuario> usuarios) throws BusinessException {
        try {
            return repository.saveAll(usuarios);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Usuario> getUsuarios() throws BusinessException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Usuario getUsuarioById(long id) throws BusinessException, NotFoundException {
        Optional<Usuario> opt=null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el usuario " + id);
        }
        return opt.get();
    }

    @Override
    public Usuario getUsuarioByUsername(String username) throws BusinessException, NotFoundException {
        return null;
    }

    @Override
    public void deleteUsuario(long id) throws BusinessException, NotFoundException {
        Optional<Usuario> opt=null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el usuario " + id);
        } else {
            try {
                repository.deleteById(id);
            } catch (Exception e1) {
                throw new BusinessException(e1.getMessage());
            }
        }
    }

    @Override
    public Usuario updateUsuario(Usuario usuario) throws BusinessException, NotFoundException {
        Optional<Usuario> opt=null;
        try {
            if (String.valueOf(usuario.getIdUsuario()).isEmpty()) {
                throw new BusinessException("El id del Usuario no debe estar vacío");
            }
            if (usuario.getIdUsuario() < 0) {
                throw new BusinessException("Id de Usuario inválido");
            }
            opt = repository.findById(usuario.getIdUsuario());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el usuario " + usuario.getIdUsuario());
        } else{
            try {

                Usuario usuarioExistente = new Usuario(
                        usuario.getIdUsuario(), usuario.getIdEmpleado(), usuario.getUsername(),
                        usuario.getPassword(), usuario.getActivo(), usuario.getBloqueado()
                );
                return repository.save(usuarioExistente);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }
}
