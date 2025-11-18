package com.easybuy.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.deckora.model.Pago;
import com.deckora.repository.PagoRepository;
import com.deckora.service.PagoService;


@SpringBootTest
public class PagoServiceTest {

    @Autowired
    private PagoService pagoService;
    @MockBean
    private PagoRepository pagoRepository;

    private Pago createPago(){
        return new Pago(
            1,
            "Se realizo el pago");
    }


    //
    @Test
    public void testFindAll(){
        when(pagoRepository.findAll()).thenReturn(List.of(createPago()));
        List<Pago> pagos = pagoService.findAll();

        assertNotNull(pagos);
        assertEquals(1, pagos.size());
        assertEquals("Se realizo el pago", pagos.get(0).getDescripcion());
    }

    @Test
    public void testFindById(){
        when(pagoRepository.findById(1L)).thenReturn(java.util.Optional.of(createPago()));
        Pago pago = pagoService.findById(Long.valueOf(1));
        assertNotNull(pago);
        assertEquals("Se realizo el pago", pago.getDescripcion());
    }

    @Test
    public void testSave() {
        Pago pago = createPago();
        when(pagoRepository.save(pago)).thenReturn(pago);
        Pago savedPago = pagoService.save(pago);
        assertNotNull(savedPago);
        assertEquals("Se realizo el pago", savedPago.getDescripcion());
    }

    @Test
    public void testPatchPago() {
        Pago existingPago = createPago();
        Pago patchData = new Pago();
        patchData.setDescripcion("no se realizo el pago");

        when(pagoRepository.findById(1L)).thenReturn(java.util.Optional.of(existingPago));
        when(pagoRepository.save(any(Pago.class))).thenReturn(existingPago);

        Pago patchedPago = pagoService.patchPago(Long.valueOf(1), patchData);
        assertNotNull(patchedPago);
        assertEquals("no se realizo el pago", patchedPago.getDescripcion());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(pagoRepository).deleteById(Long.valueOf(1));
        pagoService.delete(Long.valueOf(1));
        verify(pagoRepository, times(1)).deleteById(Long.valueOf(1));
    }

}