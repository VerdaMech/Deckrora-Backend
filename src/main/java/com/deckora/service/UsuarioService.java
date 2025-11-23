package com.deckora.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deckora.model.Usuario;
import com.deckora.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).get();
    }

    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public void delete(Long id){
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> buscarUsuariosPorLetra(String letra){
        return usuarioRepository.buscarUsuariosPorLetraInicial(letra);
    }

    //Métodos nuevos con 2 parametros
    public List<Usuario> buscarPorNombreYTipo(String nombre, Integer idTipoUsuario){
        return usuarioRepository.findByNombreAndTipoUsuarioId(nombre, idTipoUsuario);
    }

    //Este también es nuevo :D
    public List<Usuario> buscarPorNombreYApellido(String nombre, String Apellido){
        return usuarioRepository.findByNombreAndApellido(nombre, Apellido);
    }
    
    public Usuario patchUsuario(Long id, Usuario parcialUsuario){
        Optional<Usuario> usuarioOpcional = usuarioRepository.findById(id);
        if(usuarioOpcional.isPresent()){

            Usuario usuarioActualizar = usuarioOpcional.get();


            if(parcialUsuario.getNombre() != null){
                usuarioActualizar.setNombre(parcialUsuario.getNombre());
            }

            if(parcialUsuario.getContrasenia() != null){
                usuarioActualizar.setContrasenia(parcialUsuario.getContrasenia());
            }

            if(parcialUsuario.getApellido() != null){
                usuarioActualizar.setApellido(parcialUsuario.getApellido());
            }

            if(parcialUsuario.getCorreo() != null){
                usuarioActualizar.setCorreo(parcialUsuario.getCorreo());
            }

            if(parcialUsuario.getDireccion() != null){
                usuarioActualizar.setDireccion(parcialUsuario.getDireccion());
            }

            if(parcialUsuario.getNumero_telefono() != null){
                usuarioActualizar.setNumero_telefono(parcialUsuario.getNumero_telefono());
            }
            if(parcialUsuario.getTipoUsuario() != null){
                usuarioActualizar.setTipoUsuario(parcialUsuario.getTipoUsuario());
            }
            return usuarioRepository.save(usuarioActualizar);
        }else{
            return null;
        }
    }

    // Metodo para login
    public Usuario login(String correo, String contrasenia) {
    return usuarioRepository.findByCorreoAndContrasenia(correo, contrasenia)
            .orElse(null);
    }

}
