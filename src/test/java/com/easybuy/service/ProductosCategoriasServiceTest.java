package com.easybuy.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.deckora.model.Categoria;
import com.deckora.model.Producto;
import com.deckora.model.ProductosCategorias;
import com.deckora.repository.ProductosCategoriasRepository;
import com.deckora.service.ProductosCategoriasService;


@SpringBootTest
public class ProductosCategoriasServiceTest {

    @Autowired
    private ProductosCategoriasService productosCategoriasService;
    @MockBean
    private ProductosCategoriasRepository productosCategoriasRepository;

private ProductosCategorias createProductosCategorias(){
    return new ProductosCategorias(
        1L,
        new Producto(),
        new Categoria());
}

    //
    @Test
    public void testFindAll(){
        when(productosCategoriasRepository.findAll()).thenReturn(List.of(createProductosCategorias()));
        List<ProductosCategorias> productosCategorias = productosCategoriasService.findAll();

        assertNotNull(productosCategorias);
        assertEquals(1, productosCategorias.size());
    }

    @Test
    public void testFindById(){
        when(productosCategoriasRepository.findById(1L)).thenReturn(java.util.Optional.of(createProductosCategorias()));
        ProductosCategorias productosCategorias = productosCategoriasService.findById(Long.valueOf(1));
        assertNotNull(productosCategorias);
        assertEquals(1L, productosCategorias.getId());
    }

    @Test
    public void testSave() {
        ProductosCategorias productosCategorias = createProductosCategorias();
        when(productosCategoriasRepository.save(productosCategorias)).thenReturn(productosCategorias);
        ProductosCategorias savedProductosCategorias = productosCategoriasService.save(productosCategorias);
        assertNotNull(savedProductosCategorias);
        assertEquals(1L, productosCategorias.getId());
    }

    /*
    @Test
    public void testPatchProductosCategorias() {
        ProductosCategorias existingProductosCategorias = createProductosCategorias();
        ProductosCategorias patchData = new ProductosCategorias();
        patchData.setId(2L);

        when(productosCategoriasRepository.findById(1L)).thenReturn(java.util.Optional.of(existingProductosCategorias));
        when(productosCategoriasRepository.save(any(ProductosCategorias.class))).thenReturn(existingProductosCategorias);

        ProductosCategorias patchedProductosCategorias = productosCategoriasService.patchProductosCategorias(Long.valueOf(1), patchData);
        assertNotNull(patchedProductosCategorias);
        assertEquals(2L, productosCategorias.getId());
    }
    */

    @Test
    public void testDeleteById() {
        doNothing().when(productosCategoriasRepository).deleteById(Long.valueOf(1));
        productosCategoriasService.delete(Long.valueOf(1));
        verify(productosCategoriasRepository, times(1)).deleteById(Long.valueOf(1));
    }

}