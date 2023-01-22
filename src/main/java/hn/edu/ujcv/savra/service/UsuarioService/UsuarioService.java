package hn.edu.ujcv.savra.service.UsuarioService;

import hn.edu.ujcv.savra.entity.Usuario;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UsuarioService implements IUsuarioService  {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public Usuario saveUsuario(Usuario usuario, boolean coinciden) throws BusinessException {
        try {
            if (!usuario.getPassword().trim().isEmpty()) {
                usuario.setPassword(encriptar(usuario.getPassword().trim()));
            }
            usuario.setNombre(usuario.getNombre().trim().toUpperCase());
            usuario.setApellido(usuario.getApellido().trim().toUpperCase());
            validarUsuario(usuario, coinciden);
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
        Optional<Usuario> opt=null;
        try {
            opt = repository.findByUsername(username);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el usuario");
        }
        return opt.get();
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
    public Usuario updateUsuario(Usuario usuario, boolean coinciden) throws BusinessException, NotFoundException {
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
                validarUsuario(usuario, coinciden);
                Usuario usuarioExistente = new Usuario(
                        usuario.getIdUsuario(), usuario.getUsername().trim(), usuario.getPassword().trim(),
                        usuario.getNombre().trim().toUpperCase(), usuario.getApellido().trim().toUpperCase(),
                        usuario.getActivo(), usuario.getBloqueado(),
                        usuario.getIdRol()
                );
                return repository.save(usuarioExistente);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }

    @Override
    public void activarDesactivarUsuario(int estado,long id) throws BusinessException, NotFoundException {
        Optional<Usuario> opt = null;
        try{
            opt = repository.findById(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("No se encontró el usuario: "+ id);
        }else {
            try {
                repository.activoUsuario(estado,id);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
    }

    @Override
    public void bloquearUsuario(int estado , String username)throws BusinessException,NotFoundException {
        Optional<Usuario> opt = null;
        try{
            opt = repository.findByUsername(username);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("No se encontró el usuario: "+ username);
        }else {
            try {
                repository.bloqueoUsuario(estado,username);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void validarUsuario(Usuario usuario, boolean coinciden) throws BusinessException {
        if(usuario.getNombre().trim().isEmpty()) {
            throw new BusinessException("El nombre es requerido");
        }
        if(usuario.getNombre().trim().length()<3) {
            throw new BusinessException("Ingrese más de tres caracteres en el nombre");
        }
        if(usuario.getNombre().trim().length()>50) {
            throw new BusinessException("El nombre no debe exceder los cincuenta caracteres");
        }
        Pattern patDoc = Pattern.compile("^[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+([a-zA-ZÀ-ÿ\\u00f1\\u00d1])*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+$");
        Matcher matDoc = patDoc.matcher(usuario.getNombre().trim());
        if(!matDoc.matches()){
            throw new BusinessException("Nombre no debe contener números, espacios o caracteres especiales ఠ_ఠ");
        }
        if(usuario.getNombre().trim().matches("(.)\\1{2,}")) {
            throw new BusinessException("El nombre no debe tener tantas letras repetidas ఠ_ఠ");
        }

        if(usuario.getApellido().trim().isEmpty()) {
            throw new BusinessException("El apellido es requerido");
        }
        if(usuario.getApellido().trim().length()<4) {
            throw new BusinessException("Ingrese más de cuatro caracteres en el apellido");
        }
        if(usuario.getApellido().trim().length()>50) {
            throw new BusinessException("El apellido no debe exceder los cincuenta caracteres");
        }
        Matcher mat = patDoc.matcher(usuario.getApellido().trim());
        if(!mat.matches()){
            throw new BusinessException("El apellido no debe contener números, espacios o caracteres especiales ఠ_ఠ");
        }
        if(usuario.getApellido().trim().matches("(.)\\1{2,}")) {
            throw new BusinessException("El apellido no debe tener tantas letras repetidas ఠ_ఠ");
        }

        if(usuario.getUsername().trim().isEmpty()) {
            throw new BusinessException("El nombre de Usuario es requerido");
        }
        if(usuario.getUsername().trim().length()<5) {
            throw new BusinessException("Ingrese más de cinco caracteres en el nombre de Usuario");
        }
        if(usuario.getUsername().trim().length()>50) {
            throw new BusinessException("El nombre de Usuario no debe exceder los cincuenta caracteres");
        }
        Matcher matD = patDoc.matcher(usuario.getUsername().trim());
        if(!matD.matches()){
            throw new BusinessException("El nombre de Usuario no debe contener números, espacios o caracteres especiales ఠ_ఠ");
        }
        if(usuario.getUsername().trim().matches("(.)\\1{2,}")) {
            throw new BusinessException("El nombre de Usuario no debe tener tantas letras repetidas ఠ_ఠ");
        }
        List<Usuario> usuarios = getUsuarios();
        for (Usuario item : usuarios) {
            if ((item.getUsername().equals(usuario.getUsername().trim())) && (item.getIdUsuario() != usuario.getIdUsuario())) {
                throw new BusinessException("El nombre del Usuario ya está en uso");
            }
        }

        if(usuario.getPassword().trim().isEmpty()) {
            throw new BusinessException("La contraseña es requerida");
        }
        if(usuario.getPassword().trim().length()<5) {
            throw new BusinessException("Debe ingresar por lo menos cinco caracteres en la contraseña");
        }
        if(usuario.getPassword().trim().length()>100) {
            throw new BusinessException("Contraseña inválida");
        }
        if(!coinciden) {
            throw new BusinessException("Las contraseñas no coinciden");
        }

        if(usuario.getIdRol()<1) {
            throw new BusinessException("Rol inválido");
        }
        if(usuario.getUsername().trim().equals("admin") && usuario.getBloqueado()==1) {
            throw new BusinessException("Usuario admin no puede ser bloqueado");
        }
    }

    private String encriptar(String password) throws UnsupportedEncodingException {
        return Base64.getEncoder().encodeToString(password.getBytes("utf-8"));
    }

    public boolean validarLogin(String username, String password) throws BusinessException, NotFoundException {
        boolean coincide = false;
        Optional<Usuario> opt = null;
        try{
            opt = repository.findByUsername(username);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (opt.isPresent()) {
            try {
                String cryptPassword = encriptar(password);
                if(cryptPassword.equals(opt.get().getPassword()) && opt.get().getActivo()==1)
                    coincide=true;
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
        return coincide;
    }

    public boolean validarUsuarioBloqueado(String username) throws BusinessException, NotFoundException {
        boolean coincide = false;
        Optional<Usuario> opt = null;
        try{
            opt = repository.findByUsername(username);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (opt.isPresent()) {
            try {
                if(opt.get().getBloqueado()==1 && opt.get().getActivo()==1)
                    coincide=true;
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
        return coincide;
    }
}
