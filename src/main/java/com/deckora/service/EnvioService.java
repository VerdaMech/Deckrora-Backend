package com.deckora.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deckora.model.Envio;
import com.deckora.repository.EnvioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EnvioService {

    @Autowired
    private EnvioRepository envioRepository;

    public List<Envio> findAll(){
        return envioRepository.findAll();
    }

    public Envio findById(Long id){
        return envioRepository.findById(id).get();
    }

    public Envio save(Envio envio){
        return envioRepository.save(envio);
    }

    public void delete(Long id){
        envioRepository.deleteById(id);
    }

    public Envio patchEnvio(Long id, Envio parcialEnvio){
        Optional<Envio> envioOpcional = envioRepository.findById(id);
        if (envioOpcional.isPresent()) {
            
            Envio envioActualizar = envioOpcional.get();
            
            if (parcialEnvio.getNombre_envio() != null) {
                envioActualizar.setNombre_envio(parcialEnvio.getNombre_envio());
            }
            return envioRepository.save(envioActualizar);
        } else {
            return null;
        }
    }
}
