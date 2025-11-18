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

import com.deckora.assemblers.EstadoOrdenModelAssembler;
import com.deckora.model.EstadoOrden;
import com.deckora.service.EstadoOrdenService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v2/estadoOrdenes")
@Tag(name = "Estado Ordenes", description = "Operaciones relacionadas con la creación, modificación y eliminación de los estados de las ordenes")
public class EstadoOrdenControllerV2 {

    @Autowired
    private EstadoOrdenService estadoOrdenService;

    @Autowired
    private EstadoOrdenModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene todos los estados de ordenes", description = "Muestra una lista de todos los estados de ordenes creados")
    public ResponseEntity<CollectionModel<EntityModel<EstadoOrden>>> getAllEstadosOrdenes(){
        List <EntityModel<EstadoOrden>> listaEstadoOrdenes = estadoOrdenService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if(listaEstadoOrdenes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
            listaEstadoOrdenes,
            linkTo(methodOn(EstadoOrdenControllerV2.class).getAllEstadosOrdenes()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene un estado en específico", description = "A través de un id, este método muestra un estado de orden en específico")
    public ResponseEntity<EntityModel<EstadoOrden>> getEstadoOrdenById(@Parameter(description = "Id del estado", required = true, example = "1")@PathVariable Long id){
        EstadoOrden EstadoOrden = estadoOrdenService.findById(id);
        if (EstadoOrden == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(EstadoOrden));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método crea un estado de orden", description = "Crea un nuevo estado de orden, enviando un objeto a través de un POST")
    public ResponseEntity<EntityModel<EstadoOrden>> createEstadoOrden(@Parameter(description = "Detalles del estado", required = true)@RequestBody EstadoOrden EstadoOrden){
        EstadoOrden newEstadoOrden = estadoOrdenService.save(EstadoOrden);
        return ResponseEntity
                .created(linkTo(methodOn(EstadoOrdenControllerV2.class).getEstadoOrdenById(Long.valueOf(newEstadoOrden.getId()))).toUri())
                .body(assembler.toModel(newEstadoOrden));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método actualiza un estado", description = "A través de un id, este método actualiza un estado, pero se deben escribir todos los atributos")
    public ResponseEntity<EntityModel<EstadoOrden>> updateEstadoOrden(@Parameter(description = "Id del estado", required = true, example = "1")@PathVariable Long id, @Parameter(description = "Detalles del estado", required = true)@RequestBody EstadoOrden EstadoOrden){
        EstadoOrden.setId(EstadoOrden.getId());
        EstadoOrden updateEstadoOrden = estadoOrdenService.save(EstadoOrden);
        return ResponseEntity.ok(assembler.toModel(updateEstadoOrden));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método puede modificar un campo en específico en un estado", description = "A través de un id, este método actualiza un estado, este método actualiza solo el atributo que nosotros queremos modificar")
    public ResponseEntity<EntityModel<EstadoOrden>> patchEstadoOrden(@Parameter(description = "Id del estado", required = true, example = "1")@PathVariable Long id, @Parameter(description = "Campo a modificar", required = true)@RequestBody EstadoOrden parcialEstadoOrden){
        EstadoOrden patched = estadoOrdenService.patchEstadoOrden(id, parcialEstadoOrden);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método puede eliminar un estado", description = "Elimina el estado de orden especificado por el id")
    public ResponseEntity<Void> deleteEstadoOrden(@Parameter(description = "Id del estado", required = true, example = "1")@PathVariable Long id) {
        EstadoOrden existing = estadoOrdenService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        estadoOrdenService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
