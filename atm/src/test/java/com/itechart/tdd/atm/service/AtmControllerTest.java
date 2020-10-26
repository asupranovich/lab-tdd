package com.itechart.tdd.atm.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@Disabled
@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@DisplayName("ATM specification:")
public class AtmControllerTest {

  @Autowired private MockMvc mvc;

  @Nested
  @DisplayName("Balance endpoint:")
  class BalanceTest {
    @Test
    @DisplayName("Returns status 404 and error message when account doesn't exist")
    void shouldReturnErrorIfAccountNotFound() throws Exception {
      mvc.perform(get("/account/1/balance"))
          .andExpect(status().is(404))
          .andExpect(content().string("Account with id 1 doesn't exist"));
    }

    @Test
    @DisplayName("Returns status 200 and account amount when account exists")
    void shouldReturnBalanceIfAccountExists() throws Exception {
      mvc.perform(get("/account/10/balance"))
          .andExpect(status().is2xxSuccessful())
          .andExpect(content().string("100"));
    }
  }

  @Nested
  @DisplayName("Deposit endpoint:")
  class DepositTest {

    @Test
    @DisplayName("Returns status 404 and error message when account doesn't exist")
    void shouldReturnErrorIfAccountNotFound() throws Exception {
      mvc.perform(put("/account/1/deposit").param("amount", "100"))
          .andExpect(status().is(404))
          .andExpect(content().string("Account with id 1 doesn't exist"));
    }

    @Test
    @DisplayName("Returns status 400 and error message when amount is invalid")
    void shouldReturnErrorIfAmountInvalid() throws Exception {
      mvc.perform(put("/account/20/deposit").param("amount", "0"))
          .andExpect(status().is(400))
          .andExpect(content().string("Amount is invalid"));

      mvc.perform(put("/account/20/deposit").param("amount", "-100"))
          .andExpect(status().is(400))
          .andExpect(content().string("Amount is invalid"));
    }

    @Test
    @DisplayName("Returns status 200 and new balance when amount is valid")
    void shouldReturnNewBalanceIfAmountValid() throws Exception {
      mvc.perform(put("/account/30/deposit").param("amount", "100"))
          .andExpect(status().is2xxSuccessful())
          .andExpect(content().string("400"));

      mvc.perform(get("/account/30/balance"))
          .andExpect(status().is2xxSuccessful())
          .andExpect(content().string("400"));
    }

  }

  @Nested
  @DisplayName("Withdraw endpoint:")
  class WithdrawTest {

    @Test
    @DisplayName("Returns status 404 and error message when account doesn't exist")
    void shouldReturnErrorIfAccountNotFound() throws Exception {
      mvc.perform(put("/account/1/withdraw").param("amount", "100"))
          .andExpect(status().is(404))
          .andExpect(content().string("Account with id 1 doesn't exist"));
    }

    @Test
    @DisplayName("Returns status 400 and error message when amount is invalid")
    void shouldReturnErrorIfAmountInvalid() throws Exception {
      mvc.perform(put("/account/20/withdraw").param("amount", "0"))
          .andExpect(status().is(400))
          .andExpect(content().string("Amount is invalid"));

      mvc.perform(put("/account/20/withdraw").param("amount", "-100"))
          .andExpect(status().is(400))
          .andExpect(content().string("Amount is invalid"));
    }

    @Test
    @DisplayName("Returns status 400 and error message when withdraw amount > account balance")
    void shouldReturnErrorIfAmountGtBalance() throws Exception {
      mvc.perform(put("/account/20/withdraw").param("amount", "300"))
          .andExpect(status().is(400))
          .andExpect(content().string("Insufficient amount on account"));
    }

    @Test
    @DisplayName("Returns status 200 and new balance when amount is valid")
    void shouldReturnNewBalanceIfAmountValid() throws Exception {
      mvc.perform(put("/account/40/withdraw").param("amount", "100"))
          .andExpect(status().is2xxSuccessful())
          .andExpect(content().string("300"));

      mvc.perform(get("/account/40/balance"))
          .andExpect(status().is2xxSuccessful())
          .andExpect(content().string("300"));
    }
  }
}
