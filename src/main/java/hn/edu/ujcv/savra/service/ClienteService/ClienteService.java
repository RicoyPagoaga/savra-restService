package hn.edu.ujcv.savra.service.ClienteService;

import hn.edu.ujcv.savra.entity.Cliente;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.exceptions.SqlExceptions;
import hn.edu.ujcv.savra.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ClienteService implements IClienteService{
    @Autowired
    private ClienteRepository repository;
    @Override
    public Cliente saveCliente(Cliente pCliente) throws BusinessException, SQLException {
        try{
            //nombre
            if(pCliente.getNombre().isEmpty()){
                throw new BusinessException("Nombre Cliente esta Vacío");
            }
            if(pCliente.getNombre().length() < 3){
                throw new BusinessException("Nombre Cliente debe contener mínimo 3 carácteres");
            }
            if(pCliente.getNombre().length() >60){
                throw new BusinessException("Nombre Cliente debe contener menos de 60 carácteres");
            }
            //documento
            if(pCliente.getDocumento().isEmpty()){
                throw new BusinessException("Documento esta vacío");
            }
            if(pCliente.getDocumento().length() < 7){
                throw new BusinessException("Documento debe tener mínimo 7 carácteres");
            }
            if(pCliente.getDocumento().length() > 14){
                throw new BusinessException("Documento debe ser menor o igual a 14 carácteres");
            }
            //idTipoDocumento
            if(pCliente.getIdTipoDocumento() < 1) {
                throw new BusinessException("Id Documento vacío");
            }
            Pattern patron=Pattern.compile("[72389]");
            Matcher validarNumero = patron.matcher(pCliente.getTelefono().substring(0,1));
            //telefono
            if (pCliente.getTelefono().isEmpty()){
                throw new BusinessException("Teléfono esta vacío");
            }
            if (pCliente.getTelefono().length() != 8){
                throw new BusinessException("No. de teléfono debe ser igual a 8 dígitos");
            }
            Pattern pat = Pattern.compile("[\\d]*");
            Matcher mat = pat.matcher(pCliente.getTelefono());
            if(!mat.matches()){
                throw new BusinessException("No. de teléfono debe ser númerico");
            }
            if (!validarNumero.matches()){
                throw new BusinessException("No. de teléfono no pertenece a una operadora valida");
            }
            //direccion
            if (pCliente.getDireccion().isEmpty()){
                throw new BusinessException("Direccion del cliente esta vacío");
            }
            if (pCliente.getDireccion().length() < 6){
                throw new BusinessException("Direccion debe ser mayor a 6 carácteres");
            }
            if (pCliente.getDireccion().length() > 50){
                throw new BusinessException("Direccion debe ser menor a 50 Carácteres");
            }
            //idCategoria
            if (pCliente.getIdCategoria() < 1){
                throw new BusinessException("Id Categoría vacío");
            }
            return repository.save(pCliente);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Cliente> getClientes() throws BusinessException {
        return repository.findAll();
    }


    @Override
    public Cliente getClientebyNombre(long id) throws BusinessException, NotFoundException {
        return null;
    }

    @Override
    public void deleteCliente(long id) throws BusinessException, NotFoundException {
        Optional<Cliente> opt = null;
        try {
            opt = repository.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            throw new NotFoundException("No se encontró el cliente: " + id);
        } else {
            try {
                repository.deleteById(id);
            } catch (Exception e1) {
                throw new BusinessException(e1.getMessage());
            }
        }
    }

    @Override
    public Cliente UpdateCliente(Cliente pCliente) throws BusinessException, NotFoundException, SqlExceptions {
        Optional<Cliente> opt = null;
        try{
            //nombre
            if(pCliente.getNombre().isEmpty()){
                throw new BusinessException("Nombre Cliente esta Vacío");
            }
            if(pCliente.getNombre().length() < 3){
                throw new BusinessException("Nombre Cliente debe contener mínimo 3 carácteres");
            }
            if(pCliente.getNombre().length() >60){
                throw new BusinessException("Nombre Cliente debe contener menos de 60 carácteres");
            }
            //documento
            if(pCliente.getDocumento().isEmpty()){
                throw new BusinessException("Documento esta vacío");
            }
            if(pCliente.getDocumento().length() < 7){
                throw new BusinessException("Documento debe tener mínimo 7 carácteres");
            }
            if(pCliente.getDocumento().length() > 14){
                throw new BusinessException("Documento debe ser menor o igual a 14 carácteres");
            }
            //idTipoDocumento
            if(pCliente.getIdTipoDocumento() < 1){
                throw new BusinessException("Id Documento Invalido");
            }
            Pattern patron=Pattern.compile("[2389]");
            Matcher validarNumero = patron.matcher(pCliente.getTelefono().substring(0,1));
            //telefono
            if (pCliente.getTelefono().isEmpty()){
                throw new BusinessException("Teléfono esta vacío");
            }
            if (pCliente.getTelefono().length() != 8){
                throw new BusinessException("No. de teléfono debe ser igual a 8 dígitos");
            }
            Pattern pat = Pattern.compile("[\\d]*");
            Matcher mat = pat.matcher(pCliente.getTelefono());
            if(!mat.matches()){
                throw new BusinessException("No. de teléfono debe ser númerico");
            }
            if (!validarNumero.matches()){
                throw new BusinessException("No. de teléfono no pertenece a una operadora valida");
            }
            //direccion
            if (pCliente.getDireccion().isEmpty()){
                throw new BusinessException("Direccion del cliente esta vacío");
            }
            if (pCliente.getDireccion().length() < 6){
                throw new BusinessException("Direccion debe ser mayor a 6 carácteres");
            }
            if (pCliente.getDireccion().length() > 50){
                throw new BusinessException("Direccion debe ser menor a 50 Carácteres");
            }
            //idCategoria
            if (pCliente.getIdCategoria() < 0){
                throw new BusinessException("Id Categoria Invalido");
            }
            opt = repository.findById(pCliente.getIdCliente());
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("No se encontró el cliente:" + pCliente.getIdCliente());
        }else {
            try {
                Cliente newCliente = new Cliente();
                newCliente.setIdCliente(pCliente.getIdCliente());
                newCliente.setNombre(pCliente.getNombre());
                newCliente.setDocumento(pCliente.getDocumento());
                newCliente.setIdTipoDocumento(pCliente.getIdTipoDocumento());
                newCliente.setTelefono(pCliente.getTelefono());
                newCliente.setDireccion(pCliente.getDireccion());
                newCliente.setIdCategoria(pCliente.getIdCategoria());
                return repository.save(newCliente);
            }catch (Exception e1){
                throw new BusinessException(e1.getMessage());
            }
        }
    }
}