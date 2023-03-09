package hn.edu.ujcv.savra.service.PaisService;

import hn.edu.ujcv.savra.entity.Modelo;
import hn.edu.ujcv.savra.entity.Pais;
import hn.edu.ujcv.savra.entity.TipoDocumento;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.PaisRepository;
import hn.edu.ujcv.savra.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.*;

@Service
public class PaisService implements IPaisService{

    @Autowired
    private PaisRepository repository;
    private Log mi_log = new Log();
    @Override
    public Pais savePais(Pais pPais) throws BusinessException, SQLException {
        try{
            //cod_iso
            if(pPais.getCod_iso().trim().isEmpty()){
                throw new BusinessException("Código ISO esta Vacío.");
            }
            if(pPais.getCod_iso().trim().length() != 2){
                throw new BusinessException("Utilice el código ISO de 2 letras.");
            }
            //Pattern patDoc = Pattern.compile("[a-zA-Z]*");

            //Pattern pat = Pattern.compile("[\\d]*");
            Pattern pat = Pattern.compile("[a-zA-Z]*");
            Matcher mat = pat.matcher(pPais.getCod_iso().trim());
            if(!mat.matches()){
                throw new BusinessException("Código ISO no debe ser númerico, ni caracteres especiales.");
            }

            //nombre
            if(pPais.getNombre().trim().isEmpty()){
                throw new BusinessException("Nombre País esta vacío.");
            }
            Pattern pat3 = Pattern.compile("[a-zA-ZñÑáéíóúÁÉÍÓÚ ]*");
            Matcher mat1 = pat3.matcher(pPais.getNombre().trim());
            if(!mat1.matches()){
                throw new BusinessException("Nombre no debe contener números, ni caracteres especiales");
            }
            if(pPais.getNombre().trim().length() < 4){
                throw new BusinessException("Nombre País debe contener más de 4 caracteres ఠ_ఠ");
            }
            if(pPais.getNombre().trim().length() >56){
                throw new BusinessException("Nombre País debe contener menos de 56 caracteres ఠ_ఠ");
            }
            Pattern dobleEspacio = Pattern.compile("\\s{2,}");
            if (dobleEspacio.matcher(pPais.getNombre().trim()).find()) {
                throw new BusinessException("Nombre no debe contener espacios dobles ఠ_ఠ");
            }

            //cod_area
            if(pPais.getCod_area().trim().isEmpty()){
                throw new BusinessException("Código de área esta vacío ఠ_ఠ");
            }
            if(pPais.getCod_area().trim().length() < 1){
                throw new BusinessException("Código de área debe contener al menos un caracterఠ_ఠ");
            }
            if (pPais.getCod_area().trim().charAt(0)=='0'){
                throw new BusinessException("Código de área inválido, no debe comenzar con '0' ");
            }
            if(pPais.getCod_area().trim().length() > 3){
                throw new BusinessException("Código de área no debe contener mas de 3 caracteresఠ_ఠ");
            }
            Pattern pat1 = Pattern.compile("[\\d]*");
            Matcher mat2 = pat1.matcher(pPais.getCod_area().trim());
            if(!mat2.matches()){
                throw new BusinessException("Código de área debe ser númerico (no es necesario utilizar '+')");
            }
            pPais.setCod_iso(pPais.getCod_iso().toUpperCase());
            pPais.setNombre(pPais.getNombre().toUpperCase());
            List<Pais> paises = getPaises();
            for (Pais item : paises) {
                if (item.getCod_iso().equals(pPais.getCod_iso().trim())){
                    throw new BusinessException("Ya existe un registro, con este código ISO");
                }
                if (item.getNombre().equals(pPais.getNombre().trim())){
                    throw new BusinessException("Ya existe un registro, con este nombre");
                }
                if ( item.getCod_area().equals(pPais.getCod_area().trim())) {
                    throw new BusinessException("Ya existe un registro, con este código de área");
                }
            }
            return repository.save(pPais);
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }
    @Override
    public List<Pais> getPaises() throws BusinessException {
        return repository.findAll();
    }

    @Override
    public Pais getPaisById(long iso) throws BusinessException, NotFoundException {
        Optional<Pais> opt=null;
        try {
            opt = repository.findById(iso);
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró el pais " + iso);
            throw new NotFoundException("No se encontró el pais " + iso);
        }
        return opt.get();
    }

    @Override
    public Pais getPaisByNombre(String nombre) throws BusinessException, NotFoundException {
        Optional<Pais> opt=null;
        try {
            opt = repository.findByNombre(nombre);
        } catch (Exception e) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()) {
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró el país " + nombre);
            throw new NotFoundException("No se encontró el país " + nombre);
        }
        return opt.get();
    }

