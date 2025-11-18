package com.deckora.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deckora.model.Pago;
import com.deckora.repository.PagoRepository;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    public List<Pago> findAll(){
        return pagoRepository.findAll();
    }

    public Pago findById(Long id){
        return pagoRepository.findById(id).get();
    }

    public Pago save(Pago pago){
        return pagoRepository.save(pago);
    }

    public void delete(Long id){
        pagoRepository.deleteById(id);
    }

    public Pago patchPago(Long id, Pago parcialPago){
        Optional<Pago> pagoOpcional = pagoRepository.findById(id);
        if (pagoOpcional.isPresent()) {
            
            Pago pagoActualizar = pagoOpcional.get();
            
            if (parcialPago.getDescripcion() != null) {
                pagoActualizar.setDescripcion(parcialPago.getDescripcion());
            }
            return pagoRepository.save(pagoActualizar);
        } else {
            return null;
        }
    }
}
