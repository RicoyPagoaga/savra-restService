package hn.edu.ujcv.savra.service.ParametroFacturaService;

import hn.edu.ujcv.savra.entity.ParametroFactura;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;
import hn.edu.ujcv.savra.repository.ParametroFacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ParametroFacturaService implements IParametroFacturaService{
    @Autowired
    private ParametroFacturaRepository repository;

    @Override
    public ParametroFactura saveParametro(ParametroFactura pParametro) throws BusinessException {
        try {
            pParametro.setCai(pParametro.getCai().toUpperCase());
            validarParametro(pParametro);
            if (!pParametro.getFechaInicio().isEqual(LocalDate.now())){
                throw new BusinessException("Fecha Inicio debe der la fecha actual");
            }
            return repository.save(pParametro);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public ParametroFactura updateParametro(ParametroFactura pParametro) throws BusinessException, NotFoundException {
        Optional<ParametroFactura> opt = null;
        try{
            opt = repository.findById(pParametro.getIdParametro());
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("No se encontró el Parámetro: "+pParametro.getIdParametro());
        }else {
            try{
                pParametro.setCai(pParametro.getCai().toUpperCase());
                validarParametro(pParametro);
                ParametroFactura newParametro = new ParametroFactura(pParametro.getIdParametro(), pParametro.getCai(),
                        pParametro.getRangoInicial(), pParametro.getRangoFinal(), pParametro.getFechaLimiteEmision(),
                        pParametro.getFechaInicio(), pParametro.getUltimaFactura());
                return repository.save(newParametro);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }

        }
    }

    @Override
    public List<ParametroFactura> getParametrosFactura() throws BusinessException {
        return repository.findAll();
    }

    @Override
    public void deleteParametro(long idParametro) throws BusinessException, NotFoundException {
        Optional<ParametroFactura> opt = null;
        try {
            opt = repository.findById(idParametro);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("No se encontró el parámetro: "+ idParametro);
        }else {
            try {
                repository.deleteById(idParametro);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
    }

    @Override
    public ParametroFactura getParametroByCai(String pCai) throws BusinessException, NotFoundException {
        Optional<ParametroFactura> opt = null;
        try {
            opt = repository.findFirstByCai(pCai);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if (!opt.isPresent()){
            throw new NotFoundException("No se encontró el parámetro: "+pCai);
        }else {
            return opt.get();
        }
    }

    void validarParametro(ParametroFactura pParametro)throws BusinessException {
        if (pParametro.getCai().isEmpty()){
            throw new BusinessException("CAI Vacío");
        }
        Pattern numerosCai = Pattern.compile("[0-9]+");
        if(!numerosCai.matcher(pParametro.getCai().trim()).find()){
            throw new BusinessException("CAI debe contener almenos un número");
        }
        Pattern letrasCai = Pattern.compile("[a-zA-Z]+");
        if(!letrasCai.matcher(pParametro.getCai().trim()).find()){
            throw new BusinessException("CAI debe contener almenos una letra");
        }
        if (pParametro.getCai().length() != 37){
            throw new BusinessException("CAI incompleto seguir ejemplo");
        }
        if (pParametro.getRangoInicial().isEmpty()){
            throw new BusinessException("Rango Inicial Vacío");
        }
        if (pParametro.getRangoInicial().length() != 19 ){
            throw new BusinessException("Rango Inical incompleto, seguir ejemplo");
        }
        if (pParametro.getRangoFinal().isEmpty()){
            throw new BusinessException("Rango Final Vacío");
        }
        if (pParametro.getRangoFinal().length() != 19 ){
            throw new BusinessException("Rango Final incompleto, seguir ejemplo");
        }
        String[] item = pParametro.getRangoFinal().split("-");
        String[] item2 = pParametro.getRangoInicial().split("-");

        if(Integer.parseInt(item2[0]) > 20){
            throw new BusinessException("Punto de Emisión en Rango Inicial no debe ser mayor de 020 , verifique 1er segmento!");
        }
        if(Integer.parseInt(item2[0]) < 1){
            throw new BusinessException("Punto de Emisión en Rango Inicial no debe ser menor de 01 , verifique 1er segmento!");
        }
        if(Integer.parseInt(item2[1]) > 1){
            throw new BusinessException("Establecimiento en Rango Inicial no debe ser mayor de 001 , verifique 2do segmento!");
        }
        if(Integer.parseInt(item2[1]) < 1){
            throw new BusinessException("Establecimiento en Rango Inicial no debe ser menor de 001 , verifique 2do segmento!");
        }
        if(Integer.parseInt(item2[2]) > 1){
            throw new BusinessException("Tipo Documento en Rango Inicial no debe ser mayor de 01 , verifique 3er segmento!");
        }
        if(Integer.parseInt(item2[2]) < 1){
            throw new BusinessException("Tipo Documento en Rango Inicial no debe ser menor de 01 , verifique 3er segmento!");
        }
        if(Integer.parseInt(item2[3]) < 1){
            throw new BusinessException("Numeración correlativa en rango inicial no debe ser menor de 000000001 , verifique 4to segmento!");
        }
        if(Integer.parseInt(item2[3]) > 1000000){
            throw new BusinessException("Numeración correlativa en rango inicial no debe ser mayor de 001000000 , verifique 4to segmento!");
        }
        for (int i=0;i<item.length-1;i++){
            if (!item[i].equals(item2[i])){
                throw new BusinessException("Rango final no acorde a rango inicial! verifique los primeros 3 segmentos");
            }
        }
        if (Integer.parseInt(item[3]) < Integer.parseInt(item2[3])+100){
            throw new BusinessException("Rango final no debe ser menor de 100 del rango inicial, verifique 4to segmento");
        }
        if (Integer.parseInt(item[3]) > Integer.parseInt(item2[3])+100000){
            throw new BusinessException("Rango final no debe ser mayor de 100,000 del rango inicial, verifique 4to segmento");
        }
        if (pParametro.getFechaLimiteEmision() == null){
            throw new BusinessException("Fecha límite de emisión esta vacía");
        }
        if (pParametro.getFechaLimiteEmision().isBefore(pParametro.getFechaInicio().plusMonths(5))){
            throw new BusinessException("Fecha límite de emisión no debe ser menor de 5 meses");
        }
        if (pParametro.getFechaLimiteEmision().isAfter(LocalDate.now().plusMonths(7))){
            throw new BusinessException("Fecha límite de emisión no debe ser mayor de 7 meses");
        }
        if (pParametro.getFechaInicio() == null){
            throw new BusinessException("Fecha Inicio esta vacía actual");
        }
    }
}
