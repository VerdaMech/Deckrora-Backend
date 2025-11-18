package com.deckora.repository;

import org.springframework.stereotype.Repository;

import com.deckora.model.Producto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

    List<Producto> findById(Integer id);

    @Query("SELECT u FROM Producto u WHERE LOWER(u.nombre_producto) LIKE CONCAT(:letra, '%')")
    List<Producto> buscarProductoPorLetraInicial(@Param("letra") String letra);
}