    @Override
    public void detelePais(long id) throws BusinessException, NotFoundException {
        Optional<Pais> opt = null;
        try{
            opt = repository.findById(id);
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró el país: " + id);
            throw new NotFoundException("No se encontró el país: " + id);
        }else{
            try {
                repository.deleteById(id);
            }catch (Exception e1){
                mi_log.CrearArchivo(this.getClass().getSimpleName());
                mi_log.logger.severe(e1.getMessage());
                throw new BusinessException(e1.getMessage());
            }
        }
    }

    @Override
    public Pais updatePais(Pais pPais) throws BusinessException, NotFoundException {
        Optional<Pais> opt = null;
        try{
            //cod_iso
            if(pPais.getCod_iso().isEmpty()){
                throw new BusinessException("Código ISO esta Vacío");
            }
            if(pPais.getCod_iso().length() != 2){
                throw new BusinessException("Utilice el código de 2 letras");
            }
            //nombre
            if(pPais.getNombre().isEmpty()){
                throw new BusinessException("Nombre País esta vacío");
            }
            if(pPais.getNombre().length() < 4){
                throw new BusinessException("Nombre País debe contener más de 4 caracteres");
            }
            if(pPais.getNombre().length() >56){
                throw new BusinessException("Nombre País debe contener menos de 56 caracteres");
            }
            Pattern dobleEspacio = Pattern.compile("\\s{2,}");
            if (dobleEspacio.matcher(pPais.getNombre()).find()) {
                throw new BusinessException("Nombre no debe contener espacios dobles ఠ_ఠ");
            }
            //cod_area
            if(pPais.getCod_area().isEmpty()){
                throw new BusinessException("Código de área esta vacío");
            }
            if(pPais.getCod_area().length() < 1){
                throw new BusinessException("Código de área debe contener al menos un caracter");
            }
            if(pPais.getCod_area().length() > 3){
                throw new BusinessException("Código de área no debe contener mas de 3 caracteres");
            }
            Pattern pat = Pattern.compile("[\\d]*");
            Matcher mat = pat.matcher(pPais.getCod_area());
            if(!mat.matches()){
                mi_log.CrearArchivo(this.getClass().getSimpleName());
                mi_log.logger.severe("Código de área debe ser númerico (no es necesario utilizar '+')");
                throw new BusinessException("Código de área debe ser númerico (no es necesario utilizar '+')");
            }
            opt = repository.findById(pPais.getIdPais());
        }catch (Exception e){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            mi_log.CrearArchivo(this.getClass().getSimpleName());
            mi_log.logger.severe("No se encontró el país:" + pPais.getIdPais());
            throw new NotFoundException("No se encontró el país:" + pPais.getIdPais());
        }else {
            try {
                Pais newPais = new Pais();
                newPais.setIdPais(pPais.getIdPais());
                newPais.setNombre(pPais.getNombre().toUpperCase());
                newPais.setCod_iso(pPais.getCod_iso().toUpperCase());
                newPais.setCod_area(pPais.getCod_area());
                return repository.save(newPais);
            }catch (Exception e1){
                mi_log.CrearArchivo(this.getClass().getSimpleName());
                mi_log.logger.severe(e1.getMessage());
                throw new BusinessException(e1.getMessage());
            }
        }
    }
}
