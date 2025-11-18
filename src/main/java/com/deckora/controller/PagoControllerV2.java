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

import com.deckora.assemblers.PagoModelAssembler;
import com.deckora.model.Pago;
import com.deckora.service.PagoService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v2/pagos")
@Tag(name = "Pagos", description = "Operaciones relacionadas con la creacion, modificación y eliminación de los pagos.")
public class PagoControllerV2 {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PagoModelAssembler pagoAssembler;

    //get de todos los pagos
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene todos los pagos", description = "Muestra una lista de todos los pagos creados")
    public ResponseEntity<CollectionModel<EntityModel<Pago>>> getAllPagos(){
        List <EntityModel<Pago>> listaPagos = pagoService.findAll().stream()
                .map(pagoAssembler::toModel)
                .collect(Collectors.toList());
        if(listaPagos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
            listaPagos,
            linkTo(methodOn(PagoControllerV2.class).getAllPagos()).withSelfRel()
        ));
    }

    //get x id
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene un pago en específico", description = "A través de un id, este método muestra un pago en específico")
    public ResponseEntity<EntityModel<Pago>> getPagoById(@Parameter(description = "Id del pago", required = true, example = "1")@PathVariable Long id){
        Pago pago = pagoService.findById(id);
        if (pago == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pagoAssembler.toModel(pago));
    }

    //post crear un pago
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método crea un pago", description = "Crea un nuevo pago, enviando un objeto a través de un POST")
    public ResponseEntity<EntityModel<Pago>> createPago(@Parameter(description = "Detalles del pago", required = true)@RequestBody Pago pago){
        Pago newPago = pagoService.save(pago);
        return ResponseEntity
                .created(linkTo(methodOn(PagoControllerV2.class).getPagoById(Long.valueOf(newPago.getId()))).toUri())
                .body(pagoAssembler.toModel(newPago));
    }

    //put para pago
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método actualiza un pago", description = "A través de un id, este método actualiza un pago, pero se deben escribir todos los atributos")
    public ResponseEntity<EntityModel<Pago>> updatePago(@Parameter(description = "Id del pago", required = true, example = "1")@PathVariable Long id, @Parameter(description = "Detalles del pago", required = true)@RequestBody Pago pago){
        pago.setId(pago.getId()); //revisar despues
        Pago updatePago = pagoService.save(pago);
        return ResponseEntity.ok(pagoAssembler.toModel(updatePago));
    }

    //Patch pago
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método puede modificar un campo en específico en un pago", description = "A través de un id, este método actualiza un pago, este método actualiza solo el atributo que nosotros queremos modificar")
    public ResponseEntity<EntityModel<Pago>> patchPago(@Parameter(description = "Id del pago", required = true, example = "1")@PathVariable Long id, @Parameter(description = "Campo a modificar", required = true)@RequestBody Pago parcialPago){
        Pago patched = pagoService.patchPago(id, parcialPago);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pagoAssembler.toModel(patched));
    }
    
    //delete pago
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método puede eliminar un pago", description = "Elimina el pago especificado por el id")
    public ResponseEntity<Void> deletePago(@Parameter(description = "Id del pago", required = true, example = "1")@PathVariable Long id) {
        Pago pagoExistente = pagoService.findById(id);
        if (pagoExistente == null) {
            return ResponseEntity.notFound().build();
        }
        pagoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
