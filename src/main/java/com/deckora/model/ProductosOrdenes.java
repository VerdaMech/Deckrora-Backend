package com.deckora.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table;

@Entity
@Table(name = "ProductosOrdenes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductosOrdenes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 3)
    private Integer cantidad_producto;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    @JsonIgnoreProperties("ordenes")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_orden", nullable = false)
    @JsonIgnoreProperties("productos")
    private Orden orden;
}

