package com.itechart.tdd.atm.service;

import com.itechart.tdd.atm.dao.AccountRepository;
import com.itechart.tdd.atm.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AtmService {

  private final AccountRepository accountRepository;

  @Transactional(readOnly = true)
  public Integer getBalance(Integer accountId) {
    return getAccount(accountId).getBalance();
  }

  @Transactional
  public Integer deposit(Integer accountId, Integer amount) {
    assertAmountIsValid(amount);
    Account account = getAccount(accountId);
    return updateBalance(account, amount);
  }

  @Transactional
  public Integer withdraw(Integer accountId, Integer amount) {
    assertAmountIsValid(amount);
    Account account = getAccount(accountId);
    if (account.getBalance() < amount) {
      throw new IllegalArgumentException("Insufficient amount on account");
    }
    return updateBalance(account, -amount);
  }

  private Integer updateBalance(Account account, Integer amount) {
    account.setBalance(account.getBalance() + amount);
    accountRepository.save(account);
    return account.getBalance();
  }

  private static void assertAmountIsValid(Integer amount) {
     if (amount == null || amount <= 0) {
       throw new IllegalArgumentException("Amount is invalid");
     }
  }

  private Account getAccount(Integer accountId) {
    Optional<Account> account =
        (accountId == null) ? Optional.empty() : accountRepository.findById(accountId);
    return account.orElseThrow(
        () -> new AccountNotFoundException("Account with id " + accountId + " doesn't exist"));
  }
}
