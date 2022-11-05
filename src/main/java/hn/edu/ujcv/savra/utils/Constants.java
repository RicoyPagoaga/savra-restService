package hn.edu.ujcv.savra.utils;


public class Constants {
    private static final String URL_API_BASE     = "/api";
    private static final String URL_API_VERSION  = "v1";
    private static final String URL_BASE         = URL_API_BASE+URL_API_VERSION;
    public  static final String URL_BASE_PAISES  = String.format("%s/paises",URL_BASE);
    public  static final String URL_BASE_CATEGORIA_CLIENTES  = String.format("%s/categoria_clientes",URL_BASE);
    public  static final String URL_BASE_CLIENTES  = String.format("%s/clientes",URL_BASE);
}
