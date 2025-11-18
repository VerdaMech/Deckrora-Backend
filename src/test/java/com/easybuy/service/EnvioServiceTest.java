package com.easybuy.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.deckora.model.Envio;
import com.deckora.repository.EnvioRepository;
import com.deckora.service.EnvioService;


@SpringBootTest
public class EnvioServiceTest {

    @Autowired
    private EnvioService envioService;
    @MockBean
    private EnvioRepository envioRepository;

    private Envio createEnvio(){
        return new Envio(
            1,
            "Envio");
    }


    //
    @Test
    public void testFindAll(){
        when(envioRepository.findAll()).thenReturn(List.of(createEnvio()));
        List<Envio> envios = envioService.findAll();

        assertNotNull(envios);
        assertEquals(1, envios.size());
        assertEquals("Envio", envios.get(0).getNombre_envio());
    }

    @Test
    public void testFindById(){
        when(envioRepository.findById(1L)).thenReturn(java.util.Optional.of(createEnvio()));
        Envio envio = envioService.findById(Long.valueOf(1));
        assertNotNull(envio);
        assertEquals("Envio", envio.getNombre_envio());
    }

    @Test
    public void testSave() {
        Envio envio = createEnvio();
        when(envioRepository.save(envio)).thenReturn(envio);
        Envio savedEnvio = envioService.save(envio);
        assertNotNull(savedEnvio);
        assertEquals("Envio", savedEnvio.getNombre_envio());
    }

    @Test
    public void testPatchEnvio() {
        Envio existingEnvio = createEnvio();
        Envio patchData = new Envio();
        patchData.setNombre_envio("Super envio");

        when(envioRepository.findById(1L)).thenReturn(java.util.Optional.of(existingEnvio));
        when(envioRepository.save(any(Envio.class))).thenReturn(existingEnvio);

        Envio patchedEnvio = envioService.patchEnvio(Long.valueOf(1), patchData);
        assertNotNull(patchedEnvio);
        assertEquals("Super envio", patchedEnvio.getNombre_envio());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(envioRepository).deleteById(Long.valueOf(1));
        envioService.delete(Long.valueOf(1));
        verify(envioRepository, times(1)).deleteById(Long.valueOf(1));
    }

}