package com.deckora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deckora.model.EstadoOrden;

@Repository
public interface EstadoOrdenRepository extends JpaRepository<EstadoOrden, Long> {

}
