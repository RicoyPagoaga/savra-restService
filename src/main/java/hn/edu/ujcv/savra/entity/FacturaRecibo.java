package hn.edu.ujcv.savra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;

public interface FacturaRecibo {
     String getCai();
     String getNoFactura();
     String getFechaLimite();
     String getCliente();
     String getFechaFactura();
     String getEmpleado();
     String getTipoEntrega();
     String getShipper();
     double getCostoEnvio();
     String getFechaDespacho();
     String getFechaEntrega();
     String getMetodoPago();
     String getNoTarjeta();
     double getEfectivo();
     String getCupon();
     String getRangoInicial();
     String getRangoFinal();
}
