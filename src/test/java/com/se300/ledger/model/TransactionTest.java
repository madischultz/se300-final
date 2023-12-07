package com.se300.ledger.model;

import com.se300.ledger.SmartStoreApplication;
import com.se300.ledger.TestSmartStoreApplication;
import com.se300.ledger.service.LedgerException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {SmartStoreApplication.class})
public class TransactionTest {

    void setUpAccounts() throws LedgerException {
        Account dummyPayer = new Account("payer", 0);
        Account dummyPayee = new Account("payee", 0);
    }

    //Implemented Transaction Test
    @Test
    void testTransactionInstantiation() throws LedgerException{
        //DONE
        Account dummyPayer = new Account("payer", 0);
        Account dummyPayee = new Account("payee", 0);

        Transaction dummyTransaction = new Transaction("1", 50, 10, "paid",  dummyPayer, dummyPayee);

        assertAll("Verify Transaction Properties",
                ()-> assertEquals("1", dummyTransaction.getTransactionId()),
                ()-> assertEquals(50, dummyTransaction.getAmount()),
                ()-> assertEquals(10, dummyTransaction.getFee()),
                ()-> assertEquals("paid", dummyTransaction.getNote()),
                ()-> assertEquals(dummyPayer, dummyTransaction.getPayer()),
                ()-> assertEquals(dummyPayee, dummyTransaction.getReceiver()));
    }

    @Test
    void testTransactionEquals() throws LedgerException {
        Account dummyPayer = new Account("payer", 100);
        Account dummyPayee = new Account("receiver", 100);

        Transaction t = new Transaction("2", 10, 10, "test", dummyPayer, dummyPayee);

        assertAll("Testing Transaction Equals Branches",
                ()->assertFalse(t.equals(dummyPayer)),
                ()->assertTrue(t.equals(new Transaction("2", 10, 10, "test", dummyPayer, dummyPayee))),
                ()->assertFalse(t.equals(new Transaction("2", 10, 10, "test", dummyPayer, new Account("ex",123)))),
                ()->assertFalse(t.equals(new Transaction("2", 10, 10, "test", new Account("ex2",123), dummyPayee))),
                ()->assertFalse(t.equals(new Transaction("2", 10, 10, "test1", dummyPayer, dummyPayee))),
                ()->assertFalse(t.equals(new Transaction("2", 10, 11, "test", dummyPayer, dummyPayee))),
                ()->assertFalse(t.equals(new Transaction("2", 11, 10, "test", dummyPayer, dummyPayee))),
                ()->assertFalse(t.equals(new Transaction("3", 10, 10, "test", dummyPayer, dummyPayee))));
    }

    @Test
    void testHashCode() throws LedgerException {
        Transaction transaction
                = new Transaction("transaction", 50, 10, "hash test", new Account("test1", 100), new Account("test2", 100));
        assertEquals(183717454, transaction.hashCode());
    }
}
