package com.deckora.controller;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deckora.assemblers.TipoUsuarioModelAssembler;
import com.deckora.model.TipoUsuario;
import com.deckora.service.TipoUsuarioService;

import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v2/tipoUsuarios")
@Tag(name = "Tipos de Usuario", description = "Operaciones relacionadas con la creación, modificación y eliminación de los tipos de usuarios")
public class TipoUsuarioControllerV2 {

    @Autowired
    private TipoUsuarioService tipoUsuarioService;

    @Autowired
    private TipoUsuarioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene todos los tipos de usuarios", description = "Muestra una lista de todos los tipos de usuarios creados")
    public ResponseEntity<CollectionModel<EntityModel<TipoUsuario>>> getAllTipoUsuarios(){
        List <EntityModel<TipoUsuario>> listaTipoUsuarios = tipoUsuarioService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if(listaTipoUsuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
            listaTipoUsuarios,
            linkTo(methodOn(TipoUsuarioControllerV2.class).getAllTipoUsuarios()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene un tipo de usuario", description = "A través de un id, este método muestra un tipo de usuario en específico")
    public ResponseEntity<EntityModel<TipoUsuario>> getTipoUsuarioById(@Parameter(description = "Id del tipo de usuario", required = true, example = "1")@PathVariable Long id){
        TipoUsuario tipoUsuario = tipoUsuarioService.findById(id);
        if (tipoUsuario == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(tipoUsuario));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método crea un nuevo tipo de usuario", description = "Crea un nuevo tipo de usuario, enviando un objeto a través de un POST")
    public ResponseEntity<EntityModel<TipoUsuario>> createTipoUsuario(@Parameter(description = "Detalles del tipo de usuario", required = true)@RequestBody TipoUsuario tipoUsuario){
        TipoUsuario newTipoUsuario = tipoUsuarioService.save(tipoUsuario);
        return ResponseEntity
                .created(linkTo(methodOn(TipoUsuarioControllerV2.class).getTipoUsuarioById(Long.valueOf(newTipoUsuario.getId()))).toUri())
                .body(assembler.toModel(newTipoUsuario));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método actualiza un tipo de usuario", description = "A través de un id, este método actualiza un tipo de usuario, pero se deben escribir todos los atributos")
    public ResponseEntity<EntityModel<TipoUsuario>> updateTipoUsuario(@Parameter(description = "Id del tipo de usuario", required = true, example = "1")@PathVariable Long id,@Parameter(description = "Detalles del tipo de usuario", required = true) @RequestBody TipoUsuario tipoUsuario){
        tipoUsuario.setId(tipoUsuario.getId());
        TipoUsuario updateTipoUsuario = tipoUsuarioService.save(tipoUsuario);
        return ResponseEntity.ok(assembler.toModel(updateTipoUsuario));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método puede modificar un campo en específico en un tipo de usuario", description = "A través de un id, este método actualiza solo el atributo que nosotros queremos modificar")
    public ResponseEntity<EntityModel<TipoUsuario>> patchTipoUsuario(@Parameter(description = "Id del tipo de usuario", required = true, example = "1")@PathVariable Long id,@Parameter(description = "Campo del tipo de usuario a modificar") @RequestBody TipoUsuario parcialTipoUsuario){
        TipoUsuario patched = tipoUsuarioService.patchTipoUsuario(id, parcialTipoUsuario);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método elimina un tipo de usuario", description = "Elimina un tipo de usuario especificado por el id")
    public ResponseEntity<Void> deleteTipoUsuario(@PathVariable Long id) {
        TipoUsuario existing = tipoUsuarioService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        tipoUsuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
