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

import com.deckora.assemblers.CategoriaModelAssembler;
import com.deckora.model.Categoria;
import com.deckora.service.CategoriaService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v2/categorias")
//tag es para dar el titulo en el swagger de la categoria
@Tag(name = "Categorias", description = "Operaciones relacionadas con la creacion, modificación y eliminación de las categorias.")
public class CategoriaControllerV2 {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CategoriaModelAssembler categoriaAssembler;

    //get de todos las categorias
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    //operation es para el resumen de lo que hace
    //paramater es para describir el tipo de dato esperado
    @Operation(summary = "Este método obtiene todas las categorias", description = "Muestra una lista de todas las categorias creadas")
    public ResponseEntity<CollectionModel<EntityModel<Categoria>>> getAllCategorias(){
        List <EntityModel<Categoria>> listaCategorias = categoriaService.findAll().stream()
                .map(categoriaAssembler::toModel)
                .collect(Collectors.toList());
        if(listaCategorias.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
            listaCategorias,
            linkTo(methodOn(CategoriaControllerV2.class).getAllCategorias()).withSelfRel()
        ));
    }

    //get x id
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene una categoria en específico", description = "A través de un id, este método muestra una categoria en específico")
    public ResponseEntity<EntityModel<Categoria>> getCategoriaById(@Parameter(description = "Id del categoria", required = true, example = "1")@PathVariable Long id){
        Categoria categoria = categoriaService.findById(id);
        if (categoria == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoriaAssembler.toModel(categoria));
    }

    //post crear un categoria
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método crea una categoria", description = "Crea nueva categoria, enviando un objeto a través de un POST")
    public ResponseEntity<EntityModel<Categoria>> createCategoria(@Parameter(description = "Detalles del categoria", required = true)@RequestBody Categoria categoria){
        Categoria newCategoria = categoriaService.save(categoria);
        return ResponseEntity
                .created(linkTo(methodOn(CategoriaControllerV2.class).getCategoriaById(Long.valueOf(newCategoria.getId()))).toUri())
                .body(categoriaAssembler.toModel(newCategoria));
    }

    //put para categoria
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método actualiza una categoria", description = "A través de un id, este método actualiza una categoria, pero se deben escribir todos los atributos")
    public ResponseEntity<EntityModel<Categoria>> updateCategoria(@Parameter(description = "Id del categoria", required = true, example = "1")@PathVariable Long id, @Parameter(description = "Detalles del categoria", required = true)@RequestBody Categoria categoria){
        categoria.setId(categoria.getId()); //revisar despues
        Categoria updateCategoria = categoriaService.save(categoria);
        return ResponseEntity.ok(categoriaAssembler.toModel(updateCategoria));
    }

    //Patch categoria
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método puede modificar un campo en específico en una categoria", description = "A través de un id, este método actualiza una categoria, este método actualiza solo el atributo que nosotros queremos modificar")
    public ResponseEntity<EntityModel<Categoria>> patchCategoria(@Parameter(description = "Id del categoria", required = true, example = "1")@PathVariable Long id, @Parameter(description = "Campo a modificar", required = true)@RequestBody Categoria parcialCategoria){
        Categoria patched = categoriaService.patchCategoria(id, parcialCategoria);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoriaAssembler.toModel(patched));
    }
    
    //delete categoria
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método puede eliminar una categoria", description = "Elimina la categoria especificada por el id")
    public ResponseEntity<Void> deleteCategoria(@Parameter(description = "Id del categoria", required = true, example = "1")@PathVariable Long id) {
        Categoria categoriaExistente = categoriaService.findById(id);
        if (categoriaExistente == null) {
            return ResponseEntity.notFound().build();
        }
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
