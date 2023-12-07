package com.se300.ledger.model;

import com.se300.ledger.SmartStoreApplication;
import com.se300.ledger.TestSmartStoreApplication;
import com.se300.ledger.service.LedgerException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {SmartStoreApplication.class})
public class AccountTest {

    @Test
    void testAccountInstantiation() throws LedgerException {

        Account dummyAccount = new Account("sergey", 0);

        assertAll("Verify Account properties",
                () -> assertEquals("sergey", dummyAccount.getAddress()),
                () -> assertEquals(0, dummyAccount.getBalance()));

    }

    @Test
    void testAccountEquals() throws LedgerException {
        Account me = new Account("madi", 70);

        Transaction dummyTransaction = new Transaction("1", 10, 10, "note",me, new Account("myself", 0));
        assertFalse(me.equals(dummyTransaction));

    }

}
