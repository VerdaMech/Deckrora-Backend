package com.deckora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deckora.model.Orden;
import com.deckora.model.Producto;
import com.deckora.model.ProductosOrdenes;


@Repository
public interface ProductosOrdenesRepository extends JpaRepository<ProductosOrdenes, Long>{

    List<ProductosOrdenes> findByProducto(Producto producto);
    List<ProductosOrdenes> findByOrden(Orden orden);
    
    void deleteByProducto(Producto producto);
    void deleteByOrden(Orden orden);
}
