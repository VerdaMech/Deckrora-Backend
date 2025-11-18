package com.deckora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deckora.model.Categoria;
import com.deckora.model.Producto;
import com.deckora.model.ProductosCategorias;

import java.util.List;

@Repository
public interface ProductosCategoriasRepository extends JpaRepository<ProductosCategorias,Long>{

    List<ProductosCategorias> findByCategoria(Categoria categoria);
    List<ProductosCategorias> findByProducto(Producto producto);

    void deleteByProducto(Producto producto);
    void deleteByCategoria(Categoria categoria);
}
