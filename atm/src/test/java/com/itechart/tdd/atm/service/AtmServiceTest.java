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
