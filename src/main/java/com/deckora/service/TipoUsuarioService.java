package com.deckora.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deckora.model.TipoUsuario;
import com.deckora.repository.TipoUsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TipoUsuarioService {
    
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    public List<TipoUsuario> findAll(){
        return tipoUsuarioRepository.findAll();
    }

    public TipoUsuario findById(Long id){
        return tipoUsuarioRepository.findById(id).get();
    }

    public TipoUsuario save(TipoUsuario tipoUsuario){
        return tipoUsuarioRepository.save(tipoUsuario);
    }

    public void delete(Long id){
        tipoUsuarioRepository.deleteById(id);
    }

    public TipoUsuario patchTipoUsuario(Long id, TipoUsuario parcialTipoUsuario){
        Optional<TipoUsuario> tipoUsuarioOpcional = tipoUsuarioRepository.findById(id);
        if (tipoUsuarioOpcional.isPresent()) {
            
            TipoUsuario tipoUsuarioActualizar = tipoUsuarioOpcional.get();
            
            if (parcialTipoUsuario.getDescripcion() != null) {
                tipoUsuarioActualizar.setDescripcion(parcialTipoUsuario.getDescripcion());
            }
            return tipoUsuarioRepository.save(tipoUsuarioActualizar);
        } else {
            return null;
        }
    }
}
