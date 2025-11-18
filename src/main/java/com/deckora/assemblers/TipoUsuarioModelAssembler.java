package com.deckora.assemblers;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.deckora.controller.TipoUsuarioControllerV2;
import com.deckora.model.TipoUsuario;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class TipoUsuarioModelAssembler implements RepresentationModelAssembler<TipoUsuario, EntityModel<TipoUsuario>>{
    
    @SuppressWarnings("null")
    @Override
    public EntityModel<TipoUsuario> toModel(TipoUsuario tipoUsuario){
        return EntityModel.of(tipoUsuario,
            linkTo(methodOn(TipoUsuarioControllerV2.class).getTipoUsuarioById(Long.valueOf(tipoUsuario.getId()))).withSelfRel(),
            linkTo(methodOn(TipoUsuarioControllerV2.class).getAllTipoUsuarios()).withRel("tipos_Usuarios"),
            linkTo(methodOn(TipoUsuarioControllerV2.class).createTipoUsuario(tipoUsuario)).withRel("crear_tipos_Usuario"),
            linkTo(methodOn(TipoUsuarioControllerV2.class).updateTipoUsuario(Long.valueOf(tipoUsuario.getId()),tipoUsuario)).withRel("actualizar_tipos_usuarios"),
            linkTo(methodOn(TipoUsuarioControllerV2.class).patchTipoUsuario(Long.valueOf(tipoUsuario.getId()), tipoUsuario)).withRel("patch_tipos_usuarios"),
            linkTo(methodOn(TipoUsuarioControllerV2.class).deleteTipoUsuario(Long.valueOf(tipoUsuario.getId()))).withRel("eliminar_tipo_usuarios")
            );
    }
}





