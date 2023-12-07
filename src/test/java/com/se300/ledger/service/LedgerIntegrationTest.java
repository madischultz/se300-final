package com.se300.ledger.service;


import com.se300.ledger.TestSmartStoreApplication;
import com.se300.ledger.model.Account;
import com.se300.ledger.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {TestSmartStoreApplication.class})
public class LedgerIntegrationTest {

    @Autowired
    private Ledger ledger;

    @Test
    public void testCompleteLedger() throws LedgerException {

        Account master = ledger.getUncommittedBlock().getAccount("master");
        Account mary = ledger.createAccount("mary");

        Transaction firstTransaction =
                new Transaction("1",60,10,"simple test", master, mary);
        Transaction secondTransaction =
                new Transaction("2",60,10,"simple test", master, mary);
        Transaction thirdTransaction =
                new Transaction("3",60,10,"simple test", master, mary);
        Transaction forthTransaction =
                new Transaction("4",60,10,"simple test", master, mary);
        Transaction fifthTransaction =
                new Transaction("5",60,10,"simple test", master, mary);
        Transaction sixTransaction =
                new Transaction("6",60,10,"simple test", master, mary);
        Transaction seventhTransaction =
                new Transaction("7",60,10,"simple test", master, mary);
        Transaction eightsTransaction =
                new Transaction("8",60,10,"simple test", master, mary);
        Transaction ninthTransaction =
                new Transaction("9",60,10,"simple test", master, mary);
        Transaction tenthTransaction =
                new Transaction("10",60,10,"simple test", master, mary);

        ledger.processTransaction(firstTransaction);
        ledger.processTransaction(secondTransaction);
        ledger.processTransaction(thirdTransaction);
        ledger.processTransaction(forthTransaction);
        ledger.processTransaction(fifthTransaction);
        ledger.processTransaction(sixTransaction);
        ledger.processTransaction(seventhTransaction);
        ledger.processTransaction(eightsTransaction);
        ledger.processTransaction(ninthTransaction);
        ledger.processTransaction(tenthTransaction);

        assertEquals(600, mary.getBalance());
    }

    //DONE: Create Another Ledger Integration Test
    @Test
    void testSecondCompleteLedger() throws LedgerException {
        Account master = ledger.getUncommittedBlock().getAccount("master");
        Account madi = ledger.createAccount("madi");

        Transaction eleventh =
                new Transaction("11",60,10,"simple test", master, madi);
        Transaction twelfth =
                new Transaction("12",60,10,"simple test", master, madi);
        Transaction thirteenth =
                new Transaction("13",60,10,"simple test", master, madi);
        Transaction fourteenth =
                new Transaction("14",60,10,"simple test", master, madi);
        Transaction fifteenth =
                new Transaction("15",60,10,"simple test", master, madi);

        Transaction sixteenth =
                new Transaction("16",60,10,"simple test", master, madi);
        Transaction seventeenth =
                new Transaction("17",60,10,"simple test", master, madi);
        Transaction eighteenth =
                new Transaction("18",60,10,"simple test", master, madi);
        Transaction nineteenth =
                new Transaction("19",60,10,"simple test", master, madi);
        Transaction twentieth =
                new Transaction("20",60,10,"simple test", master, madi);

        ledger.processTransaction(eleventh);
        ledger.processTransaction(twelfth);
        ledger.processTransaction(thirteenth);
        ledger.processTransaction(fourteenth);
        ledger.processTransaction(fifteenth);
        ledger.processTransaction(sixteenth);
        ledger.processTransaction(seventeenth);
        ledger.processTransaction(eighteenth);
        ledger.processTransaction(nineteenth);
        ledger.processTransaction(twentieth);

        assertDoesNotThrow(()->ledger.validate());
    }
}
