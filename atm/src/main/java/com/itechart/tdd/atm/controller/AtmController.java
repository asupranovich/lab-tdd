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
