package hn.edu.ujcv.savra.entity;

import lombok.Data;
import javax.persistence.Entity;


public interface FacturaDetalleRecibo {
     String getRepuesto();
     int getCantidad();
     double getPrecio();
     double getImporte();
     double getImpuesto();
     double getDescuento();
}
