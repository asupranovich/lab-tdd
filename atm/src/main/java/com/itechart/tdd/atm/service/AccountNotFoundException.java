package com.itechart.tdd.atm.service;

public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException(String msg) {
    super(msg);
  }

}
