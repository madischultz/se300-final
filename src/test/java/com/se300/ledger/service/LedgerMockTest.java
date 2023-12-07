package com.se300.ledger.service;

import com.se300.ledger.TestSmartStoreApplication;
import com.se300.ledger.model.Account;
import com.se300.ledger.model.Transaction;
import com.se300.ledger.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {TestSmartStoreApplication.class})
public class LedgerMockTest {

    @Autowired
    private Ledger ledger;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    public void testGetAllAccount() throws LedgerException {
        List<Account> list = new ArrayList<Account>();
        Account dummyPayer = new Account("payer", 0);
        Account dummyPayee = new Account("payee", 0);
        list.add(dummyPayer);
        list.add(dummyPayee);

        given(accountRepository.save(any(Account.class))).willReturn(dummyPayee);
        ledger.createAccount(dummyPayee.getAddress());
        ledger.createAccount(dummyPayer.getAddress());
        verify(accountRepository, times(1)).save(dummyPayee);

        Collection<Account> accountList = ledger.getUncommittedBlock().getAccountBalanceMap().values();

        List<Account> differences = list.stream()
                .filter(element -> !accountList.contains(element))
                .collect(Collectors.toList());
        assertEquals(0, differences.size());
    }

    @Test
    void testGetAccountBalances() throws LedgerException {

        assertNull(ledger.getAccountBalances());

    }

    @Test
    void testGetAccountBalance() throws LedgerException {
        ledger.createAccount("test");
        assertThrows(LedgerException.class, ()->ledger.getAccountBalance("test"));
    }



    //DONE: Create Another Ledger Mock Test
}
