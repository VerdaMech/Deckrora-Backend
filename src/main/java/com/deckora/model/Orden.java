package com.deckora.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Orden")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 10)
    private String fecha;

    @Column(nullable = false)
    private Integer total;

    @ManyToOne
    @JoinColumn(name = "run_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "metodo_pago", nullable = false)
    private Pago pago;

    @ManyToOne
    @JoinColumn(name = "id_estado_orden", nullable = false)
    private EstadoOrden estadoOrden;

    @ManyToOne
    @JoinColumn(name = "id_delivery", nullable = false)
    private Envio delivery;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("orden")
    private List<ProductosOrdenes> productos;
}
