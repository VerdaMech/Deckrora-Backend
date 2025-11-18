package com.deckora.model;


//import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table;


@Entity
@Table(name = "ProductosCategorias")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductosCategorias {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    //@JsonBackReference(value = "producto-productoCategorias")
    @JsonIgnoreProperties("categorias")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    //@JsonBackReference(value = "categoria-productoCategorias")
    @JsonIgnoreProperties("productos")
    private Categoria categoria;
}
