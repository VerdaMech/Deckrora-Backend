package com.deckora.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deckora.model.Categoria;
import com.deckora.model.ProductosCategorias;
import com.deckora.repository.CategoriaRepository;
import com.deckora.repository.ProductosCategoriasRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoriaService {

    @Autowired
    private ProductosCategoriasRepository productosCategoriasRepository;

    @Autowired
    private ProductosCategoriasService productosCategoriasService;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findAll(){
        return categoriaRepository.findAll();
    }

    public Categoria findById(Long id){
        return categoriaRepository.findById(id).get();
    }

    public Categoria save(Categoria categoria){
        return categoriaRepository.save(categoria);
    }

    //Eliminacion x cascada
    public void delete(Long id){

        Categoria categoria = categoriaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        List<ProductosCategorias> productosCategorias = productosCategoriasRepository.findByCategoria(categoria);

        for (ProductosCategorias productosYCategorias : productosCategorias) {
        productosCategoriasService.delete(Long.valueOf(productosYCategorias.getId()));
        }

        categoriaRepository.delete(categoria);
    }
        

    public Categoria patchCategoria(Long id, Categoria parcialCategoria){
        Optional<Categoria> categoriaOpcional = categoriaRepository.findById(id);
        if (categoriaOpcional.isPresent()) {
            
            Categoria categoriaActualizar = categoriaOpcional.get();
            
            if (parcialCategoria.getDescripcion() != null) {
                categoriaActualizar.setDescripcion(parcialCategoria.getDescripcion());
            }
            return categoriaRepository.save(categoriaActualizar);
        } else {
            return null;
        }
    }
}
