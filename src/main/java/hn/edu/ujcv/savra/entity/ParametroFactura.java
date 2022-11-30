package hn.edu.ujcv.savra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parametrosFactura")
public class ParametroFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idParametro;
    private String cai ;
    private String rangoInicial;
    private String rangoFinal;
    private LocalDate fechaLimiteEmision= null;
    private LocalDate fechaInicio=null;
    private long ultimaFactura;
}
