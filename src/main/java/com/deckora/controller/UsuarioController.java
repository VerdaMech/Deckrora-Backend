package com.deckora.controller;

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

import com.deckora.model.Usuario;
import com.deckora.service.UsuarioService;

import java.util.List;



@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listar(){
        List <Usuario> listaUsuarios = usuarioService.findAll();
        if(listaUsuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaUsuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscar(@PathVariable Long id){
        try{
            Usuario usuario = usuarioService.findById(id);
            return ResponseEntity.ok(usuario);
        }catch  (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/letra/{letra}")
    public List<Usuario> buscarUsuariosPorLetra(@PathVariable String letra) {
        return usuarioService.buscarUsuariosPorLetra(letra);
    }
    

    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario){
        Usuario newcUser = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(newcUser);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Integer id, @RequestBody Usuario usuario){
        try{
            usuarioService.save(usuario);
            return ResponseEntity.ok(usuario);
        }catch( Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> patchUsuario(@PathVariable Long id, @RequestBody Usuario partialUser){
        try{
            Usuario actualizarUsuario = usuarioService.patchUsuario(id, partialUser);
            return ResponseEntity.ok(actualizarUsuario);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id ){
        try{
            usuarioService.delete(id);
            return ResponseEntity.noContent().build();
        }catch( Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
