package com.deckora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deckora.model.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{


    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nombre) LIKE CONCAT(:letra, '%')")
    List<Usuario> buscarUsuariosPorLetraInicial(@Param("letra") String letra);
    //Metodos nuevos :D
    List<Usuario> findByNombreAndApellido(String nombre, String apellido); 
    List<Usuario> findByNombreAndTipoUsuarioId(String nombre, Integer tipoUsuario);

    // MEtodos para el login
    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByCorreoAndContrasenia(String correo, String contrasenia);
}
