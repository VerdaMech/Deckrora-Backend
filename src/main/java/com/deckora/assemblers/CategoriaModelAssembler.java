package com.deckora.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.deckora.controller.CategoriaControllerV2;
import com.deckora.model.Categoria;
//Hypermedia as the Engine of Application State (HATEOAS)
//Principio de arquitectura REST que describe como guiar al cliente a traves de los recursos de la API
@Component
public class CategoriaModelAssembler implements RepresentationModelAssembler<Categoria, EntityModel<Categoria>>{
    @SuppressWarnings("null")
    @Override
    public EntityModel<Categoria> toModel(Categoria categoria){
        return EntityModel.of(categoria,
            linkTo(methodOn(CategoriaControllerV2.class).getCategoriaById(Long.valueOf(categoria.getId()))).withSelfRel(),
            linkTo(methodOn(CategoriaControllerV2.class).getAllCategorias()).withRel("categorias"),
            linkTo(methodOn(CategoriaControllerV2.class).createCategoria(categoria)).withRel("crear_categoria"),
            linkTo(methodOn(CategoriaControllerV2.class).updateCategoria(Long.valueOf(categoria.getId()),categoria)).withRel("actualizar_categoria"),
            linkTo(methodOn(CategoriaControllerV2.class).patchCategoria(Long.valueOf(categoria.getId()), categoria)).withRel("patch_categoria"),
            linkTo(methodOn(CategoriaControllerV2.class).deleteCategoria(Long.valueOf(categoria.getId()))).withRel("eliminar_categoria")
            );
    }
}