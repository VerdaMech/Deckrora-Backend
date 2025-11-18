package com.deckora.assemblers;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.deckora.controller.EstadoOrdenControllerV2;
import com.deckora.model.EstadoOrden;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EstadoOrdenModelAssembler implements RepresentationModelAssembler <EstadoOrden, EntityModel<EstadoOrden>> {
    @SuppressWarnings("null")
    @Override
    public EntityModel<EstadoOrden> toModel(EstadoOrden estadoOrden){
        return EntityModel.of(estadoOrden,
            linkTo(methodOn(EstadoOrdenControllerV2.class).getEstadoOrdenById(Long.valueOf(estadoOrden.getId()))).withSelfRel(),
            linkTo(methodOn(EstadoOrdenControllerV2.class).getAllEstadosOrdenes()).withRel("estados_ordenes"),
            linkTo(methodOn(EstadoOrdenControllerV2.class).createEstadoOrden(estadoOrden)).withRel("crear_estado_orden"),
            linkTo(methodOn(EstadoOrdenControllerV2.class).updateEstadoOrden(Long.valueOf(estadoOrden.getId()),estadoOrden)).withRel("actualizar_estado_orden"),
            linkTo(methodOn(EstadoOrdenControllerV2.class).patchEstadoOrden(Long.valueOf(estadoOrden.getId()), estadoOrden)).withRel("patch_estado_orden"),
            linkTo(methodOn(EstadoOrdenControllerV2.class).deleteEstadoOrden(Long.valueOf(estadoOrden.getId()))).withRel("eliminar_estado_orden")
            );
    }
}
