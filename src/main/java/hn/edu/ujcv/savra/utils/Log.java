package hn.edu.ujcv.savra.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    FileHandler fh;
    public Logger logger;

    public Log() {
    }
    private String ObtenerNombreArchivo(String nombre){
        String salida = "";
        String pattern = "dd_MM_YYYY HH_mm_ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String date = sdf.format(new Date());
        salida = nombre+" "+date+".log";
        return salida;
    }
    public boolean CrearArchivo(String nameClass){
        boolean flag = false;
        String nombreArchivo = ObtenerNombreArchivo(nameClass);
        File f = new File(nombreArchivo);
        if(!f.exists()){
            try{
                f.createNewFile();
                flag = true;
                fh = new FileHandler(nombreArchivo);
                logger = Logger.getLogger(nameClass);
                logger.addHandler(fh);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
}
