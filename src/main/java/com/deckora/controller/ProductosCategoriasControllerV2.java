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

import com.deckora.assemblers.ProductosCategoriasModelAssembler;
import com.deckora.model.ProductosCategorias;
import com.deckora.service.ProductosCategoriasService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v2/productosCategorias")
@Tag(name = "Producto Categoria", description = "Operaciones relacionadas con la creacion, modificación y eliminación de las categorias de los productos.")
public class ProductosCategoriasControllerV2 {

    @Autowired
    private ProductosCategoriasService productosCategoriasService;

    @Autowired
    private ProductosCategoriasModelAssembler productosCategoriasAssembler;

    //get de todos los productos pategorias
        @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene todos las categorias de los productos", description = "Muestra una lista de todos las categorias de los productos creadas")
    public ResponseEntity<CollectionModel<EntityModel<ProductosCategorias>>> getAllProductosCategorias(){
        List <EntityModel<ProductosCategorias>> listaProductosCategorias = productosCategoriasService.findAll().stream()
                .map(productosCategoriasAssembler::toModel)
                .collect(Collectors.toList());
        if(listaProductosCategorias.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
            listaProductosCategorias,
            linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withSelfRel()
        ));
    }

    //get x id
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene una categoria productos en específico", description = "A través de un id, este método muestra una categoria productos  en específico")
    public ResponseEntity<EntityModel<ProductosCategorias>> getProductosCategoriasById(@Parameter(description = "Id de categoria productos ", required = true, example = "1")@PathVariable Long id){
        ProductosCategorias productosCategorias = productosCategoriasService.findById(id);
        if (productosCategorias == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productosCategoriasAssembler.toModel(productosCategorias));
    }

    //post crear un ProductosCategorias
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método crea un producto categoria", description = "Crea un nuevo producto categoria, enviando un objeto a través de un POST")
    public ResponseEntity<EntityModel<ProductosCategorias>> createProductosCategorias(@Parameter(description = "Detalles del producto categoria", required = true)@RequestBody ProductosCategorias productosCategorias){
        ProductosCategorias newProductosCategorias = productosCategoriasService.save(productosCategorias);
        return ResponseEntity
                .created(linkTo(methodOn(ProductoControllerV2.class).getProductoById(Long.valueOf(newProductosCategorias.getId()))).toUri())
                .body(productosCategoriasAssembler.toModel(newProductosCategorias ));
    }

    //put para productos
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método actualiza un producto categoria", description = "A través de un id, este método actualiza un producto categoria, pero se deben escribir todos los atributos")
    public ResponseEntity<EntityModel<ProductosCategorias>> updateProductosCategorias(@Parameter(description = "Id del producto categoria", required = true, example = "1")@PathVariable Long id, @Parameter(description = "Detalles del producto", required = true)@RequestBody ProductosCategorias productosCategorias){
        productosCategorias.setId(productosCategorias.getId()); //revisar despues
        ProductosCategorias updateProductosCategorias = productosCategoriasService.save(productosCategorias);
        return ResponseEntity.ok(productosCategoriasAssembler.toModel(updateProductosCategorias));
    }

    //Patch producto
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método puede modificar un campo en específico en un producto categoria", description = "A través de un id, este método actualiza un producto categoria, este método actualiza solo el atributo que nosotros queremos modificar")
    public ResponseEntity<EntityModel<ProductosCategorias>> patchProductosCategorias(@Parameter(description = "Id del producto categoria", required = true, example = "1")@PathVariable Long id, @Parameter(description = "Campo a modificar", required = true)@RequestBody ProductosCategorias parcialProductosCategorias){
        ProductosCategorias patched = productosCategoriasService.patchProductosCategorias(id, parcialProductosCategorias);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productosCategoriasAssembler.toModel(patched));
    }
    
    //delete producto
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método puede eliminar un producto categoria", description = "Elimina el producto categoria especificado por el id")
    public ResponseEntity<Void> deleteProductosCategorias(@Parameter(description = "Id del producto", required = true, example = "1")@PathVariable Long id) {
        ProductosCategorias productosCategoriasExistente = productosCategoriasService.findById(id);
        if (productosCategoriasExistente == null) {
            return ResponseEntity.notFound().build();
        }
        productosCategoriasService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
