## Controller ##
```java
package com.itechart.tdd.atm.controller;

import com.itechart.tdd.atm.service.AccountNotFoundException;
import com.itechart.tdd.atm.service.AtmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AtmController {

  private final AtmService atmService;

  @GetMapping(path = "/account/{accountId}/balance")
  public Integer getBalance(@PathVariable int accountId) {
    return atmService.getBalance(accountId);
  }

  @PutMapping(path = "/account/{accountId}/deposit")
  public Integer deposit(@PathVariable int accountId, @RequestParam int amount) {
    return atmService.deposit(accountId, amount);
  }

  @PutMapping(path = "/account/{accountId}/withdraw")
  public Integer withdraw(@PathVariable int accountId, @RequestParam int amount) {
    return atmService.withdraw(accountId, amount);
  }

  @ExceptionHandler(AccountNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handleAccountNotFoundException(Throwable throwable) {
    return throwable.getMessage();
  }

  @ExceptionHandler(Throwable.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleException(Throwable throwable) {
    return throwable.getMessage();
  }

}

```

## Dao ##
```java
package com.itechart.tdd.atm.dao;

import com.itechart.tdd.atm.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
}
```

## Model ##
```java
package com.itechart.tdd.atm.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class Account implements Serializable {

  @Id
  private Integer id;
  private Integer balance;
}
```

## Service ##
```java
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
        () -> new AccountNotFoundException("Failed to get account with id " + accountId));
  }
}
```

## Exception ##
```java
package com.itechart.tdd.atm.service;

public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException(String msg) {
    super(msg);
  }

}
```

## App ##
```java
package com.itechart.tdd.atm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class AtmApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtmApplication.class, args);
	}

	@Configuration
	@EnableJpaRepositories
	@EnableWebMvc
	@EnableTransactionManagement
	public class AtmApplicationConfig {

	}

}
```