package com.se300.ledger.repository;

import com.se300.ledger.SmartStoreApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import com.se300.ledger.model.Account;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SpringBootTest(classes = {SmartStoreApplication.class})
public class FakeAccountRepository implements AccountRepository {

    private final Map<String, Account> db = new HashMap<>();
    @Override
    public <S extends Account> S save(S entity) {
        this.db.put(entity.getAddress(), entity);
        return entity;
    }

    @Override
    public <S extends Account> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Account> findById(String s) {
        return Optional.ofNullable(this.db.get(s));
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<Account> findAll() {
        return null;
    }

    @Override
    public Iterable<Account> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Account entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Account> entities) {

    }

    @Override
    public void deleteAll() {

    }


    //DONE: Implement Fake Account Repository
}
