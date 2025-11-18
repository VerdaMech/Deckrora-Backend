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

import com.deckora.model.Categoria;
import com.deckora.service.CategoriaService;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> listar(){
        List<Categoria> listaCategorias = categoriaService.findAll();
        if(listaCategorias.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaCategorias);
    }

    @PostMapping
    public ResponseEntity<Categoria> guardar(@RequestBody Categoria categoria){
        Categoria nuevaCategoria = categoriaService.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscar(@PathVariable Long id){
        try{
            Categoria categoria = categoriaService.findById(id);
            return ResponseEntity.ok(categoria);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(@PathVariable Long id, @RequestBody Categoria categoria){
        try{
            categoriaService.save(categoria);
            return ResponseEntity.ok(categoria);
        }catch( Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Categoria> patchCategoria(@PathVariable Long id, @RequestBody Categoria parcialCategoria) {
        try {
            Categoria actualizarCategoria = categoriaService.patchCategoria(id, parcialCategoria);
            return ResponseEntity.ok(actualizarCategoria);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            categoriaService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
