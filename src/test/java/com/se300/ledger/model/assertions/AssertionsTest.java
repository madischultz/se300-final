package com.se300.ledger.model.assertions;

import com.se300.ledger.TestSmartStoreApplication;
import com.se300.ledger.model.*;
import com.se300.ledger.controller.*;
import com.se300.ledger.service.Ledger;
import com.se300.ledger.service.LedgerException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {TestSmartStoreApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AssertionsTest {

    @Autowired
    Ledger ledger;

    @Autowired
    Ledger ledgerNew3;

    static Account mary;
    static Account sergey;

    @BeforeAll
    void setUpClass() throws LedgerException {
        MockitoAnnotations.initMocks(this);

        ledger.createAccount("mary");
        ledger.createAccount("sergey");

        Account master = ledger.getUncommittedBlock().getAccount("master");
        mary = ledger.getUncommittedBlock().getAccount("mary");
        sergey = ledger.getUncommittedBlock().getAccount("sergey");

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
    }
    @Test
    void testLedgerName(){

        assertEquals("test ledger 2023", ledger.getDescription());
    }

    @Test
    void testSetDescription(){
        Ledger ledger = Ledger.getInstance("LedgerTest", "description","seed");
        ledger.setDescription("new description test");
        assertEquals("new description test", ledger.getDescription());

        ledger.setDescription("test ledger 2023");
    }

    @Test
    void testSetLedgerName() throws LedgerException{
        ledger.setName("ledger1");

        assertEquals("ledger1", ledger.getName());

    }

    @Test
    void testSeed() throws LedgerException{
        ledger.setSeed("seed test");
        assertEquals("seed test", ledger.getSeed());
        ledger.setSeed("chapman");
    }

    @Test
    void testAccountTotal() throws LedgerException {
        assertThat(ledger.getAccountBalance("mary")).isEqualTo(600);
    }

    @Test
    void testAccountTotals() throws LedgerException {
        Map<String, Integer> balances = new HashMap<>();
        balances.put("mary",600);
        balances.put("sergey",0);
        balances.put("master", 2147482947);
        assertEquals(ledger.getAccountBalances(),balances);
    }

    @Test
    void testHashCode() throws LedgerException {
        assertEquals(103668382,mary.hashCode());
    }

    @Test
    void test_AccountEquals() throws LedgerException {
        Account eq = ledger.getUncommittedBlock().getAccount("sergey");

        assertFalse(eq.equals(new Account("sergey", 1234)));
    }


    @Test
    void blockTest() throws LedgerException {
        Block block = new Block(1,"example");
        Block block2 = new Block(2, "example2");

        block.setBlockNumber(3);
        block2.setPreviousHash("test");
        block2.setPreviousBlock(block);

        assertAll("Block Tests",
                ()-> assertEquals(3, block.getBlockNumber()),
                ()-> assertEquals("example", block.getPreviousHash()),
                ()-> assertEquals("test", block2.getPreviousHash()),
                ()-> assertEquals(block, block2.getPreviousBlock()));
    }

    Transaction transaction = new Transaction(null, null, null, null, null, null);
    @Test
    void testSetTransactionId() {
        transaction.setTransactionId("00005");
        assertEquals("00005", transaction.getTransactionId());
    }

    @Test
    void testSetAmount() {
        transaction.setAmount(975);
        assertEquals(975, transaction.getAmount());
    }

    @Test
    void testSetFee() {
        transaction.setFee(10);
        assertEquals(10, transaction.getFee());
    }

    @Test
    void testSetNote() {
        transaction.setNote("payment");
        assertEquals("payment", transaction.getNote());
    }

    @Test
    void testSetPayer() {
        transaction.setPayer(mary);
        assertEquals(mary, transaction.getPayer());
    }

    @Test
    void testSetReceiver() {
        transaction.setReceiver(sergey);
        assertEquals(sergey, transaction.getReceiver());
    }

    Account account = new Account("address5678",300);

    @Test
    void testSetAddress() {
        account.setAddress("new5678");
        assertEquals("new5678", account.getAddress());
    }

    Block block = new Block(0, "previousHash");

    @Test
    void testSetBlockNumber(){
        block.setBlockNumber(1);
        assertEquals(1, block.getBlockNumber());
    }

    @Test
    void testSetPreviousHash(){
        block.setPreviousHash("new previousHash");
        assertEquals("new previousHash", block.getPreviousHash());
    }

    LedgerException exception = new LedgerException("AddAccount","Account Exists");

    @Test
    void testGetAction() throws LedgerException {
        String action = exception.getAction();
        assertEquals("AddAccount", action, "Expected Action to be 'AddAccount'");
    }

    @Test
    void testSetAction(){
        exception.setAction("RemoveAccount");
        String updatedAction = exception.getAction();
        assertEquals("RemoveAccount", updatedAction, "Expected Action to be 'RemoveAccount'");
        exception.setAction("AddAccount");
    }

    @Test
    void testSetReason(){
        exception.setReason("account doesn't exist");
        String updatedReason = exception.getReason();
        assertEquals("account doesn't exist",updatedReason,"Expected reason to be 'account doesn't exist'");
    }


    Ledger ledger2 = Ledger.getInstance("test", "test ledger 2023","chapman");

    @Test
    void testNoteLengthExceedsLimit() {
        Account payer = new Account("payerAddress", 1234);
        Account receiver = new Account("receiverAddress", 1235);

        String tooLong = new String(new char[1025]).replace("\0","i");
        Transaction transaction1 = new Transaction("idtrans", 50, 10, tooLong, payer, receiver);
        assertThrows(LedgerException.class, () ->{
            ledger2.processTransaction(transaction1);
        }, "Note Length Must Be Less Than 1024 Chars");
    }


    @InjectMocks
    private CanaryController canaryController;
    @Mock
    private Model model;
    @Test
    public void testSayHello() {

        String viewName = canaryController.sayHello(model);

        assertEquals("example", viewName);

    }

    @Test
    void testGetNumberOfBlocks() {
        Ledger ledger = Ledger.getInstance("Test Ledger", "Test Description", "Test Seed");

        int numberBlocks = ledger.getNumberOfBlocks();

        assertEquals(1, numberBlocks);
    }

    @Test
    void testValidate() throws LedgerException{
        Ledger ledger = Ledger.getInstance("Test Ledger", "Test Description", "Test Seed");

        HashMap<Integer, Block> blocks = new HashMap<>();
        blocks.put(1, block);
        blocks.put(2, block);

        Ledger.getInstance("Test Ledger", "Test Description", "Test Seed");
        ledger.validate();

    }

    @Test
    void testLedgerProcessTransaction() throws LedgerException{
        Account master = ledger.getUncommittedBlock().getAccount("master");
        Transaction eleventhTransaction =
                new Transaction("11",60,8,"simple test", master, mary);
        Transaction twelthTransaction =
                new Transaction("10",60,10,"simple test", master, mary);
        Transaction thirteenthTransaction =
                new Transaction("13",-1,10,"simple test", master, mary);
        Transaction fourteenthTransaction =
                new Transaction("14",2147483647 + 1,10,"simple test", master, mary);
        assertAll("Process Transactions Exceptions",
                ()->assertThrows(LedgerException.class, ()->ledger.processTransaction(eleventhTransaction)),
                ()->assertThrows(LedgerException.class, ()->ledger.processTransaction(twelthTransaction)),
                ()->assertThrows(LedgerException.class, ()->ledger.processTransaction(thirteenthTransaction)),
                ()->assertThrows(LedgerException.class, ()->ledger.processTransaction(fourteenthTransaction)));
    }

    @Test
    void testAccountBalances() throws LedgerException {
        Ledger ledgerNew = Ledger.getInstance("ledger new","empty", "test");
        Ledger ledgerNew2 = Ledger.getInstance("ledger new2","123", "test");

        ledgerNew2.createAccount("newTestAccount");

        Account account = new Account("test", 0);
        assertAll("exceptions for account in ledger",
                ()->assertThrows(LedgerException.class,()->ledgerNew.getAccountBalance("test")),

                ()->assertThrows(LedgerException.class,()->ledgerNew2.getAccountBalance("nonexistent")));
    }

    @Test
    void ledgerBlockTests() throws LedgerException{
        Block block = ledger.getBlock(1);
        assertAll("testing ledger get block",
                ()->assertThrows(LedgerException.class, ()->ledger.getBlock(5)),
                ()->assertEquals(block, ledger.getBlock(1)));
    }

    @Test
    void testUncommittedTransaction() throws LedgerException {
        Account master = ledger.getUncommittedBlock().getAccount("master");

        Transaction transaction
                = new Transaction("20", 50, 10, "test", master, sergey);

        ledger.processTransaction(transaction);
        assertEquals("20",(ledger.getTransaction("20")).getTransactionId());
    }

}

