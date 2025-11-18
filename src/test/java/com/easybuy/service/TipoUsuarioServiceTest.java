package com.easybuy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.deckora.model.TipoUsuario;
import com.deckora.repository.TipoUsuarioRepository;
import com.deckora.service.TipoUsuarioService;

@SpringBootTest
public class TipoUsuarioServiceTest {

    @Autowired
    private TipoUsuarioService tipoUsuarioService;

    @MockBean
    private TipoUsuarioRepository tipoUsuarioRepository;

    private TipoUsuario createTipoUsuario() {
        return new TipoUsuario(1, "ADMIN");
    }

    @Test
    public void testFindAll() {
        when(tipoUsuarioService.findAll()).thenReturn(List.of(createTipoUsuario()));
        List<TipoUsuario> tipoUsuarios = tipoUsuarioService.findAll();
        assertNotNull(tipoUsuarios);
        assertEquals(1, tipoUsuarios.size());
    }

    @Test
    public void testFindById() {
        when(tipoUsuarioRepository.findById(Long.valueOf(1))).thenReturn(java.util.Optional.of(createTipoUsuario()));
        TipoUsuario tipoUsuario = tipoUsuarioService.findById(Long.valueOf((1)));
        assertNotNull(tipoUsuario);
        assertEquals("ADMIN", tipoUsuario.getDescripcion());
    }

    @Test
    public void testSave() {
        TipoUsuario tipoUsuario = createTipoUsuario();
        when(tipoUsuarioRepository.save(tipoUsuario)).thenReturn(tipoUsuario);
        TipoUsuario savedTipoUsuario = tipoUsuarioService.save(tipoUsuario);
        assertNotNull(savedTipoUsuario);
        assertEquals("ADMIN", savedTipoUsuario.getDescripcion());
    }

    @Test
    public void testPatchTipoUsuario() {
        TipoUsuario existingTipoUsuario = createTipoUsuario();
        TipoUsuario patchData = new TipoUsuario();
        patchData.setDescripcion("CLIENTE");

        when(tipoUsuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(existingTipoUsuario));
        when(tipoUsuarioRepository.save(any(TipoUsuario.class))).thenReturn(existingTipoUsuario);

        TipoUsuario patchedTipoUsuario = tipoUsuarioService.patchTipoUsuario(Long.valueOf(1), patchData);
        assertNotNull(patchedTipoUsuario);
        assertEquals("CLIENTE", patchedTipoUsuario.getDescripcion());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(tipoUsuarioRepository).deleteById(1L);
        tipoUsuarioService.delete(1L);
        verify(tipoUsuarioRepository, times(1)).deleteById(1L);
    }
}
