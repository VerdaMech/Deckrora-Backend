package com.deckora.assemblers;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.deckora.controller.UsuarioControllerV2;
import com.deckora.model.Usuario;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Usuario> toModel(Usuario Usuario){
        return EntityModel.of(Usuario,
            linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(Long.valueOf(Usuario.getId()))).withSelfRel(),
            linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withRel("Usuarios"),
            linkTo(methodOn(UsuarioControllerV2.class).createUsuario(Usuario)).withRel("crear_Usuario"),
            linkTo(methodOn(UsuarioControllerV2.class).updateUsuario(Long.valueOf(Usuario.getId()),Usuario)).withRel("actualizar_usuario"),
            linkTo(methodOn(UsuarioControllerV2.class).patchUsuario(Long.valueOf(Usuario.getId()), Usuario)).withRel("patch_usuario"),
            linkTo(methodOn(UsuarioControllerV2.class).deleteUsuario(Long.valueOf(Usuario.getId()))).withRel("eliminar_usuario"),
            linkTo(methodOn(UsuarioControllerV2.class).buscarUsuarioPorLetra("letra")).withRel("buscar_usuario_por_letra_inicial"),
            linkTo(methodOn(UsuarioControllerV2.class).buscarUsuarioPorNombreYApellido("nombre", "apellido")).withRel("buscar_por_nombre_y_apellido"),
            linkTo(methodOn(UsuarioControllerV2.class).buscarUsuarioPorNombreYTipoUsuario("nombre", 1)).withRel("buscar_por_nombre_y_id_Tipo_Usuario"));
    }
}
