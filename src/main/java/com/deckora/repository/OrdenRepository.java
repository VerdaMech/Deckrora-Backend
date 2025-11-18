package com.deckora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deckora.model.Envio;
import com.deckora.model.Orden;
import com.deckora.model.Pago;

@Repository
public interface OrdenRepository extends JpaRepository<Orden,Long> {


    //Query 1, Buscar ordenes que tengan x estado y x usuario
    @Query("""
        SELECT o FROM Orden o
        JOIN FETCH o.usuario u
        WHERE o.estadoOrden.id = :idEstado
        AND u.id = :idUsuario
    """)
    List<Orden> buscarPorEstadoYUsuario(
        @Param("idEstado") Integer idEstado,
        @Param("idUsuario") Integer idUsuario
    );

    //Query 2, Buscar todas las ordenes con x pago y producto
    @Query("""
        SELECT o FROM Orden o
        JOIN FETCH o.productos po
        WHERE o.pago.id = :idPago
        AND po.producto.id = :idProducto
    """)
    List<Orden> buscarPorPagoYProducto(
        @Param("idPago") Integer idPago,
        @Param("idProducto") Integer idProducto
    );

    //Query 3, buscar ordenes de un usuario que tengan una cantidad mayor a cierta cantidad de un producto
/*     @Query("""
        SELECT DISTINCT po.orden
        FROM ProductosOrdenes po
        WHERE po.cantidad_producto > :cantidadProducto
        AND po.orden.usuario.id = :idUsuario
    """)
    List<Orden> buscarOrdenesPorCantidadDeProductoYUsuario(
        @Param("idUsuario") Integer idUsuario,
        @Param("cantidadProducto") Integer cantidadProducto
    ); */

    //Query arreglada
    @Query("""
        SELECT  po.orden
        FROM ProductosOrdenes po
        JOIN FETCH po.orden.pago p
        WHERE po.cantidad_producto > :cantidadProducto
        AND po.orden.usuario.id = :idUsuario
    """)
    List<Orden> buscarOrdenesPorCantidadDeProductoYUsuario(
        @Param("idUsuario") Integer idUsuario,
        @Param("cantidadProducto") Integer cantidadProducto
    );

    //Query 4, Buscar todas las ordenes con x pago y producto
    @Query("""
        SELECT o FROM Orden o
        JOIN FETCH o.productos po
        WHERE po.producto.id = :idProducto
        AND o.usuario.id = :idUsuario
    """)
    List<Orden> buscarPorProductoYUsuario(
        @Param("idProducto") Integer idProducto,
        @Param("idUsuario") Integer idUsuario
    );

    //Metodos nuevos
    List<Orden> findByFechaBetween(String fechaIncio, String fechaFinal);
    List<Orden> findByPagoAndDelivery(Pago pago, Envio delivery);
}
