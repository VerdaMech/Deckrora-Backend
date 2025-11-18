package com.deckora.repository;

import org.springframework.stereotype.Repository;

import com.deckora.model.Categoria;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
