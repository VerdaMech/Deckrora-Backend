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

import com.deckora.assemblers.ProductoModelAssembler;
import com.deckora.model.Producto;
import com.deckora.service.ProductoService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v2/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas con la creacion, modificación y eliminación de los productos.")
public class ProductoControllerV2 {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler productoAssembler;

    //get de todos los productos
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene todos los productos", description = "Muestra una lista de todos los productos creados")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getAllProductos(){
        List <EntityModel<Producto>> listaProductos = productoService.findAll().stream()
                .map(productoAssembler::toModel)
                .collect(Collectors.toList());
        if(listaProductos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
            listaProductos,
            linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withSelfRel()
        ));
    }

    //get x id
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene un producto en específico", description = "A través de un id, este método muestra un producto en específico")
    public ResponseEntity<EntityModel<Producto>> getProductoById(@Parameter(description = "Id del producto", required = true, example = "1")@PathVariable Long id){
        Producto producto = productoService.findById(id);
        if (producto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productoAssembler.toModel(producto));
    }

    //get con el metodo especial (letra inicial)
    @GetMapping(value = "letra/{letra}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene un producto en específico", description = "A través de la letra inicial, este método muestra un producto o lista de productos con la letra indicada")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> buscarProductoPorLetra(@Parameter(description = "Letra inicial del producto", required = true, example = "s")
    @PathVariable  String letra){
    List<Producto> productos = productoService.buscarProductoPorLetra(letra.toLowerCase());
    List<EntityModel<Producto>> productosModel = productos.stream()
        .map(productoAssembler::toModel)
        .toList();

    return ResponseEntity.ok(CollectionModel.of(productosModel));
}

    //post crear un producto
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método crea un producto", description = "Crea un nuevo producto, enviando un objeto a través de un POST")
    public ResponseEntity<EntityModel<Producto>> createProducto(@Parameter(description = "Detalles del producto", required = true)@RequestBody Producto producto){
        Producto newProducto = productoService.save(producto);
        return ResponseEntity
                .created(linkTo(methodOn(ProductoControllerV2.class).getProductoById(Long.valueOf(newProducto.getId()))).toUri())
                .body(productoAssembler.toModel(newProducto));
    }

    //put para producto
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método actualiza un producto", description = "A través de un id, este método actualiza un producto, pero se deben escribir todos los atributos")
    public ResponseEntity<EntityModel<Producto>> updateProducto(@Parameter(description = "Id del producto", required = true, example = "1")@PathVariable Long id, @Parameter(description = "Detalles del producto", required = true)@RequestBody Producto producto){
        producto.setId(producto.getId()); //revisar despues
        Producto updateProducto = productoService.save(producto);
        return ResponseEntity.ok(productoAssembler.toModel(updateProducto));
    }

    //Patch producto
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método puede modificar un campo en específico en un producto", description = "A través de un id, este método actualiza un producto, este método actualiza solo el atributo que nosotros queremos modificar")
    public ResponseEntity<EntityModel<Producto>> patchProducto(@Parameter(description = "Id del producto", required = true, example = "1")@PathVariable Long id, @Parameter(description = "Campo a modificar", required = true)@RequestBody Producto parcialProducto){
        Producto patched = productoService.patchProducto(id, parcialProducto);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productoAssembler.toModel(patched));
    }
    
    //delete producto
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método puede eliminar un producto", description = "Elimina el producto especificado por el id")
    public ResponseEntity<Void> deleteProducto(@Parameter(description = "Id del producto", required = true, example = "1")@PathVariable Long id) {
        Producto productoExistente = productoService.findById(id);
        if (productoExistente == null) {
            return ResponseEntity.notFound().build();
        }
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
