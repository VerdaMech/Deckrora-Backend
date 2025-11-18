package com.deckora.assemblers;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.deckora.controller.OrdenControllerV2;
import com.deckora.model.Orden;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class OrdenModelAssembler implements RepresentationModelAssembler<Orden, EntityModel<Orden>>{
    
    @SuppressWarnings("null")
    @Override
    public EntityModel<Orden> toModel(Orden orden){
        return EntityModel.of(orden,
            linkTo(methodOn(OrdenControllerV2.class).getOrdenById(Long.valueOf(orden.getId()))).withSelfRel(),
            linkTo(methodOn(OrdenControllerV2.class).getAllOrdenes()).withRel("ordenes"),
            linkTo(methodOn(OrdenControllerV2.class).buscarPorEstadoYUsuario(1,1)).withRel("buscar_por_id_estado_y_id_usuario"),
            linkTo(methodOn(OrdenControllerV2.class).buscarPorPagoYProducto(1,1)).withRel("buscar_por_id_pago_y_id_producto"),
            linkTo(methodOn(OrdenControllerV2.class).buscarOrdenesPorCantidadDeProductoYUsuario(1,1)).withRel("buscar_por_cantidad_producto_y_id_usuario"),
            linkTo(methodOn(OrdenControllerV2.class).buscarPorProductoYUsuario(1,1)).withRel("buscar_por_producto_y_id_usuario"),
            linkTo(methodOn(OrdenControllerV2.class).createOrden(orden)).withRel("crear_orden"),
            linkTo(methodOn(OrdenControllerV2.class).updateOrden(Long.valueOf(orden.getId()),orden)).withRel("actualizar_orden"),
            linkTo(methodOn(OrdenControllerV2.class).patchOrden(Long.valueOf(orden.getId()), orden)).withRel("patch_orden"),
            linkTo(methodOn(OrdenControllerV2.class).deleteOrden(Long.valueOf(orden.getId()))).withRel("eliminar_orden"),
            linkTo(methodOn(OrdenControllerV2.class).getOrdenByFechaSolicitadaBetween("fecha_inicio", "fecha_final")).withRel("buscar_por_fecha_inicio_y_fecha_final"),
            linkTo(methodOn(OrdenControllerV2.class).getOrdenByPagoYEnvio(1, 1)).withRel("buscar_por_id_pago_y_id_envio")
            );
    }
}
