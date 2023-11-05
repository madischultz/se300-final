package com.se300.ledger.repository;

import com.se300.ledger.model.Store;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends CrudRepository<Store, Long>{
}
