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

import com.deckora.model.Envio;
import com.deckora.service.EnvioService;

@RestController
@RequestMapping("/api/v1/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @GetMapping
    public ResponseEntity<List<Envio>> listar(){
        List<Envio> listaEnvios = envioService.findAll();
        if(listaEnvios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaEnvios);
    }

    @PostMapping
    public ResponseEntity<Envio> guardar(@RequestBody Envio envio){
        Envio nuevoEnvio = envioService.save(envio);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEnvio);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Envio> buscar(@PathVariable Long id){
        try{
            Envio envio = envioService.findById(id);
            return ResponseEntity.ok(envio);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Envio> actualizar(@PathVariable Long id, @RequestBody Envio envio){
        try{
            envioService.save(envio);
            return ResponseEntity.ok(envio);
        }catch( Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Envio> patchEnvio(@PathVariable Long id, @RequestBody Envio parcialEnvio) {
        try {
            Envio actualizarEnvio = envioService.patchEnvio(id, parcialEnvio);
            return ResponseEntity.ok(actualizarEnvio);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            envioService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
