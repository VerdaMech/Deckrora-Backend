package com.deckora.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.deckora.controller.ProductosOrdenesControllerV2;
import com.deckora.model.ProductosOrdenes;

@Component
public class ProductosOrdenesModelAssembler implements RepresentationModelAssembler<ProductosOrdenes,EntityModel<ProductosOrdenes>>{

    @SuppressWarnings("null")
    @Override
    public EntityModel<ProductosOrdenes> toModel(ProductosOrdenes productosOrdenes){
        return EntityModel.of(productosOrdenes,
            linkTo(methodOn(ProductosOrdenesControllerV2.class).getProductosOrdenesById(Long.valueOf(productosOrdenes.getId()))).withSelfRel(),
            linkTo(methodOn(ProductosOrdenesControllerV2.class).getAllProductosOrdenes()).withRel("productos_ordenes"),
            linkTo(methodOn(ProductosOrdenesControllerV2.class).createProductosOrdenes(productosOrdenes)).withRel("crear_productos_ordenes"),
            linkTo(methodOn(ProductosOrdenesControllerV2.class).updateProductosOrdenes(Long.valueOf(productosOrdenes.getId()),productosOrdenes)).withRel("actualizar_productos_ordenes"),
            linkTo(methodOn(ProductosOrdenesControllerV2.class).patchProductosOrdenes(Long.valueOf(productosOrdenes.getId()), productosOrdenes)).withRel("patch_productos_ordenes"),
            linkTo(methodOn(ProductosOrdenesControllerV2.class).deleteProductosOrdenes(Long.valueOf(productosOrdenes.getId()))).withRel("eliminar_productos_ordenes")
            );
    }
}
