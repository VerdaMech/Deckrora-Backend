package com.deckora.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.deckora.controller.PagoControllerV2;
import com.deckora.model.Pago;

@Component
public class PagoModelAssembler implements RepresentationModelAssembler<Pago, EntityModel<Pago>>{
    @SuppressWarnings("null")
    @Override
    public EntityModel<Pago> toModel(Pago pago){
        return EntityModel.of(pago,
            linkTo(methodOn(PagoControllerV2.class).getPagoById(Long.valueOf(pago.getId()))).withSelfRel(),
            linkTo(methodOn(PagoControllerV2.class).getAllPagos()).withRel("pagos"),
            linkTo(methodOn(PagoControllerV2.class).createPago(pago)).withRel("crear_pago"),
            linkTo(methodOn(PagoControllerV2.class).updatePago(Long.valueOf(pago.getId()),pago)).withRel("actualizar_pago"),
            linkTo(methodOn(PagoControllerV2.class).patchPago(Long.valueOf(pago.getId()), pago)).withRel("patch_pago"),
            linkTo(methodOn(PagoControllerV2.class).deletePago(Long.valueOf(pago.getId()))).withRel("eliminar_pago")
            );
    }
}