package com.easybuy.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.deckora.model.TipoUsuario;
import com.deckora.model.Usuario;
import com.deckora.repository.UsuarioRepository;
import com.deckora.service.UsuarioService;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;


    private Usuario createUsuario() {
        return new Usuario(1, "contra",
        "21031616-7", 
        "vicente", 
        "verdaguer", 
        "vverdaguer@gmail.com", 
        "lonquen", 
        987654321L, 
        new TipoUsuario());
    }

    @Test
    public void testFindAll() {
        when(usuarioService.findAll()).thenReturn(List.of(createUsuario()));
        List<Usuario> usuarios = usuarioService.findAll();
        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
    }

    @Test
    public void testFindById() {
        when(usuarioRepository.findById(Long.valueOf(1))).thenReturn(java.util.Optional.of(createUsuario()));
        Usuario usuario = usuarioService.findById(Long.valueOf((1)));
        assertNotNull(usuario);
        assertEquals("vicente", usuario.getNombre());
    }

    @Test
    public void testSave() {
        Usuario usuario = createUsuario();
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        Usuario savedTipoUsuario = usuarioService.save(usuario);
        assertNotNull(savedTipoUsuario);
        assertEquals("vicente", savedTipoUsuario.getNombre());
    }

    @Test
    public void testPatchTipoUsuario() {
        Usuario existingUsuario = createUsuario();
        Usuario patchData = new Usuario();
        patchData.setNombre("anais");

        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(existingUsuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(existingUsuario);

        Usuario patchedUsuario = usuarioService.patchUsuario(Long.valueOf(1), patchData);
        assertNotNull(patchedUsuario);
        assertEquals("anais", patchedUsuario.getNombre());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(usuarioRepository).deleteById(1L);
        usuarioService.delete(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    //Test buscar por nombre y apellidos
    @Test
    public void testFindByNombreYApellido() {
        String nombre = "anais";
        String apellido = "palma";
        when(usuarioRepository.findByNombreAndApellido(nombre,apellido)).thenReturn(List.of(createUsuario()));
        List<Usuario> usuarios = usuarioService.buscarPorNombreYApellido(nombre,apellido);
        assertNotNull(usuarios);
        assertFalse(usuarios.isEmpty());
    }

    //Test Metodo buscar por nombre y tipo usuario
    @Test
    public void testFindByNombreYTipoUsuario() {
        Integer tipoUsuario = 1;
        when(usuarioRepository.findByNombreAndTipoUsuarioId("vicente",tipoUsuario)).thenReturn(List.of(createUsuario()));
        List<Usuario> usuarios = usuarioService.buscarPorNombreYTipo("vicente",tipoUsuario);
        assertNotNull(usuarios);
        assertEquals("vicente",usuarios.get(0).getNombre());
        assertFalse(usuarios.isEmpty());
    }


    //Test metodo antiguo de buscar por letra
    @Test
    public void testFindByLetra(){

        when(usuarioRepository.buscarUsuariosPorLetraInicial("v")).thenReturn(List.of(createUsuario()));
        List<Usuario> usuarios = usuarioService.buscarUsuariosPorLetra("v");

        assertNotNull(usuarios);
        assertEquals("vicente", usuarios.get(0).getNombre());
    }
}
