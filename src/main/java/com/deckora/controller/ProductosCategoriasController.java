package com.deckora.controller;

import org.springframework.web.bind.annotation.RestController;

import com.deckora.model.ProductosCategorias;
import com.deckora.service.ProductosCategoriasService;

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

@RestController
@RequestMapping("/api/v1/productosCategorias")
public class ProductosCategoriasController {

    @Autowired
    private ProductosCategoriasService productosCategoriasService;

    @GetMapping
    public ResponseEntity<List<ProductosCategorias>> listar(){
        List<ProductosCategorias> listaProductosCategorias = productosCategoriasService.findAll();
        if(listaProductosCategorias.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaProductosCategorias);
    }

    @PostMapping
    public ResponseEntity<ProductosCategorias> guardar(@RequestBody ProductosCategorias productosCategorias){
        ProductosCategorias productoCategoriasNuevo = productosCategoriasService.save(productosCategorias);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoCategoriasNuevo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductosCategorias> buscar(@PathVariable Long id){
        try{
            ProductosCategorias productosCategorias = productosCategoriasService.findById(id);
            return ResponseEntity.ok(productosCategorias);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductosCategorias> actualizar(@PathVariable Long id, @RequestBody ProductosCategorias productosCategorias){
        try{
            productosCategoriasService.save(productosCategorias);
            return ResponseEntity.ok(productosCategorias);
        }catch( Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductosCategorias> patchProductosCategorias(@PathVariable Long id, @RequestBody ProductosCategorias parcialProductosCategorias) {
        try {
            ProductosCategorias actualizarProductosCategorias = productosCategoriasService.patchProductosCategorias(id, parcialProductosCategorias);
            return ResponseEntity.ok(actualizarProductosCategorias);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            productosCategoriasService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
