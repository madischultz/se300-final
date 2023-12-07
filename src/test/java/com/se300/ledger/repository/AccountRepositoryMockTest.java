package com.se300.ledger.repository;

import com.se300.ledger.SmartStoreApplication;
import com.se300.ledger.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {SmartStoreApplication.class})
public class AccountRepositoryMockTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testMockedAccountRepository(){
        Account mockDummyAcc = new Account ("mockRepoDummy", 0);

        accountRepository.save(mockDummyAcc);

        assertEquals (mockDummyAcc, accountRepository.findById("mockRepoDummy").get());
    }

    //DONE: Implement Account Repository Mock Test
}
