package hn.edu.ujcv.savra.service.ShepperService;

import hn.edu.ujcv.savra.entity.Shipper;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.ShipperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ShipperService implements IShipperService{
    @Autowired
    private ShipperRepository repository;
    @Override
    public Shipper saveShipper(Shipper shipper) throws BusinessException {
        try {
            validarShipper(shipper);
            return repository.save(shipper);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Shipper> saveShippers(List<Shipper> shippers) throws BusinessException {
        try {
            return repository.saveAll(shippers);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Shipper> getShippers() throws BusinessException {
        try {
            return repository.findAll();

        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Shipper getShipperById(long id) throws BusinessException,NotFoundException {
        Optional<Shipper> opt=null;
        try {
            opt=repository.findById(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }if (!opt.isPresent()){
            throw new NotFoundException("No se encontrĂ³ el shipper"+id);
        }
        return opt.get();

    }

    @Override
    public Shipper getShipperByNombre(String nombre) throws BusinessException, NotFoundException {
        Optional<Shipper> opt= null;
        try {
            opt=repository.findShipperByCorreo(nombre);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el shipper :v"+nombre);
        }
        return opt.get();
    }

    @Override
    public void deleteShipper(long id) throws BusinessException, NotFoundException {
        Optional<Shipper> opt= null;
        try {
            opt=repository.findById(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el shipper"+id);
        }
        else {
            try {
                repository.deleteById(id);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
    }

    @Override
    public Shipper updateShipper(Shipper shipper) throws BusinessException, NotFoundException {
        Optional<Shipper> opt= null;
        try {
            opt=repository.findById(shipper.getIdShipper());
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("no se encontro el shipper"+shipper.getIdShipper());
        }
        else {
            try {
                validarShipper(shipper);
                Shipper existinShipper=new Shipper();
                existinShipper.setIdShipper(shipper.getIdShipper());
                existinShipper.setNombre(shipper.getNombre());
                existinShipper.setTelefono(shipper.getTelefono());
                existinShipper.setCorreo(shipper.getCorreo());
                existinShipper.setSitioWeb(shipper.getSitioWeb());
                existinShipper.setFechaContrato(shipper.getFechaContrato());
                return repository.save(existinShipper);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private void validarShipper(Shipper shipper) throws BusinessException{
        //nombre
        if (shipper.getNombre().trim().isEmpty()) {
            throw new BusinessException("El nombre del shipper es requerido à° _à° ");
        }
        if (shipper.getNombre().trim().length() < 5) {
            throw new BusinessException("Ingrese mĂ¡s de cinco caracteres en el nombre del shipper à° _à° ");
        }
        if (shipper.getNombre().trim().length() > 80) {
            throw new BusinessException("No debe tener menos de 80 caracteres à° _à° ");
        }
        Pattern patDoc = Pattern.compile("^[a-zA-ZĂ€-Ă¿\\u00f1\\u00d1]+(\\s[a-zA-ZĂ€-Ă¿\\u00f1\\u00d1])*[a-zA-ZĂ€-Ă¿\\u00f1\\u00d1]+$");
        Matcher matDoc = patDoc.matcher(shipper.getNombre().trim());
        if(!matDoc.matches()){
            throw new BusinessException("Nombre del shipper no debe contener nĂºmeros o caracteres especiales ni espacios dobles à° _à° ");
        }

        //telefono
        Pattern patron=Pattern.compile("[27389]");
        Matcher validarNumero = patron.matcher(shipper.getTelefono().substring(0,1));

        if (shipper.getTelefono().trim().isEmpty()) {
            throw new BusinessException("El telĂ©fono no debe estar vacĂ­o à° _à° ");
        }
        if (shipper.getTelefono().trim().length() != 8){
            throw new BusinessException("No. de telĂ©fono debe ser igual a 8 carĂ¡cteres à° _à° ");
        }
        if (!validarNumero.matches()){
            throw new BusinessException("No. de telĂ©fono no pertenece a una operadora vĂ¡lida à° _à° ");
        }
        //Correo
        if (shipper.getCorreo().trim().isEmpty()) {
            throw new BusinessException("El correo no debe estar vacĂ­o à° _à° ");
        }
        if (shipper.getCorreo().trim().length() < 3) {
            throw new BusinessException("Ingrese mĂ¡s de tres caracteres en el correo à° _à° ");
        }
        if (shipper.getCorreo().trim().length() > 75) {
            throw new BusinessException("Debe ingresar menos de 75 caracteres en el correo à° _à° ");
        }
        if (!validarCorreo(shipper.getCorreo())) {
            throw new BusinessException("La direcciĂ³n de correo es invĂ¡lida à° _à° ");
        }
        //sitioWeb
        if (shipper.getSitioWeb().trim().isEmpty()) {
            throw new BusinessException("El sitio web es requerido à° _à° ");
        }
        if (shipper.getSitioWeb().trim().length() < 3) {
            throw new BusinessException("Ingrese mĂ¡s de tres caracteres en el sitio web à° _à° ");
        }
        if (shipper.getSitioWeb().trim().length() > 75) {
            throw new BusinessException("El sitio web no debe exceder los 75 caracteres à° _à° ");
        }
        if (!validarSitioWeb(shipper.getSitioWeb())) {
            throw new BusinessException("EL sitio web es invĂ¡lido à° _à° ");
        }

        //sitioWeb
        if (shipper.getSitioWeb().trim().isEmpty()) {
            throw new BusinessException("El sitio web es requerido à° _à° ");
        }
        if (shipper.getSitioWeb().trim().length() < 3) {
            throw new BusinessException("Ingrese mĂ¡s de tres caracteres en el sitio web à° _à° ");
        }
        if (shipper.getSitioWeb().trim().length() > 75) {
            throw new BusinessException("El sitio web no debe exceder los 75 caracteres à° _à° ");
        }
        if (!validarSitioWeb(shipper.getSitioWeb())) {
            throw new BusinessException("EL sitio web es invĂ¡lido à° _à° ");
        }

        //fechaContrato
        if (shipper.getFechaContrato()==null){
            throw new BusinessException("Fecha de contrato esta vacĂ­a à° _à° ");
        }
        if (!shipper.getFechaContrato().equals( LocalDate.now())){
            throw new BusinessException("El contrato no debe ser distinta a la fecha actual à° _à° ");
        }


    }
    private boolean validarCorreo(String correo) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]{3,}+(\\.[_A-Za-z0-9-]{3,}+)*@" +
                "[A-Za-z0-9-]{2,}+(\\.[A-Za-z0-9]{2,}+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(correo);

        return matcher.find();
    }


    private boolean validarSitioWeb(String sitio) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]{3,}+(\\.[_A-Za-z0-9-]{2,}+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(sitio);

        return matcher.find();
    }
}
