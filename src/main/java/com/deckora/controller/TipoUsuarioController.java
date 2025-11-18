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

import com.deckora.model.TipoUsuario;
import com.deckora.service.TipoUsuarioService;

@RestController
@RequestMapping("/api/v1/tipoUsuarios")
public class TipoUsuarioController {

    @Autowired
    private TipoUsuarioService tipoUsuarioService;

    @GetMapping
    public ResponseEntity<List<TipoUsuario>> listar(){
        List <TipoUsuario> listaTipoUsuarios = tipoUsuarioService.findAll();
        if(listaTipoUsuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaTipoUsuarios);
    }

    @GetMapping("/id")
    public ResponseEntity<TipoUsuario> buscar(@PathVariable Long id){
        try{
            TipoUsuario tipoUsuario = tipoUsuarioService.findById(id);
            return ResponseEntity.ok(tipoUsuario);
        }catch  (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TipoUsuario> save(@RequestBody TipoUsuario tipoUsuario){
        TipoUsuario nuevoTipoUsuario = tipoUsuarioService.save(tipoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTipoUsuario);

    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoUsuario> actualizar(@PathVariable Long id, @RequestBody TipoUsuario tipoUsuario){
        try{
            tipoUsuarioService.save(tipoUsuario);
            return ResponseEntity.ok(tipoUsuario);
        }catch( Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TipoUsuario> patchTipoUsuario(@PathVariable Long id, @RequestBody TipoUsuario parcialTipoUsuario){
        try{
            TipoUsuario actualizarTipoUsuario = tipoUsuarioService.patchTipoUsuario(id, parcialTipoUsuario);
            return ResponseEntity.ok(actualizarTipoUsuario);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id ){
        try{
            tipoUsuarioService.delete(id);
            return ResponseEntity.noContent().build();
        }catch( Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
