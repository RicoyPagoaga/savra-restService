package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.FacturaDetalle;
import hn.edu.ujcv.savra.entity.FacturaDetalleRecibo;
import hn.edu.ujcv.savra.entity.FacturaRecibo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FacturaDetalleRepository extends JpaRepository<FacturaDetalle,Long> {
    List<FacturaDetalle> findByIdFactura(long idFactura);

    @Query(value = "Select R.nombre as repuesto, FD.cantidad as cantidad,PH.precio as precio,(FD.cantidad*PH.precio) as importe,FD.cantidad*PH.precio*CONVERT(FLOAT,IH.valor)/100 as impuesto,\n" +
            "FD.cantidad*PH.precio*CONVERT(FLOAT,FD.descuento)/100 as descuento\n" +
            "from facturaDetalle as FD\n" +
            "inner join factura as F ON FD.idFactura = F.idFactura\n" +
            "inner join repuesto as R ON FD.idRepuesto = R.idRepuesto\n" +
            "inner join precioHistoricoRepuesto as PH ON R.idRepuesto = PH.idRepuesto\n" +
            "inner join impuesto as I ON R.idImpuesto = I.idImpuesto\n" +
            "inner join impuestoHistorico as IH ON I.idImpuesto = IH.idImpuesto\n" +
            "where FD.idFactura= :idFact AND F.fechaFactura Between PH.fechaInicio AND COALESCE(PH.fechaFinal,GETDATE())\n" +
            "AND F.fechaFactura Between IH.fechaInicio AND COALESCE(IH.fechaFinal,GETDATE()); ",nativeQuery = true)
    List<FacturaDetalleRecibo> getReciboDetalle(@Param("idFact")long idFactura);
}
