package com.se300.ledger.repository;

import com.se300.ledger.SmartStoreApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.se300.ledger.model.Account;
import org.junit.jupiter.api.Test;
import com.se300.ledger.TestSmartStoreApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {SmartStoreApplication.class})
public class AccountRepositoryIntegrationTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testAccountRepository(){
        Account repoDummyAcc = accountRepository.save(new Account ("repoDummy", 0));

        assertEquals (repoDummyAcc, accountRepository.findById("repoDummy").get());
    }

    //DONE:Implement Account Repository Integration Test
}
