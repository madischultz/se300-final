package com.se300.ledger.model.complete;


import com.se300.ledger.TestSmartStoreApplication;
import com.se300.ledger.repository.TransactionRepository;
import com.se300.ledger.service.LedgerException;
import org.springframework.boot.test.context.SpringBootTest;
import com.se300.ledger.model.Account;
import com.se300.ledger.model.Block;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.se300.ledger.model.*;
import com.se300.ledger.service.Ledger;
import com.se300.ledger.service.LedgerException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {TestSmartStoreApplication.class})
public class CompleteTest {

    Transaction transaction = new Transaction(null, null, null, null, null, null);
    Account mary;
    Account sergey;



    @Test
    public void testSetTransactionId() {
        transaction.setTransactionId("00005");
        assertEquals("00005", transaction.getTransactionId());
    }

    @Test
    public void testSetAmount() {
        transaction.setAmount(975);
        assertEquals(975, transaction.getAmount());
    }

    @Test
    public void testSetFee() {
        transaction.setFee(10);
        assertEquals(10, transaction.getFee());
    }

    @Test
    public void testSetNote() {
        transaction.setNote("payment");
        assertEquals("payment", transaction.getNote());
    }

    @Test
    public void testSetPayer() {
        transaction.setPayer(mary);
        assertEquals(mary, transaction.getPayer());
    }

    @Test
    public void testSetReceiver() {
        transaction.setReceiver(sergey);
        assertEquals(sergey, transaction.getReceiver());
    }

    Account account = new Account("address5678",300);

    @Test
    public void testSetAddress() {
        account.setAddress("new5678");
        assertEquals("new5678", account.getAddress());
    }

    Block block = new Block(0, "previousHash");

    @Test
    public void testSetBlockNumber(){
        block.setBlockNumber(1);
        assertEquals(1, block.getBlockNumber());
    }

    @Test
    public void testSetPreviousHash(){
        block.setPreviousHash("new previousHash");
        assertEquals("new previousHash", block.getPreviousHash());
    }

    LedgerException exception = new LedgerException("AddAccount","Account Exists");

    @Test
    public void testGetAction(){
        String action = exception.getAction();
        assertEquals("AddAccount", action, "Expected Action to be 'AddAccount'");
    }

    @Test
    public void testSetAction(){
        exception.setAction("RemoveAccount");
        String updatedAction = exception.getAction();
        assertEquals("RemoveAccount", updatedAction, "Expected Action to be 'RemoveAccount'");
    }

    @Test
    public void testSetReason(){
        exception.setReason("account doesn't exist");
        String updatedReason = exception.getReason();
        assertEquals("account doesn't exist",updatedReason,"Expected reason to be 'account doesn't exist'");
    }

    // private static Ledger ledger;
    Ledger ledger = Ledger.getInstance("test", "test ledger 2023","chapman");

    @Test
    public void testNoteLengthExceedsLimit() {
        Account payer = new Account("payerAddress", 1234);
        Account receiver = new Account("receiverAddress", 1235);

        String tooLong = new String(new char[1025]).replace("\0","i");
        Transaction transaction1 = new Transaction("idtrans", 50, 10, tooLong, payer, receiver);
        assertThrows(LedgerException.class, () ->{
            ledger.processTransaction(transaction1);
        }, "Note Length Must Be Less Than 1024 Chars");
    }

    /*
     * DONE: Must do the following
     * 1. Achieve Test Coverage
     * 2. Produce Quality Report
     *
     * My CompleteTest file doesn't seem to be running, so many of these were added to AssertionsTest
     */
}
