package com.easybuy.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.deckora.model.Categoria;
import com.deckora.model.ProductosCategorias;
import com.deckora.repository.CategoriaRepository;
import com.deckora.repository.ProductosCategoriasRepository;
import com.deckora.service.CategoriaService;


@SpringBootTest
public class CategoriaServiceTest {

    @Autowired
    private CategoriaService categoriaService;
    @MockBean
    private CategoriaRepository categoriaRepository;
    @MockBean
    private ProductosCategoriasRepository productoscategoriasRepository;

private Categoria createCategoria(){
    return new Categoria(
        1,
        "Juguetes para niños",
        new ArrayList<ProductosCategorias>());
}

    //
    @Test
    public void testFindAll(){
        when(categoriaRepository.findAll()).thenReturn(List.of(createCategoria()));
        List<Categoria> categorias = categoriaService.findAll();

        assertNotNull(categorias);
        assertEquals(1, categorias.size());
        assertEquals("Juguetes para niños", categorias.get(0).getDescripcion());
    }

    @Test
    public void testFindById(){
        when(categoriaRepository.findById(1L)).thenReturn(java.util.Optional.of(createCategoria()));
        Categoria categoria = categoriaService.findById(Long.valueOf(1));
        assertNotNull(categoria);
        assertEquals("Juguetes para niños", categoria.getDescripcion());
    }

    @Test
    public void testSave() {
        Categoria categoria = createCategoria();
        when(categoriaRepository.save(categoria)).thenReturn(categoria);
        Categoria savedCategoria = categoriaService.save(categoria);
        assertNotNull(savedCategoria);
        assertEquals("Juguetes para niños", savedCategoria.getDescripcion());
    }

    @Test
    public void testPatchCategoria() {
        Categoria existingCategoria = createCategoria();
        Categoria patchData = new Categoria();
        patchData.setDescripcion("Tecnologia");

        when(categoriaRepository.findById(1L)).thenReturn(java.util.Optional.of(existingCategoria));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(existingCategoria);

        Categoria patchedCategoria = categoriaService.patchCategoria(Long.valueOf(1), patchData);
        assertNotNull(patchedCategoria);
        assertEquals("Tecnologia", patchedCategoria.getDescripcion());
    }

    @Test
public void testDeleteCategoria() {
    Categoria categoria = createCategoria();

    when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
    when(productoscategoriasRepository.findByCategoria(categoria)).thenReturn(Collections.emptyList());

    categoriaService.delete(1L);


    verify(categoriaRepository, times(1)).delete(categoria);
}
}