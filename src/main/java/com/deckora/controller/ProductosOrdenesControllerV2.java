package com.deckora.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
import org.springframework.web.bind.annotation.RestController;

import com.deckora.assemblers.ProductosOrdenesModelAssembler;
import com.deckora.model.ProductosOrdenes;
import com.deckora.service.ProductosOrdenesService;

import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v2/productosOrdenes")
@Tag(name = "Productos Ordenes", description = "Operaciones relacionadas con la creación, modificación y eliminación de las relaciones entre productos y ordenes.")
public class ProductosOrdenesControllerV2 {

    
    @Autowired
    private ProductosOrdenesService productosOrdenesService;
    @Autowired
    private ProductosOrdenesModelAssembler productosOrdenesAssembler;

    //get de todos los productosOrdenes
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene todos los productosOrdenes", description = "Muestra una lista de todos los productosOrdenes creados")
    public ResponseEntity<CollectionModel<EntityModel<ProductosOrdenes>>> getAllProductosOrdenes(){
        List <EntityModel<ProductosOrdenes>> listaProductosOrdenes = productosOrdenesService.findAll().stream()
                .map(productosOrdenesAssembler::toModel)
                .collect(Collectors.toList());
        if(listaProductosOrdenes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
            listaProductosOrdenes,
            linkTo(methodOn(ProductosOrdenesControllerV2.class).getAllProductosOrdenes()).withSelfRel()
        ));
    }

    //get x id
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene un productosOrdenes en específico", description = "A través de un id, este método muestra un productosOrdenes en específico")
    public ResponseEntity<EntityModel<ProductosOrdenes>> getProductosOrdenesById(@Parameter(description = "Id del productosOrdenes", required = true, example = "1")@PathVariable Long id){
        ProductosOrdenes productosOrdenes = productosOrdenesService.findById(id);
        if (productosOrdenes == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productosOrdenesAssembler.toModel(productosOrdenes));
    }

    //post crear un productosOrdenes
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método crea un productosOrdenes", description = "Crea un nuevo productosOrdenes, enviando un objeto a través de un POST")
    public ResponseEntity<EntityModel<ProductosOrdenes>> createProductosOrdenes(@Parameter(description = "Detalles del productosOrdenes", required = true)@RequestBody ProductosOrdenes productosOrdenes){
        ProductosOrdenes newProductosOrdenes = productosOrdenesService.save(productosOrdenes);
        return ResponseEntity
                .created(linkTo(methodOn(ProductosOrdenesControllerV2.class).getProductosOrdenesById(Long.valueOf(newProductosOrdenes.getId()))).toUri())
                .body(productosOrdenesAssembler.toModel(newProductosOrdenes));
    }

    //put para productosOrdenes
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método actualiza un productosOrdenes", description = "A través de un id, este método actualiza un productosOrdenes, pero se deben escribir todos los atributos")
    public ResponseEntity<EntityModel<ProductosOrdenes>> updateProductosOrdenes(@Parameter(description = "Id del productosOrdenes", required = true, example = "1")@PathVariable Long id, @Parameter(description = "Detalles del productosOrdenes", required = true)@RequestBody ProductosOrdenes productosOrdenes){
        productosOrdenes.setId(productosOrdenes.getId()); //revisar despues
        ProductosOrdenes updateProductosOrdenes = productosOrdenesService.save(productosOrdenes);
        return ResponseEntity.ok(productosOrdenesAssembler.toModel(updateProductosOrdenes));
    }

    //Patch productosOrdenes
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método puede modificar un campo en específico en un productosOrdenes", description = "A través de un id, este método actualiza un productosOrdenes, este método actualiza solo el atributo que nosotros queremos modificar")
    public ResponseEntity<EntityModel<ProductosOrdenes>> patchProductosOrdenes(@Parameter(description = "Id del productosOrdenes", required = true, example = "1")@PathVariable Long id, @Parameter(description = "Campo a modificar", required = true)@RequestBody ProductosOrdenes parcialProductosOrdenes){
        ProductosOrdenes patched = productosOrdenesService.patchProductosOrdenes(id, parcialProductosOrdenes);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productosOrdenesAssembler.toModel(patched));
    }
    
    //delete productosOrdenes
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método puede eliminar un productosOrdenes", description = "Elimina el productosOrdenes especificado por el id")
    public ResponseEntity<Void> deleteProductosOrdenes(@Parameter(description = "Id del productosOrdenes", required = true, example = "1")@PathVariable Long id) {
        ProductosOrdenes productosOrdenesExistente = productosOrdenesService.findById(id);
        if (productosOrdenesExistente == null) {
            return ResponseEntity.notFound().build();
        }
        productosOrdenesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
