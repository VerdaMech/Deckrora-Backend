package com.deckora.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deckora.model.EstadoOrden;
import com.deckora.repository.EstadoOrdenRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EstadoOrdenService {

    @Autowired
    private EstadoOrdenRepository estadoOrdenRepository;

    public List<EstadoOrden> findAll(){
        return estadoOrdenRepository.findAll();
    }

    public EstadoOrden findById(Long id){
        return estadoOrdenRepository.findById(id).get();
    }

    public EstadoOrden save(EstadoOrden estadoOrden){
        return estadoOrdenRepository.save(estadoOrden);
    }

    public void delete(Long id){
        estadoOrdenRepository.deleteById(id);
    }

    public EstadoOrden patchEstadoOrden(Long id, EstadoOrden parcialEstadoOrden){
        Optional<EstadoOrden> estadoOrdenOpcional = estadoOrdenRepository.findById(id);
        if (estadoOrdenOpcional.isPresent()) {
            
            EstadoOrden estadoOrdenActualizar = estadoOrdenOpcional.get();
            
            if (parcialEstadoOrden.getDescripcion() != null) {
                estadoOrdenActualizar.setDescripcion(parcialEstadoOrden.getDescripcion());
            }
            return estadoOrdenRepository.save(estadoOrdenActualizar);
        } else {
            return null;
        }
    }
}
