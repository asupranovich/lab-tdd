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
