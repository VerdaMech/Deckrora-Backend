package com.deckora.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.deckora.controller.ProductoControllerV2;
import com.deckora.model.Producto;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>>{
    @SuppressWarnings("null")
    @Override
    public EntityModel<Producto> toModel(Producto producto){
        return EntityModel.of(producto,
            linkTo(methodOn(ProductoControllerV2.class).getProductoById(Long.valueOf(producto.getId()))).withSelfRel(),
            linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withRel("productos"),
            linkTo(methodOn(ProductoControllerV2.class).buscarProductoPorLetra("a")).withRel("buscar_por_letra_inicial"),
            linkTo(methodOn(ProductoControllerV2.class).createProducto(producto)).withRel("crear_producto"),
            linkTo(methodOn(ProductoControllerV2.class).updateProducto(Long.valueOf(producto.getId()),producto)).withRel("actualizar_producto"),
            linkTo(methodOn(ProductoControllerV2.class).patchProducto(Long.valueOf(producto.getId()), producto)).withRel("patch_producto"),
            linkTo(methodOn(ProductoControllerV2.class).deleteProducto(Long.valueOf(producto.getId()))).withRel("eliminar_producto")
            );
    }
}