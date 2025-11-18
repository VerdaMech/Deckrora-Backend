package com.deckora.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.deckora.model.EstadoOrden;
import com.deckora.service.EstadoOrdenService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1/estadoOrdenes")
public class EstadoOrdenController {

    @Autowired
    private EstadoOrdenService estadoOrdenService;

    @GetMapping
    public ResponseEntity<List<EstadoOrden>> listar(){
        List<EstadoOrden> listaEstadoOrdenes = estadoOrdenService.findAll();
        if(listaEstadoOrdenes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaEstadoOrdenes);
    }

    @PostMapping
    public ResponseEntity<EstadoOrden> guardar(@RequestBody EstadoOrden estadoOrden){
        EstadoOrden newEstadoOrden = estadoOrdenService.save(estadoOrden);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEstadoOrden);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoOrden> buscar(@PathVariable Long id){
        try{
            EstadoOrden estadoOrden = estadoOrdenService.findById(id);
            return ResponseEntity.ok(estadoOrden);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoOrden> actualizar(@PathVariable Long id, @RequestBody EstadoOrden estadoOrden){
        try{
            estadoOrdenService.save(estadoOrden);
            return ResponseEntity.ok(estadoOrden);
        }catch( Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EstadoOrden> patchEstadoOrden(@PathVariable Long id, @RequestBody EstadoOrden parcialEstadoOrden) {
        try {
            EstadoOrden actualizarEstadoOrden = estadoOrdenService.patchEstadoOrden(id, parcialEstadoOrden);
            return ResponseEntity.ok(actualizarEstadoOrden);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            estadoOrdenService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
