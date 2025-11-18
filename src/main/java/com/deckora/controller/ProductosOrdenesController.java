package com.deckora.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deckora.model.ProductosOrdenes;
import com.deckora.service.ProductosOrdenesService;

@RestController
@RequestMapping("/api/v1/productosOrdenes")
public class ProductosOrdenesController {

    @Autowired
    private ProductosOrdenesService productosOrdenesService;

    @GetMapping
    public ResponseEntity<List<ProductosOrdenes>> listar(){
        List<ProductosOrdenes> listaProductosOrdenes = productosOrdenesService.findAll();
        if(listaProductosOrdenes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaProductosOrdenes);
    }

    @PostMapping
    public ResponseEntity<ProductosOrdenes> guardar(@RequestBody ProductosOrdenes productosOrdenes){
        ProductosOrdenes productoNuevo = productosOrdenesService.save(productosOrdenes);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoNuevo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductosOrdenes> buscar(@PathVariable Long id){
        try{
            ProductosOrdenes productosOrdenes = productosOrdenesService.findById(id);
            return ResponseEntity.ok(productosOrdenes);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductosOrdenes> actualizar(@PathVariable Long id, @RequestBody ProductosOrdenes productosOrdenes){
        try{
            productosOrdenesService.save(productosOrdenes);
            return ResponseEntity.ok(productosOrdenes);
        }catch( Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductosOrdenes> patchProductosOrdenes(@PathVariable Long id, @RequestBody ProductosOrdenes parcialProductosOrdenes) {
        try {
            ProductosOrdenes actualizarProductosOrdenes = productosOrdenesService.patchProductosOrdenes(id, parcialProductosOrdenes);
            return ResponseEntity.ok(actualizarProductosOrdenes);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            productosOrdenesService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
