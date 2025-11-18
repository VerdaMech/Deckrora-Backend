package com.deckora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deckora.model.Pago;


@Repository
public interface PagoRepository extends JpaRepository<Pago, Long>{

}
