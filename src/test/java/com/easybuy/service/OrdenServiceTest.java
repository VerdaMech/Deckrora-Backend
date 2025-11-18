package com.easybuy.service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.deckora.model.Envio;
import com.deckora.model.EstadoOrden;
import com.deckora.model.Orden;
import com.deckora.model.Pago;
import com.deckora.model.ProductosOrdenes;
import com.deckora.model.Usuario;
import com.deckora.repository.EnvioRepository;
import com.deckora.repository.OrdenRepository;
import com.deckora.repository.PagoRepository;
import com.deckora.repository.ProductosOrdenesRepository;
import com.deckora.service.OrdenService;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrdenServiceTest {

    @Autowired
    private OrdenService ordenService;

    @MockBean
    private ProductosOrdenesRepository productosOrdenesRepository;
    @MockBean
    private OrdenRepository ordenRepository;
    @MockBean
    private PagoRepository pagoRepository;
    @MockBean 
    private EnvioRepository envioRepository;


    private Orden createOrden() {
        return new Orden(
        1, 
        "18-06-2025", 
        15000, 
        new Usuario(), 
        new Pago(), 
        new EstadoOrden(), 
        new Envio(), 
        new ArrayList<ProductosOrdenes>());
    }

    @Test
    public void testFindAll() {
        when(ordenService.findAll()).thenReturn(List.of(createOrden()));
        List<Orden> usuarios = ordenService.findAll();
        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
    }

    @Test
    public void testFindById() {
        when(ordenRepository.findById(Long.valueOf(1))).thenReturn(java.util.Optional.of(createOrden()));
        Orden orden = ordenService.findById(Long.valueOf((1)));
        assertNotNull(orden);
        assertEquals("18-06-2025", orden.getFecha());
    }

    @Test
    public void testSave() {
        Orden orden = createOrden();
        when(ordenRepository.save(orden)).thenReturn(orden);
        Orden savedTipoUsuario = ordenService.save(orden);
        assertNotNull(savedTipoUsuario);
        assertEquals("18-06-2025", savedTipoUsuario.getFecha());
    }

    @Test
    public void testPatchOrden() {
        Orden existingOrden = createOrden();
        Orden patchData = new Orden();
        patchData.setFecha("17-06-2025");

        when(ordenRepository.findById(1L)).thenReturn(java.util.Optional.of(existingOrden));
        when(ordenRepository.save(any(Orden.class))).thenReturn(existingOrden);

        Orden patchedOrden = ordenService.patchOrden(Long.valueOf(1), patchData);
        assertNotNull(patchedOrden);
        assertEquals("17-06-2025", patchedOrden.getFecha());
    }

    @Test
    public void testDeleteOrden() {
        Orden orden = createOrden();

        when(ordenRepository.findById(1L)).thenReturn(Optional.of(orden));
        when(productosOrdenesRepository.findByOrden(orden)).thenReturn(Collections.emptyList());

        ordenService.delete(1L);


        verify(ordenRepository, times(1)).delete(orden);
    }

    //test query 1/que el servicio funcione correctamente
    @Test
    public void testbuscarPorEstadoYUsuario(){
        //le da valores a lo que pide este metodo
        Integer idEstado = 1;
        Integer idUsuario = 1;

        when(ordenRepository.buscarPorEstadoYUsuario(idEstado, idUsuario)).thenReturn(List.of(createOrden()));
        List<Orden> ordenes = ordenService.buscarPorEstadoYUsuario(idEstado, idUsuario);

        assertNotNull(ordenes);
        assertEquals(15000, ordenes.get(0).getTotal());
    }

    //test query 2
    @Test
    public void buscarPorPagoYProducto(){
        //le da valores a lo que pide este metodo
        Integer idPago = 1;
        Integer idProducto = 1;

        when(ordenRepository.buscarPorPagoYProducto(idPago, idProducto)).thenReturn(List.of(createOrden()));
        List<Orden> ordenes = ordenService.buscarPorPagoYProducto(idPago, idProducto);

        assertNotNull(ordenes);
        assertEquals(15000, ordenes.get(0).getTotal());
    }

    //test query 3
    @Test
    public void buscarOrdenesPorCantidadDeProductoYUsuario(){
        //le da valores a lo que pide este metodo
        Integer idUsuario = 1;
        Integer cantidadProduto = 1;

        when(ordenRepository.buscarOrdenesPorCantidadDeProductoYUsuario(idUsuario, cantidadProduto)).thenReturn(List.of(createOrden()));
        List<Orden> ordenes = ordenService.buscarOrdenesPorCantidadDeProductoYUsuario(idUsuario, cantidadProduto);

        assertNotNull(ordenes);
        assertEquals(15000, ordenes.get(0).getTotal());
    }

    //test query 4
    @Test
    public void buscarPorProductoYUsuario(){
        //le da valores a lo que pide este metodo
        Integer idProducto = 1;
        Integer idUsuario = 1;

        when(ordenRepository.buscarPorProductoYUsuario(idProducto, idUsuario)).thenReturn(List.of(createOrden()));
        List<Orden> ordenes = ordenService.buscarPorProductoYUsuario(idProducto, idUsuario);

        assertNotNull(ordenes);
        assertEquals(15000, ordenes.get(0).getTotal());
    }

    //Test buscar por pagos y envios
    @Test
    public void buscarPorPagoYEnvio() {
        // Datos de entrada simulados
        Integer idPago = 1;
        Integer idEnvio = 1;

        Pago pago = new Pago(idPago,"debito");

        Envio envio = new Envio(idEnvio,"Chile Correos");

        Orden orden = createOrden();
        orden.setPago(pago);
        orden.setDelivery(envio);

        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));
        when(envioRepository.findById(1L)).thenReturn(Optional.of(envio));
        when(ordenRepository.findByPagoAndDelivery(pago, envio)).thenReturn(List.of(orden));

        List<Orden> ordenes = ordenService.buscarPorPagoYDelivery(idPago, idEnvio);

        assertNotNull(ordenes);
        assertEquals(1, ordenes.size());
    }

    //Test buscar entre medio de fechas
    @Test
    public void testFindByFechaSolicitadaBetween() {
        String fechaInicio = "2025-06-20";
        String fechaFinal = "2025-06-21";
        when(ordenRepository.findByFechaBetween(fechaInicio, fechaFinal)).thenReturn(List.of(createOrden()));
        List<Orden> ordenes = ordenService.buscarEntreFechas(fechaInicio, fechaFinal);
        assertEquals(1, ordenes.size());
    }

}
