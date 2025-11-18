package com.deckora.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deckora.model.ProductosCategorias;
import com.deckora.repository.ProductosCategoriasRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductosCategoriasService {

    @Autowired
    private ProductosCategoriasRepository productosCategoriasRepository;

    public List<ProductosCategorias> findAll(){
        return productosCategoriasRepository.findAll();
    }

    public ProductosCategorias findById(Long id){
        return productosCategoriasRepository.findById(id).get();
    }

    public ProductosCategorias save(ProductosCategorias productosCategorias){
        return productosCategoriasRepository.save(productosCategorias);
    }

    public void delete(Long id){
        productosCategoriasRepository.deleteById(id);
    }

    public ProductosCategorias patchProductosCategorias(Long id, ProductosCategorias parcialProductosCategorias){
        Optional<ProductosCategorias> productosCategoriasOpcional = productosCategoriasRepository.findById(id);
        if (productosCategoriasOpcional.isPresent()) {
            
            ProductosCategorias productosCategoriasActualizar = productosCategoriasOpcional.get();
            
            if (parcialProductosCategorias.getProducto() != null) {
                productosCategoriasActualizar.setProducto(parcialProductosCategorias.getProducto());
            }
            if (parcialProductosCategorias.getCategoria() != null) {
                productosCategoriasActualizar.setCategoria(parcialProductosCategorias.getCategoria());
            }
            return productosCategoriasRepository.save(productosCategoriasActualizar);
        } else {
            return null;
        }
    }
}
