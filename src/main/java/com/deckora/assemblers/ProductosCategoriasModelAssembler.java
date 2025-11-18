package com.deckora.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.deckora.controller.ProductosCategoriasControllerV2;
import com.deckora.model.ProductosCategorias;

@Component
public class ProductosCategoriasModelAssembler implements RepresentationModelAssembler<ProductosCategorias, EntityModel<ProductosCategorias>>{
    @SuppressWarnings("null")
    @Override
    public EntityModel<ProductosCategorias> toModel(ProductosCategorias productosCategorias){
        return EntityModel.of(productosCategorias,
            linkTo(methodOn(ProductosCategoriasControllerV2.class).getProductosCategoriasById(Long.valueOf(productosCategorias.getId()))).withSelfRel(),
            linkTo(methodOn(ProductosCategoriasControllerV2.class).getAllProductosCategorias()).withRel("_productos_categorias"),
            linkTo(methodOn(ProductosCategoriasControllerV2.class).createProductosCategorias(productosCategorias)).withRel("crear_productos_categorias"),
            linkTo(methodOn(ProductosCategoriasControllerV2.class).updateProductosCategorias(Long.valueOf(productosCategorias.getId()),productosCategorias)).withRel("actualizar_productos_categorias"),
            linkTo(methodOn(ProductosCategoriasControllerV2.class).patchProductosCategorias(Long.valueOf(productosCategorias.getId()), productosCategorias)).withRel("patch_productos_categorias"),
            linkTo(methodOn(ProductosCategoriasControllerV2.class).deleteProductosCategorias(Long.valueOf(productosCategorias.getId()))).withRel("eliminar_productos_categorias")
            );
    }
}