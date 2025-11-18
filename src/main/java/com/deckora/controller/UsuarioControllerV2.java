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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deckora.assemblers.UsuarioModelAssembler;
import com.deckora.model.Usuario;
import com.deckora.service.UsuarioService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v2/usuarios")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con la creación, modificación y eliminación de los usuarios")
public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene todos los usuarios", description = "Muestra una lista de todos los usuarios creados")
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> getAllUsuarios(){
        List <EntityModel<Usuario>> listaUsuarios = usuarioService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if(listaUsuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
            listaUsuarios,
            linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene un usuario en específico", description = "A través de un id, este método muestra un usuario en específico")
    public ResponseEntity<EntityModel<Usuario>> getUsuarioById(@Parameter(description = "Id del usuario", required = true, example = "1")@PathVariable Long id){
        Usuario Usuario = usuarioService.findById(id);
        if (Usuario == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(Usuario));
    }

    //get con el metodo especial (letra inicial)
    @GetMapping(value = "letra/{letra}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene un usuario en específico", description = "A través de la letra inicial, este método muestra un usuario o lista de usuarios con la letra indicada")
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> buscarUsuarioPorLetra(@Parameter(description = "Letra inicial del Usuario", required = true, example = "s")@PathVariable  String letra){
    List<Usuario> usuarios = usuarioService.buscarUsuariosPorLetra(letra.toUpperCase());
    List<EntityModel<Usuario>> usuariosModel = usuarios.stream()
        .map(assembler::toModel)
        .toList();

        return ResponseEntity.ok(CollectionModel.of(usuariosModel));
    }

    //Metodo con nombre y tipo usuario
    @GetMapping(value = "nombre/{nombre}/idTipoUsuario/{idTipoUsuario}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene uno o varios usuarios en específico", description = "A través del nombre y el id del tipo usuario, este método muestra un usuario o lista de usuarios con la letra indicada y el tipo de usuario")
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> buscarUsuarioPorNombreYTipoUsuario(@Parameter(description = "Nombre del usuario", required = true, example = "juan")@PathVariable  String nombre, @Parameter(description = "Id tipo Usuario", required = true, example = "1")@PathVariable Integer idTipoUsuario){
    List<Usuario> usuarios = usuarioService.buscarPorNombreYTipo(nombre.toLowerCase(),idTipoUsuario);
    List<EntityModel<Usuario>> usuariosModel = usuarios.stream()
        .map(assembler::toModel)
        .toList();

        return ResponseEntity.ok(CollectionModel.of(usuariosModel));
    }

    //Metodo con nombre y apellido
    @GetMapping(value = "nombre/{nombre}/apellido/{apellido}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene uno o varios usuarios en específico", description = "A través del nombre y apellido, este método muestra un usuario o lista de usuarios que compartan nombre y apellido")
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> buscarUsuarioPorNombreYApellido(@Parameter(description = "nombre", required = true, example = "anais")@PathVariable String nombre,@Parameter(description = "apellido", required = true, example = "palma")@PathVariable String apellido){
    List<Usuario> usuarios = usuarioService.buscarPorNombreYApellido(nombre,apellido);
    List<EntityModel<Usuario>> usuariosModel = usuarios.stream()
        .map(assembler::toModel)
        .toList();

        return ResponseEntity.ok(CollectionModel.of(usuariosModel));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método crea un nuevo usuario", description = "Crea un nuevo usuario, enviando un objeto a través de un POST")
    public ResponseEntity<EntityModel<Usuario>> createUsuario(@Parameter(description = "Detalles del usuario", required = true) @RequestBody Usuario Usuario){
        Usuario newUsuario = usuarioService.save(Usuario);
        return ResponseEntity
                .created(linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(Long.valueOf(newUsuario.getId()))).toUri())
                .body(assembler.toModel(newUsuario));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método actualiza un usuario", description = "A través de un id, este método actualiza un usuario, pero se deben escribir todos los atributos")
    public ResponseEntity<EntityModel<Usuario>> updateUsuario(@Parameter(description = "Id del usuario", required = true, example = "1")@PathVariable Long id, @Parameter(description = "Detalles del estado", required = true)@RequestBody Usuario Usuario){
        Usuario.setId(Usuario.getId());
        Usuario updateUsuario = usuarioService.save(Usuario);
        return ResponseEntity.ok(assembler.toModel(updateUsuario));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método puede modificar un campo en específico de un usuario", description = "A través de un id, este método actualiza un usuario, este método actualiza solo el atributo que nosotros queremos modificar")
    public ResponseEntity<EntityModel<Usuario>> patchUsuario(@Parameter(description = "Id del usuario", required = true, example = "1")@PathVariable Long id, @Parameter(description = "Detalles del estado", required = true)@RequestBody Usuario parcialUsuario){
        Usuario patched = usuarioService.patchUsuario(id, parcialUsuario);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método puede eliminar un usuario", description = "Elimina el usuario especificado por el id")
    public ResponseEntity<Void> deleteUsuario(@Parameter(description = "Id del estado", required = true, example = "1")@PathVariable Long id) {
        Usuario existing = usuarioService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
