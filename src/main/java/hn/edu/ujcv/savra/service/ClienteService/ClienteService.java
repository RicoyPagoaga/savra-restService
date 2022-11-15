package hn.edu.ujcv.savra.service.ClienteService;

import hn.edu.ujcv.savra.entity.Cliente;
import hn.edu.ujcv.savra.entity.TipoDocumento;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.exceptions.SqlExceptions;
import hn.edu.ujcv.savra.repository.ClienteRepository;
import hn.edu.ujcv.savra.repository.TipoDocumentoRepository;
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
        //TipoDocumento doc1 = buscarDocumento(pCliente.getIdCliente());
        try{
            //nombre
            if(pCliente.getNombre().trim().isEmpty()){
                throw new BusinessException("Nombre Cliente esta Vacío ఠ_ఠ");
            }
            if(pCliente.getNombre().trim().length() < 3){
                throw new BusinessException("Nombre Cliente debe contener mínimo 3 carácteres ఠ_ఠ");
            }
            if(pCliente.getNombre().trim().length() >60){
                throw new BusinessException("Nombre Cliente debe contener menos de 60 carácteres ఠ_ఠ");
            }
            Pattern patDoc = Pattern.compile("^([a-zA-Z]+)(\\s[a-zA-Z]+)*$");
            Matcher matDoc = patDoc.matcher(pCliente.getNombre().trim());
            if(!matDoc.matches()){
                throw new BusinessException("Nombre Cliente no debe contener números o letras con tildeఠ_ఠ");
            }
            //documento
            if(pCliente.getDocumento().trim().isEmpty()){
                throw new BusinessException("Documento esta vacío ఠ_ఠ");
            }
            //idTipoDocumento
            if(pCliente.getIdTipoDocumento() < 1) {
                throw new BusinessException("Id Documento vacío");
            }
            contiene(pCliente.getIdTipoDocumento(), pCliente.getDocumento().trim());
            //telefono
            if (pCliente.getTelefono().trim().isEmpty()){
                throw new BusinessException("Teléfono esta vacío");
            }
            Pattern patron=Pattern.compile("[72389]");
            Matcher validarNumero = patron.matcher(pCliente.getTelefono().substring(0,1));
            if (pCliente.getTelefono().trim().length() != 8){
                throw new BusinessException("No. de teléfono debe ser igual a 8 dígitos ఠ_ఠ");
            }
            Pattern pat = Pattern.compile("[\\d]*");
            Matcher mat = pat.matcher(pCliente.getTelefono().trim());
            if(!mat.matches()){
                throw new BusinessException("No. de teléfono debe ser númerico ఠ_ఠ");
            }
            if (!validarNumero.matches()){
                throw new BusinessException("No. de teléfono no pertenece a una operadora valida ఠ_ఠ");
            }
            //direccion
            if (pCliente.getDireccion().trim().isEmpty()){
                throw new BusinessException("Direccion del cliente esta vacío ఠ_ఠ");
            }
            if (pCliente.getDireccion().trim().length() < 6){
                throw new BusinessException("Direccion debe ser mayor a 6 carácteres ఠ_ఠ");
            }
            if (pCliente.getDireccion().trim().length() > 50){
                throw new BusinessException("Direccion debe ser menor a 50 Carácteres ఠ_ఠ");
            }
            //idCategoria
            if (pCliente.getIdCategoria() < 1){
                throw new BusinessException("Id Categoría vacío");
            }
            pCliente.setNombre(pCliente.getNombre().trim().toUpperCase());
            return repository.save(pCliente);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    private void validarNumerosDocumento(String nombreDocumento) throws BusinessException {
        Pattern pat = Pattern.compile("[\\d]*");
        Matcher mat = pat.matcher(nombreDocumento);
        if(!mat.matches()){
            throw new BusinessException("Documento debe ser númerico");
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
            if(pCliente.getNombre().trim().isEmpty()){
                throw new BusinessException("Nombre Cliente esta Vacío ఠ_ఠ");
            }
            if(pCliente.getNombre().trim().length() < 3){
                throw new BusinessException("Nombre Cliente debe contener mínimo 3 carácteres ఠ_ఠ");
            }
            if(pCliente.getNombre().trim().length() >60){
                throw new BusinessException("Nombre Cliente debe contener menos de 60 carácteres ఠ_ఠ");
            }
            Pattern patDoc = Pattern.compile("^([a-zA-Z]+)(\\s[a-zA-Z]+)*$");
            Matcher matDoc = patDoc.matcher(pCliente.getNombre().trim());
            if(!matDoc.matches()){
                throw new BusinessException("Nombre Cliente no debe contener números o letras con tilde ఠ_ఠ");
            }
            //documento
            if(pCliente.getDocumento().trim().isEmpty()){
                throw new BusinessException("Documento esta vacío ఠ_ఠ");
            }
            //idTipoDocumento
            if(pCliente.getIdTipoDocumento() < 1) {
                throw new BusinessException("Id Documento vacío");
            }
            contiene(pCliente.getIdTipoDocumento(), pCliente.getDocumento().trim());
            //telefono
            if (pCliente.getTelefono().trim().isEmpty()){
                throw new BusinessException("Teléfono esta vacío");
            }
            Pattern patron=Pattern.compile("[72389]");
            Matcher validarNumero = patron.matcher(pCliente.getTelefono().substring(0,1));
            if (pCliente.getTelefono().trim().length() != 8){
                throw new BusinessException("No. de teléfono debe ser igual a 8 dígitos ఠ_ఠ");
            }
            Pattern pat = Pattern.compile("[\\d]*");
            Matcher mat = pat.matcher(pCliente.getTelefono().trim());
            if(!mat.matches()){
                throw new BusinessException("No. de teléfono debe ser númerico ఠ_ఠ");
            }
            if (!validarNumero.matches()){
                throw new BusinessException("No. de teléfono no pertenece a una operadora valida ఠ_ఠ");
            }
            //direccion
            if (pCliente.getDireccion().trim().isEmpty()){
                throw new BusinessException("Direccion del cliente esta vacío ఠ_ఠ");
            }
            if (pCliente.getDireccion().trim().length() < 6){
                throw new BusinessException("Direccion debe ser mayor a 6 carácteres ఠ_ఠ");
            }
            if (pCliente.getDireccion().trim().length() > 50){
                throw new BusinessException("Direccion debe ser menor a 50 Carácteres ఠ_ఠ");
            }
            //idCategoria
            if (pCliente.getIdCategoria() < 1){
                throw new BusinessException("Id Categoría vacío");
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
                newCliente.setNombre(pCliente.getNombre().toUpperCase());
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
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;
    private TipoDocumento buscarDocumento(long id) throws BusinessException, NotFoundException {
        Optional<TipoDocumento> opt=null;
        try {
            opt = tipoDocumentoRepository.findById(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("No se encontro el documento" + id);
        }
        System.out.println(opt.get().getNombreDocumento());
        return opt.get();
    }
    private void contiene(long id, String nombreDoc) throws BusinessException {
        try {
            Pattern pat = Pattern.compile("[\\d]*");
            Matcher mat = pat.matcher(nombreDoc);
            if (buscarDocumento(id).getNombreDocumento().contains("DNI")){
                if(!mat.matches()){
                    throw new BusinessException("DNI debe ser númerico");
                }
                if (nombreDoc.length() != 13){
                    throw new BusinessException("Cantidad de caracteres DNI invalido!");
                }
            }else if (buscarDocumento(id).getNombreDocumento().contains("RTN")){
                if(!mat.matches()){
                    throw new BusinessException("RTN debe ser númerico");
                }
                if (nombreDoc.length() != 14){
                    throw new BusinessException("Cantidad de caracteres RTN invalido!");
                }
            }else {
                if (nombreDoc.length() < 7){
                    System.out.println(nombreDoc.length());
                    throw new BusinessException("Documento no debe ser menor a 7 de caracteres !");
                }
            }
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }
}