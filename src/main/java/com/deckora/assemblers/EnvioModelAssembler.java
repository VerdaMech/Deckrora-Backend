package com.deckora.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.deckora.controller.EnvioControllerV2;
import com.deckora.model.Envio;

@Component
public class EnvioModelAssembler implements RepresentationModelAssembler<Envio, EntityModel<Envio>>{
    @SuppressWarnings("null")
    @Override
    public EntityModel<Envio> toModel(Envio envio){
        return EntityModel.of(envio,
            linkTo(methodOn(EnvioControllerV2.class).getEnvioById(Long.valueOf(envio.getId()))).withSelfRel(),
            linkTo(methodOn(EnvioControllerV2.class).getAllEnvios()).withRel("envios"),
            linkTo(methodOn(EnvioControllerV2.class).createEnvio(envio)).withRel("crear_envio"),
            linkTo(methodOn(EnvioControllerV2.class).updateEnvio(Long.valueOf(envio.getId()),envio)).withRel("actualizar_envio"),
            linkTo(methodOn(EnvioControllerV2.class).patchEnvio(Long.valueOf(envio.getId()), envio)).withRel("patch_envio"),
            linkTo(methodOn(EnvioControllerV2.class).deleteEnvio(Long.valueOf(envio.getId()))).withRel("eliminar_envio")
            );
    }
}