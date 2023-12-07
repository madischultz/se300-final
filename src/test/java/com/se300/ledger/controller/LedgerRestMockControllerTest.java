package com.se300.ledger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se300.ledger.TestSmartStoreApplication;
import com.se300.ledger.model.Account;
import com.se300.ledger.model.Transaction;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TestSmartStoreApplication.class)
@RunWith(SpringRunner.class)
public class LedgerRestMockControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAccountById() throws Exception {
        mockMvc.perform(get("/accounts/master")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.address").value("master"));
    }


    //DONE: Implement Transaction Mock Retrieval Test Method

    @Test
    public void testProcessAndGetTransactionById() throws Exception {
        MvcResult payee = mockMvc.perform(post("/accounts")
                        .content(new ObjectMapper().writeValueAsString(new Account("sergey", 0)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        MvcResult payer = mockMvc.perform(get("/accounts/master")).andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        Account sergey = new ObjectMapper().readValue(payee.getResponse().getContentAsString(), Account.class);
        Account master = new ObjectMapper().readValue(payer.getResponse().getContentAsString(), Account.class);
        MvcResult transaction = mockMvc.perform(post("/transactions")
                        .content(new ObjectMapper().writeValueAsString(
                                new Transaction("1", 60, 10, "simple test", master, sergey)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals("1", transaction.getResponse().getContentAsString());

        mockMvc.perform(get("/transactions/1")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.amount").value("60"));
    }
}
