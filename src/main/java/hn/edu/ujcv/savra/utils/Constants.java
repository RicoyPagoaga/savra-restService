package hn.edu.ujcv.savra.utils;


public class Constants {
    private static final String URL_API_BASE     = "/api";
    private static final String URL_API_VERSION  = "v1";
    private static final String URL_BASE         = URL_API_BASE+URL_API_VERSION;
    public  static final String URL_BASE_PAISES  = String.format("%s/paises",URL_BASE);
    public  static final String URL_BASE_CATEGORIA_CLIENTES  = String.format("%s/categoria_clientes",URL_BASE);
    public  static final String URL_BASE_CLIENTES  = String.format("%s/clientes",URL_BASE);
    public static final  String URL_BASE_EMPLEADOS= String.format("%s/empleados",URL_BASE);
    public static final  String URL_BASE_TIPO_DOCUMENTO= String.format("%s/tipoDocumento",URL_BASE);
    public static final String URL_BASE_PROVEEDORES = String.format("%s/proveedores", URL_BASE);
    public static final String URL_BASE_MARCAS      = String.format("%s/marcas", URL_BASE);
    public static final String URL_BASE_MODELOS     = String.format("%s/modelos", URL_BASE);
    public static final String URL_BASE_TRANSMISIONES     = String.format("%s/transmisiones", URL_BASE);
    public static final String URL_BASE_REPUESTOS   = String.format("%s/repuestos", URL_BASE);
    public static final String URL_BASE_CATEGORIA_REPUESTOS   = String.format("%s/categoria_repuestos", URL_BASE);
    public static final String URL_BASE_USUARIOS   = String.format("%s/usuarios", URL_BASE);
    public static final String URL_BASE_CARGO   = String.format("%s/cargos", URL_BASE);
    public static final String URL_BASE_TIPO_ENTREGA    = String.format("%s/tipos_entrega", URL_BASE);
    public static final String URL_BASE_METODO_PAGO     = String.format("%s/metodos_pago", URL_BASE);
    public static final String URL_BASE_PRECIO_REPUESTO = String.format("%s/precios_repuesto", URL_BASE);
    public static final String URL_BASE_IMPUESTO        = String.format("%s/impuestos", URL_BASE);
}
