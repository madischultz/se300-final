package com.se300.ledger.repository;

import com.se300.ledger.model.Basket;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends CrudRepository<Basket, Long> {
}
