package com.deckora.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deckora.model.ProductosOrdenes;
import com.deckora.repository.ProductosOrdenesRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductosOrdenesService {

    @Autowired
    private ProductosOrdenesRepository productosOrdenesRepository;

    public List<ProductosOrdenes> findAll(){
        return productosOrdenesRepository.findAll();
    }

    public ProductosOrdenes findById(Long id){
        return productosOrdenesRepository.findById(id).get();
    }

    public ProductosOrdenes save(ProductosOrdenes productosOrdenes){
        return productosOrdenesRepository.save(productosOrdenes);
    }

    public void delete(Long id){
        productosOrdenesRepository.deleteById(id);
    }

    public ProductosOrdenes patchProductosOrdenes(Long id, ProductosOrdenes parcialProductosOrdenes){
        Optional<ProductosOrdenes> productosOrdenesOpcional = productosOrdenesRepository.findById(id);
        if (productosOrdenesOpcional.isPresent()) {
            
            ProductosOrdenes productosOrdenesActualizar = productosOrdenesOpcional.get();
            
            if (parcialProductosOrdenes.getCantidad_producto() != null) {
                productosOrdenesActualizar.setCantidad_producto(parcialProductosOrdenes.getCantidad_producto());
            }
            if(parcialProductosOrdenes.getProducto() != null){
                productosOrdenesActualizar.setProducto(parcialProductosOrdenes.getProducto());
            }
            if(parcialProductosOrdenes.getOrden() != null){
                productosOrdenesActualizar.setOrden(parcialProductosOrdenes.getOrden());
            }
            return productosOrdenesRepository.save(productosOrdenesActualizar);
        } else {
            return null;
        }
    }
}
