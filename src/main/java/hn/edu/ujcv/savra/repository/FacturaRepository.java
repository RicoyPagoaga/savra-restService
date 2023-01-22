package hn.edu.ujcv.savra.repository;

import hn.edu.ujcv.savra.entity.Factura;
import hn.edu.ujcv.savra.entity.FacturaRecibo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FacturaRepository extends JpaRepository<Factura,Long> {
    Optional<Factura> findByNoFactura(String noFactura);

    @Query(value = "Select PF.cai as cai, F.noFactura as noFactura, CONVERT(varchar,PF.fechaLimiteEmision,103) AS fechaLimite, C.nombre as cliente, \n" +
            "CONVERT(varchar,F.fechaFactura,22) as fechaFactura , E.nombre as empleado, TE.nombre as tipoEntrega,\n" +
            "(Case\tWhen F.idShipper IS NULL Then 'N/A' Else (Select S.nombre From shipper as S where S.idShipper = F.idShipper) End) as [shipper],\n" +
            "F.costoEnvio as costoEnvio,(Case When Convert(varchar(20),F.fechaDespacho) IS NULL Then 'N/A' Else CONVERT(varchar,F.fechaDespacho,103) End) as [fechaDespacho],\n" +
            "(Case When Convert(varchar(20),F.fechaEntrega) IS NULL Then 'N/A' Else CONVERT(varchar,F.fechaEntrega,103) End) as [fechaEntrega], MP.nombre as metodoPago,\n" +
            "(Case When F.tarjeta IS NULL Then 'N/A' Else F.tarjeta End)as noTarjeta,\n" +
            "F.efectivo as efectivo,(Case When F.idCupon IS NULL Then 'N/A' Else (Select CP.codigo from cupon as CP where CP.idCupon = F.idCupon) End)as [cupon],\n" +
            "PF.rangoInicial as rangoInicial, PF.rangoFinal as rangoFinal\n" +
            "From factura as F\n" +
            "Inner Join parametrosFactura as PF ON F.idParametroFactura = PF.idParametro\n" +
            "Inner Join cliente as C ON F.idCliente = C.idCliente\n" +
            "Inner Join empleado as E ON F.idEmpleado = E.idEmpleado\n" +
            "Inner Join tipoEntrega as TE ON F.idTipoEntrega = TE.idTipoEntrega\n" +
            "Inner Join metodoPago as MP ON F.idMetodoPago = MP.idMetodoPago\n" +
            "Where idFactura = :idFact",nativeQuery = true)
    Optional<FacturaRecibo> getReciboEncabezado(@Param("idFact")long idFactura);
}
