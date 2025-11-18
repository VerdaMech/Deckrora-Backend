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

import com.deckora.assemblers.OrdenModelAssembler;
import com.deckora.model.Orden;
import com.deckora.service.OrdenService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;


@RestController
@RequestMapping("/api/v2/ordenes")
@Tag(name = "Ordenes", description = "Operaciones relacionadas con la creación, modificación y eliminación de ordenes")
public class OrdenControllerV2 {

    @Autowired
    private OrdenService ordenService;

    @Autowired
    private OrdenModelAssembler assembler;



    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene todas las ordenes", description = "Muestra una lista de todas las ordenes creadas")
    public ResponseEntity<CollectionModel<EntityModel<Orden>>> getAllOrdenes(){
        List <EntityModel<Orden>> ordenes = ordenService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if(ordenes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
            ordenes,
            linkTo(methodOn(OrdenControllerV2.class).getAllOrdenes()).withSelfRel()
        ));
    }
                
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene una orden específica", description = "A través de un id, este método muestra una orden en específico")
    public ResponseEntity<EntityModel<Orden>> getOrdenById(@Parameter(description = "Id de la orden", required = true, example = "123") @PathVariable Long id){
        Orden orden = ordenService.findById(id);
        if (orden == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(orden));
    }

    //get con query 1
    @GetMapping(value = "por-estado/{idEstado}/por-usuario/{idUsuario}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene una orden especifica", description = "A través de el id de estado y id de usuario, este método muestra una orden o lista de ordenes filtradas por estado y usuario")
    public ResponseEntity<CollectionModel<EntityModel<Orden>>> buscarPorEstadoYUsuario(
        @Parameter(description = "id del estado", required = true, example = "1") @PathVariable Integer idEstado,
        @Parameter(description = "id del usuario", required = true, example = "1") @PathVariable  Integer idUsuario){

    List<Orden> ordenes = ordenService.buscarPorEstadoYUsuario(idEstado, idUsuario);
    
    List<EntityModel<Orden>> ordenesModel = ordenes.stream()
        .map(assembler::toModel)
        .toList();

        return ResponseEntity.ok(CollectionModel.of(ordenesModel));
    }

    //get con query 2
    @GetMapping(value = "por-pago/{idPago}/por-producto/{idProducto}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene una orden especifica", description = "A través de el id de pago y id de producto, este método muestra una orden o lista de ordenes filtradas por pago y producto") //productos que se pagan mas con x metodo de pago
    public ResponseEntity<CollectionModel<EntityModel<Orden>>> buscarPorPagoYProducto(
        @Parameter(description = "id del pago", required = true, example = "1") @PathVariable Integer idPago,
        @Parameter(description = "id del producto", required = true, example = "1") @PathVariable  Integer idProducto){

    List<Orden> ordenes = ordenService.buscarPorPagoYProducto(idPago, idProducto);

    List<EntityModel<Orden>> ordenesModel = ordenes.stream()
        .map(assembler::toModel)
        .toList();

        return ResponseEntity.ok(CollectionModel.of(ordenesModel));
    }
    //get con query 3
    @GetMapping(value = "por-usuario/{idUsuario}/por-cantidad-producto/{cantidadProducto}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene una orden especifica", description = "A través de un valor entregado y id de usuario, este método muestra una orden o lista de ordenes filtradas por cantidad de producto y usuario")
    public ResponseEntity<CollectionModel<EntityModel<Orden>>> buscarOrdenesPorCantidadDeProductoYUsuario(
        @Parameter(description = "id de usuario", required = true, example = "1") @PathVariable Integer idUsuario,
        @Parameter(description = "cantidad producto", required = true, example = "1") @PathVariable  Integer cantidadProducto){

    List<Orden> ordenes = ordenService.buscarOrdenesPorCantidadDeProductoYUsuario(idUsuario, cantidadProducto);

    List<EntityModel<Orden>> ordenesModel = ordenes.stream()
        .map(assembler::toModel)
        .toList();

        return ResponseEntity.ok(CollectionModel.of(ordenesModel));
    }
    //get con query 4
    @GetMapping(value = "por-producto/{idProducto}/por-usuario/{idUsuario}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene una orden especifica", description = "A través del id de un producto y id de usuario, este método muestra una orden o lista de ordenes filtradas por producto usuario")
    public ResponseEntity<CollectionModel<EntityModel<Orden>>> buscarPorProductoYUsuario(
        @Parameter(description = "id de producto", required = true, example = "1") @PathVariable Integer idProducto,
        @Parameter(description = "id de usuario", required = true, example = "1") @PathVariable Integer idUsuario){

    List<Orden> ordenes = ordenService.buscarPorProductoYUsuario(idProducto, idUsuario);

    List<EntityModel<Orden>> ordenesModel = ordenes.stream()
        .map(assembler::toModel)
        .toList();

        return ResponseEntity.ok(CollectionModel.of(ordenesModel));
    }

    //Metodo para buscar por fechas
    @GetMapping(value = "/fechas/{fechaInicio}/{fechaFin}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene una orden o lista de ordenes entre 2 fechas", description = "A través de 2 fechas, este método muestra una lista de ordenes realizadas entre medio de las 2 fechas ingresadas")
    public ResponseEntity<CollectionModel<EntityModel<Orden>>> getOrdenByFechaSolicitadaBetween(
        @Parameter(description = "Fecha Inicial", required = true, example = "2025-06-21") @PathVariable String fechaInicio, 
        @Parameter(description = "Fecha Final", required = true, example = "2025-06-22") @PathVariable String fechaFin) {
        List<Orden> ordenes = ordenService.buscarEntreFechas(fechaInicio, fechaFin);
        List<EntityModel<Orden>> ordenesModel = ordenes.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(ordenesModel));
    }

    //Metodo buscar por Id pago y Id envio
    @GetMapping(value = "/idPago/{idPago}/idEnvio/{idEnvio}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método obtiene una orden o lista de ordenes con parametros especificos", description = "A través del id de pago y id de Envio, este método muestra una orden o lista de ordenes filtradas por idPago y idEnvio")
    public ResponseEntity<CollectionModel<EntityModel<Orden>>> getOrdenByPagoYEnvio(
        @Parameter(description = "Id Pago", required = true, example = "1")@PathVariable Integer idPago, 
        @Parameter(description = "Id Envio", required = true, example = "1")@PathVariable Integer idEnvio) {
        List<Orden> ordenes = ordenService.buscarPorPagoYDelivery(idPago, idEnvio);
        List<EntityModel<Orden>> ordenesModel = ordenes.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(ordenesModel));
    }


    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método crea una nueva orden", description = "Crea una nueva orden, enviando un objeto a través de un POST")
    public ResponseEntity<EntityModel<Orden>> createOrden(@Parameter(description = "Detalles de la orden", required = true)@RequestBody Orden orden){
        Orden newOrden = ordenService.save(orden);
        return ResponseEntity
                .created(linkTo(methodOn(OrdenControllerV2.class).getOrdenById(Long.valueOf(newOrden.getId()))).toUri())
                .body(assembler.toModel(newOrden));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método actualiza una orden", description = "A través de un id, este método actualiza una orden, pero se deben escribir todos los atributos")
    public ResponseEntity<EntityModel<Orden>> updateOrden(@Parameter(description = "Id de la orden", required = true, example = "123")@PathVariable Long id, @Parameter(description = "Detalles de la orden", required = true) @RequestBody Orden orden){
        orden.setId(orden.getId());
        Orden updateOrden = ordenService.save(orden);
        return ResponseEntity.ok(assembler.toModel(updateOrden));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método puede modificar un campo en específico en una orden", description = "A través de un id, este método actualiza solo el atributo que nosotros queremos modificar")
    public ResponseEntity<EntityModel<Orden>> patchOrden(@Parameter(description = "Id de la orden", required = true, example = "123")@PathVariable Long id, @Parameter(description = "Campo de la orden a modificar", required = true)@RequestBody Orden parcialOrden){
        Orden patched = ordenService.patchOrden(id, parcialOrden);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Este método elimina una orden", description = "Elimina la orden especificada por el id")
    public ResponseEntity<Void> deleteOrden(@Parameter(description = "Id de la orden", required = true, example = "123")@PathVariable Long id) {
        Orden existing = ordenService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        ordenService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
