package com.deckora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deckora.model.Producto;
import com.deckora.service.ProductoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> listar(){
        List<Producto> listaProductos = productoService.findAll();
        if(listaProductos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaProductos);
    }

    @PostMapping
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto){
        Producto productoNuevo = productoService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoNuevo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscar(@PathVariable Long id){
        try{
            Producto producto = productoService.findById(id);
            return ResponseEntity.ok(producto);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/letra/{letra}")
    public List<Producto> buscarProductosPorLetra(@PathVariable String letra) {
        return productoService.buscarProductoPorLetra(letra);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto producto){
        try{
            productoService.save(producto);
            return ResponseEntity.ok(producto);
        }catch( Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Producto> patchProducto(@PathVariable Long id, @RequestBody Producto parcialProducto) {
        try {
            Producto actualizarProducto = productoService.patchProducto(id, parcialProducto);
            return ResponseEntity.ok(actualizarProducto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            productoService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
