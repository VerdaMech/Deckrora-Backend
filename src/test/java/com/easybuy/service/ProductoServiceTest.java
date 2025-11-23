/* package com.easybuy.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.deckora.model.Producto;
import com.deckora.model.ProductosCategorias;
import com.deckora.model.ProductosOrdenes;
import com.deckora.repository.ImagenRepository;
import com.deckora.repository.ProductoRepository;
import com.deckora.repository.ProductosCategoriasRepository;
import com.deckora.repository.ProductosOrdenesRepository;
import com.deckora.service.ProductoService;

@SpringBootTest
public class ProductoServiceTest {

    @Autowired
    private ProductoService productoService;
    @MockBean
    private ProductosCategoriasRepository productosCategoriasRepository;
    @MockBean
    private ProductosOrdenesRepository productosOrdenesRepository;
    @MockBean
    private ProductoRepository productoRepository;
    @MockBean
    private ImagenRepository imagenRepository;

    private Producto createProducto(){
        return new Producto(
            1,
            "Switch",
            300000,
            4,
            new ArrayList<ProductosCategorias>(),
            new ArrayList<ProductosOrdenes>(),
            new ArrayList<Imagen>());
    }


    //
    @Test
    public void testFindAll(){
        when(productoRepository.findAll()).thenReturn(List.of(createProducto()));
        List<Producto> productos = productoService.findAll();

        assertNotNull(productos);
        assertEquals(1, productos.size());
        assertEquals("Switch", productos.get(0).getNombre_producto());
    }

    @Test
    public void testFindByLetra(){
        when(productoRepository.buscarProductoPorLetraInicial("s")).thenReturn(List.of(createProducto()));
        List<Producto> productos = productoService.buscarProductoPorLetra("s");

        assertNotNull(productos);
        assertEquals("Switch", productos.get(0).getNombre_producto());
    }

    @Test
    public void testFindById(){
        when(productoRepository.findById(1L)).thenReturn(java.util.Optional.of(createProducto()));
        Producto producto = productoService.findById(Long.valueOf(1));
        assertNotNull(producto);
        assertEquals("Switch", producto.getNombre_producto());
    }

    @Test
    public void testSave() {
        Producto producto = createProducto();
        when(productoRepository.save(producto)).thenReturn(producto);
        Producto savedProducto = productoService.save(producto);
        assertNotNull(savedProducto);
        assertEquals("Switch", savedProducto.getNombre_producto());
    }

    @Test
    public void testPatchProducto() {
        Producto existingProducto = createProducto();
        Producto patchData = new Producto();
        patchData.setNombre_producto("Switch 2");

        when(productoRepository.findById(1L)).thenReturn(java.util.Optional.of(existingProducto));
        when(productoRepository.save(any(Producto.class))).thenReturn(existingProducto);

        Producto patchedProducto = productoService.patchProducto(Long.valueOf(1), patchData);
        assertNotNull(patchedProducto);
        assertEquals("Switch 2", patchedProducto.getNombre_producto());
    }

@Test
public void testDeleteProducto() {
    Producto producto = createProducto();

    when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
    when(productosCategoriasRepository.findByProducto(producto)).thenReturn(Collections.emptyList());
    when(productosOrdenesRepository.findByProducto(producto)).thenReturn(Collections.emptyList());

    productoService.delete(1L);


    verify(productoRepository, times(1)).delete(producto);
}


} */