package com.deckora.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
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

import com.deckora.assemblers.ImagenModelAssembler;
import com.deckora.model.Imagen;
import com.deckora.service.ImagenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/imagenes")
@Tag(name = "Imagenes", description = "Imagenes de las cartas")
public class ImagenController {
    
    @Autowired
    private ImagenService imagenService;

    @Autowired
    private ImagenModelAssembler imagenAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene todas las Imagenes", description = "Muestra una lista de todas las Imagenes")
    public ResponseEntity<CollectionModel<EntityModel<Imagen>>> getAllImagenes(){
        List <EntityModel<Imagen>> listaImagenes = imagenService.findAll().stream()
                .map(imagenAssembler::toModel)
                .collect(Collectors.toList());
        if(listaImagenes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
            listaImagenes,
            linkTo(methodOn(ImagenController.class).getAllImagenes()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene una imagen en específico", description = "A través de un id, este método muestra una imagen en específico")
    public ResponseEntity<EntityModel<Imagen>> getImagenById(@Parameter(description = "Id del imagen", required = true, example = "1")@PathVariable Long id){
        Imagen imagen = imagenService.findById(id);
        if (imagen == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(imagenAssembler.toModel(imagen));
    }

    //post crear un imagen
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método crea una imagen", description = "Crea nueva imagen, enviando un objeto a través de un POST")
    public ResponseEntity<EntityModel<Imagen>> createImagen(@Parameter(description = "Detalles del imagen", required = true)@RequestBody Imagen imagen){
        Imagen newImagen = imagenService.save(imagen);
        return ResponseEntity
                .created(linkTo(methodOn(ImagenController.class).getImagenById(Long.valueOf(newImagen.getId()))).toUri())
                .body(imagenAssembler.toModel(newImagen));
    }

    //put para imagen
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método actualiza una imagen", description = "A través de un id, este método actualiza una imagen, pero se deben escribir todos los atributos")
    public ResponseEntity<EntityModel<Imagen>> updateImagen(@Parameter(description = "Id del imagen", required = true, example = "1")@PathVariable Long id, @Parameter(description = "Detalles del imagen", required = true)@RequestBody Imagen imagen){
        imagen.setId(imagen.getId()); //revisar despues
        Imagen updateImagen = imagenService.save(imagen);
        return ResponseEntity.ok(imagenAssembler.toModel(updateImagen));
    }

    //Patch imagen
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método puede modificar un campo en específico en una imagen", description = "A través de un id, este método actualiza una imagen, este método actualiza solo el atributo que nosotros queremos modificar")
    public ResponseEntity<EntityModel<Imagen>> patchImagen(@Parameter(description = "Id del imagen", required = true, example = "1")@PathVariable Long id, @Parameter(description = "Campo a modificar", required = true)@RequestBody Imagen parcialImagen){
        Imagen patched = imagenService.patchImagen(id, parcialImagen);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(imagenAssembler.toModel(patched));
    }
    
    //delete imagen
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método puede eliminar una imagen", description = "Elimina la imagen especificada por el id")
    public ResponseEntity<Void> deleteImagen(@Parameter(description = "Id del imagen", required = true, example = "1")@PathVariable Long id) {
        Imagen imagenExistente = imagenService.findById(id);
        if (imagenExistente == null) {
            return ResponseEntity.notFound().build();
        }
        imagenService.delete(id);
        return ResponseEntity.noContent().build();
    }
    

}
