package com.deckora.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "Producto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre_producto;

    @Column(length = 7, nullable = false)
    private int precio;

    @Column(length = 3, nullable = false)
    private int cantidad;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    //@JsonManagedReference(value = "producto-productoCategorias")
    @JsonIgnoreProperties("producto")
    private List<ProductosCategorias> categorias;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("producto")
    private List<ProductosOrdenes> ordenes;

}
