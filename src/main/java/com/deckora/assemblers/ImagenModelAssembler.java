package com.deckora.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.deckora.controller.ImagenController;
import com.deckora.model.Imagen;

@Component
public class ImagenModelAssembler implements RepresentationModelAssembler<Imagen, EntityModel<Imagen>>{

  @SuppressWarnings("null")
  @Override
  public EntityModel<Imagen> toModel(Imagen imagen){

    return EntityModel.of(imagen,
      linkTo(methodOn(ImagenController.class).getImagenById(Long.valueOf(imagen.getId()))).withSelfRel(),
      linkTo(methodOn(ImagenController.class).getAllImagenes()).withRel("imagenes"),
      linkTo(methodOn(ImagenController.class).createImagen(imagen)).withRel("crear_imagen"),
      linkTo(methodOn(ImagenController.class).updateImagen(Long.valueOf(imagen.getId()),imagen)).withRel("actualizar_imagen"),
      linkTo(methodOn(ImagenController.class).patchImagen(Long.valueOf(imagen.getId()), imagen)).withRel("patch_imagen"),
      linkTo(methodOn(ImagenController.class).deleteImagen(Long.valueOf(imagen.getId()))).withRel("eliminar_imagen")
      );
  }

}