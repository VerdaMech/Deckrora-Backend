package com.deckora.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deckora.model.Imagen;
import com.deckora.repository.ImagenRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ImagenService {

    @Autowired
    private ImagenRepository imagenRepository;

    public List<Imagen> findAll() {
        return imagenRepository.findAll();
    }

    public Imagen findById(Long id) {
        return imagenRepository.findById(id).get();
    }

    public Imagen save(Imagen imagen) {

        return imagenRepository.save(imagen);

    }

    public void delete(Long id) {
        imagenRepository.deleteById(id);
    }

    public Imagen patchImagen(Long id, Imagen parcialImagen) {

        Optional<Imagen> imagenOpcional = imagenRepository.findById(id);
        if (imagenOpcional.isPresent()) {
            Imagen imagenActualizar = imagenOpcional.get();
            if (parcialImagen.getRuta() != null) {
                imagenActualizar.setRuta(parcialImagen.getRuta());
            }
            return imagenRepository.save(imagenActualizar);

        } else {

            return null;

        }

    }
}