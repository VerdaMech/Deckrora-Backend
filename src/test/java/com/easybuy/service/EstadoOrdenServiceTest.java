package com.easybuy.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.deckora.model.EstadoOrden;
import com.deckora.repository.EstadoOrdenRepository;
import com.deckora.service.EstadoOrdenService;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EstadoOrdenServiceTest {

    @Autowired
    private EstadoOrdenService estadoOrdenService;

    @MockBean
    private EstadoOrdenRepository estadoOrdenRepository;

    private EstadoOrden createEstadoOrden() {
        return new EstadoOrden(1, "STARKEN");
    }

    @Test
    public void testFindAll() {
        when(estadoOrdenService.findAll()).thenReturn(List.of(createEstadoOrden()));
        List<EstadoOrden> estadoOrdenes = estadoOrdenService.findAll();
        assertNotNull(estadoOrdenes);
        assertEquals(1, estadoOrdenes.size());
    }

    @Test
    public void testFindById() {
        when(estadoOrdenRepository.findById(Long.valueOf(1))).thenReturn(java.util.Optional.of(createEstadoOrden()));
        EstadoOrden estadoOrden = estadoOrdenService.findById(Long.valueOf((1)));
        assertNotNull(estadoOrden);
        assertEquals("STARKEN", estadoOrden.getDescripcion());
    }

    @Test
    public void testSave() {
        EstadoOrden estadoOrden = createEstadoOrden();
        when(estadoOrdenRepository.save(estadoOrden)).thenReturn(estadoOrden);
        EstadoOrden savedTipoUsuario = estadoOrdenService.save(estadoOrden);
        assertNotNull(savedTipoUsuario);
        assertEquals("STARKEN", savedTipoUsuario.getDescripcion());
    }

    @Test
    public void testPatchTipoUsuario() {
        EstadoOrden existingEstadoOrden = createEstadoOrden();
        EstadoOrden patchData = new EstadoOrden();
        patchData.setDescripcion("CORREOSCHILE");

        when(estadoOrdenRepository.findById(1L)).thenReturn(java.util.Optional.of(existingEstadoOrden));
        when(estadoOrdenRepository.save(any(EstadoOrden.class))).thenReturn(existingEstadoOrden);

        EstadoOrden patchedEstadoOrden = estadoOrdenService.patchEstadoOrden(Long.valueOf(1), patchData);
        assertNotNull(patchedEstadoOrden);
        assertEquals("CORREOSCHILE", patchedEstadoOrden.getDescripcion());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(estadoOrdenRepository).deleteById(1L);
        estadoOrdenService.delete(1L);
        verify(estadoOrdenRepository, times(1)).deleteById(1L);
    }
}
