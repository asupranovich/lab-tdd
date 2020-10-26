## Controller ##
```java
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
```

## Integration ##
```java
package com.itechart.tdd.atm.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@Disabled
@Tag("integration")
@SpringBootTest
@AutoConfigureTestDatabase
public class AtmServiceIntegrationTest {

  @Autowired private AtmService atmService;

  @Test
  void shouldThrowExceptionIfAccountNotExists() {
    Assertions.assertThrows(
        AccountNotFoundException.class,
        () -> {
          atmService.getBalance(1);
        });
  }

  @ParameterizedTest
  @MethodSource("getAccountBalances")
  void shouldReturnBalanceIfAccountExists(Integer accountId, Integer expectedBalance) {
    Integer balance = atmService.getBalance(accountId);
    Assertions.assertEquals(expectedBalance, balance);
  }

  static Stream<Arguments> getAccountBalances() {
    return Stream.of(Arguments.of(10, 100), Arguments.of(20, 200), Arguments.of(30, 300));
  }
}

```

## Unit ##
```java
package com.itechart.tdd.atm.service;

import com.itechart.tdd.atm.dao.AccountRepository;
import com.itechart.tdd.atm.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class AtmServiceTest {

  @Mock
  private AccountRepository accountRepository;

  @InjectMocks
  private AtmService atmService;

  @Test
  void shouldReturnBalanceIfAccountExists() {
    Account account = new Account();
    account.setBalance(100);
    Mockito.when(accountRepository.findById(1)).thenReturn(Optional.of(account));

    Integer balance = atmService.getBalance(1);
    Assertions.assertEquals(100, balance);

    Mockito.verify(accountRepository, Mockito.times(1)).findById(1);
  }

  @Test
  void shouldThrowExceptionIfAccountIdNull() {
    AccountNotFoundException exception = Assertions.assertThrows(AccountNotFoundException.class, () -> atmService.getBalance(null));
    Assertions.assertEquals("Account with id null doesn't exist", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionIfAccountNotFound() {
    Mockito.when(accountRepository.findById(1)).thenReturn(Optional.empty());
    AccountNotFoundException exception = Assertions.assertThrows(AccountNotFoundException.class, () -> atmService.getBalance(1));
    Assertions.assertEquals("Account with id 1 doesn't exist", exception.getMessage());
  }

}
```