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

import com.deckora.assemblers.EnvioModelAssembler;
import com.deckora.model.Envio;
import com.deckora.service.EnvioService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v2/envios")
@Tag(name = "Envios", description = "Operaciones relacionadas con la creación, modificación y eliminación de los envios.")
public class EnvioControllerV2 {

    @Autowired
    private EnvioService envioService;

    @Autowired
    private EnvioModelAssembler envioAssembler;

    //get de todos los envios
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene todos los envios", description = "Muestra una lista de todos los envios creados")
    public ResponseEntity<CollectionModel<EntityModel<Envio>>> getAllEnvios(){
        List <EntityModel<Envio>> listaEnvios = envioService.findAll().stream()
                .map(envioAssembler::toModel)
                .collect(Collectors.toList());
        if(listaEnvios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
            listaEnvios,
            linkTo(methodOn(EnvioControllerV2.class).getAllEnvios()).withSelfRel()
        ));
    }

    //get x id
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene un envio en específico", description = "A través de un id, este método muestra un envio en específico")
    public ResponseEntity<EntityModel<Envio>> getEnvioById(@Parameter(description = "Id del envio", required = true, example = "1")@PathVariable Long id){
        Envio envio = envioService.findById(id);
        if (envio == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(envioAssembler.toModel(envio));
    }

    //post crear un envio
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método crea un envio", description = "Crea un nuevo envio, enviando un objeto a través de un POST")
    public ResponseEntity<EntityModel<Envio>> createEnvio(@Parameter(description = "Detalles del envio", required = true)@RequestBody Envio envio){
        Envio newEnvio = envioService.save(envio);
        return ResponseEntity
                .created(linkTo(methodOn(EnvioControllerV2.class).getEnvioById(Long.valueOf(newEnvio.getId()))).toUri())
                .body(envioAssembler.toModel(envio));
    }

    //put para envio
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método actualiza un envio", description = "A través de un id, este método actualiza un envio, pero se deben escribir todos los atributos")
    public ResponseEntity<EntityModel<Envio>> updateEnvio(@Parameter(description = "Id del envio", required = true, example = "1")@PathVariable Long id, @Parameter(description = "Detalles del envio", required = true)@RequestBody Envio envio){
        envio.setId(envio.getId()); //revisar despues
        Envio updateEnvio = envioService.save(envio);
        return ResponseEntity.ok(envioAssembler.toModel(updateEnvio));
    }

    //Patch envio
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método puede modificar un campo en específico en un envio", description = "A través de un id, este método actualiza un envio, este método actualiza solo el atributo que nosotros queremos modificar")
    public ResponseEntity<EntityModel<Envio>> patchEnvio(@Parameter(description = "Id del envio", required = true, example = "1")@PathVariable Long id, @Parameter(description = "Campo a modificar", required = true)@RequestBody Envio parcialEnvio){
        Envio patched = envioService.patchEnvio(id, parcialEnvio);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(envioAssembler.toModel(patched));
    }
    
    //delete envio
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método puede eliminar un envio", description = "Elimina el envio especificado por el id")
    public ResponseEntity<Void> deleteEnvio(@Parameter(description = "Id del envio", required = true, example = "1")@PathVariable Long id) {
        Envio envioExistente = envioService.findById(id);
        if (envioExistente == null) {
            return ResponseEntity.notFound().build();
        }
        envioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
