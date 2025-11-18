package com.deckora.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deckora.model.Pago;
import com.deckora.service.PagoService;

@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<Pago>> listar(){
        List<Pago> listaPagos = pagoService.findAll();
        if(listaPagos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaPagos);
    }

    @PostMapping
    public ResponseEntity<Pago> guardar(@RequestBody Pago pago){
        Pago nuevoPago = pagoService.save(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPago);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> buscar(@PathVariable Long id){
        try{
            Pago pago = pagoService.findById(id);
            return ResponseEntity.ok(pago);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizar(@PathVariable Long id, @RequestBody Pago pago){
        try{
            pagoService.save(pago);
            return ResponseEntity.ok(pago);
        }catch( Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Pago> patchPago(@PathVariable Long id, @RequestBody Pago parcialPago) {
        try {
            Pago actualizarPago = pagoService.patchPago(id, parcialPago);
            return ResponseEntity.ok(actualizarPago);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            pagoService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
