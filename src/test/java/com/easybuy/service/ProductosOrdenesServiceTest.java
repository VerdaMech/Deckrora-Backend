package com.easybuy.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.deckora.model.Orden;
import com.deckora.model.Producto;
import com.deckora.model.ProductosOrdenes;
import com.deckora.repository.ProductosOrdenesRepository;
import com.deckora.service.ProductosOrdenesService;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductosOrdenesServiceTest {

    @Autowired
    private ProductosOrdenesService productosOrdenesService;

    @MockBean
    private ProductosOrdenesRepository productosOrdenesRepository;


    private ProductosOrdenes createProductosOrdenes() {
        return new ProductosOrdenes(
        1, 
        10, 
        new Producto(), 
        new Orden());
    }

    @Test
    public void testFindAll() {
        when(productosOrdenesService.findAll()).thenReturn(List.of(createProductosOrdenes()));
        List<ProductosOrdenes> listaProductosOrdenes = productosOrdenesService.findAll();
        assertNotNull(listaProductosOrdenes);
        assertEquals(1, listaProductosOrdenes.size());
    }

    @Test
    public void testFindById() {
        when(productosOrdenesRepository.findById(Long.valueOf(1))).thenReturn(java.util.Optional.of(createProductosOrdenes()));
        ProductosOrdenes productosOrdenes = productosOrdenesService.findById(Long.valueOf((1)));
        assertNotNull(productosOrdenes);
        assertEquals(10, productosOrdenes.getCantidad_producto());
    }

    @Test
    public void testSave() {
        ProductosOrdenes productosOrdenes = createProductosOrdenes();
        when(productosOrdenesRepository.save(productosOrdenes)).thenReturn(productosOrdenes);
        ProductosOrdenes savedTipoUsuario = productosOrdenesService.save(productosOrdenes);
        assertNotNull(savedTipoUsuario);
        assertEquals(10, savedTipoUsuario.getCantidad_producto());
    }

    @Test
    public void testPatchProductosOrdenes() {
        ProductosOrdenes existingProductosOrdenes = createProductosOrdenes();
        ProductosOrdenes patchData = new ProductosOrdenes();
        patchData.setCantidad_producto(15);

        when(productosOrdenesRepository.findById(1L)).thenReturn(java.util.Optional.of(existingProductosOrdenes));
        when(productosOrdenesRepository.save(any(ProductosOrdenes.class))).thenReturn(existingProductosOrdenes);

        ProductosOrdenes patchedProductosOrdenes = productosOrdenesService.patchProductosOrdenes(Long.valueOf(1), patchData);
        assertNotNull(patchedProductosOrdenes);
        assertEquals(15 ,patchedProductosOrdenes.getCantidad_producto());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(productosOrdenesRepository).deleteById(1L);
        productosOrdenesService.delete(1L);
        verify(productosOrdenesRepository, times(1)).deleteById(1L);
    }
}
