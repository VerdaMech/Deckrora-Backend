package com.deckora.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deckora.model.Producto;
import com.deckora.model.ProductosCategorias;
import com.deckora.model.ProductosOrdenes;
import com.deckora.repository.ProductoRepository;
import com.deckora.repository.ProductosCategoriasRepository;
import com.deckora.repository.ProductosOrdenesRepository;

import java.util.List;
import java.util.Optional;


import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private ProductosCategoriasRepository productosCategoriasRepository;

    @Autowired
    private ProductosCategoriasService productosCategoriasService;

    @Autowired ProductosOrdenesRepository productosOrdenesRepository;

    @Autowired ProductosOrdenesService productosOrdenesService;

    public List<Producto> findAll(){
        return productoRepository.findAll();
    }

    public Producto findById(Long id){
        return productoRepository.findById(id).get();
    }

    public Producto save(Producto producto){
        return productoRepository.save(producto);
    }

    //Eliminar x cascada (para borrar relacion con categoria y orden- revisar?)
    public void delete(Long id){

        Producto producto = productoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        List<ProductosCategorias> productosCategorias = productosCategoriasRepository.findByProducto(producto);
        for (ProductosCategorias productosYCategorias : productosCategorias) {
        productosCategoriasService.delete(Long.valueOf(productosYCategorias.getId()));
        }

        List<ProductosOrdenes> productosOrdenes = productosOrdenesRepository.findByProducto(producto);
        for (ProductosOrdenes productosYOrdenes : productosOrdenes) {
        productosOrdenesService.delete(Long.valueOf(productosYOrdenes.getId()));
        }

        productoRepository.delete(producto);
    }


    public List<Producto> buscarProductoPorLetra(String letra){
        return productoRepository.buscarProductoPorLetraInicial(letra);
    }

    public Producto patchProducto(Long id, Producto parcialProducto){
        Optional<Producto> productoOpcional = productoRepository.findById(id);
        if (productoOpcional.isPresent()) {
            
            Producto productoActualizar = productoOpcional.get();
            
            if (parcialProducto.getNombre_producto() != null) {
                productoActualizar.setNombre_producto(parcialProducto.getNombre_producto());
            }

            if(parcialProducto.getPrecio() != 0) {
                productoActualizar.setPrecio(parcialProducto.getPrecio());
            }

            if(parcialProducto.getCantidad() != 0) {
                productoActualizar.setCantidad(parcialProducto.getCantidad());
            }
            return productoRepository.save(productoActualizar);
        } else {
            return null;
        }
    }
}
