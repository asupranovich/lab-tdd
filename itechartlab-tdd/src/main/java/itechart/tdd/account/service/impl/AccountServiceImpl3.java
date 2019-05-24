package itechart.tdd.account.service.impl;

import itechart.tdd.account.dao.AccountDao;
import itechart.tdd.account.service.AccountService;
import java.math.BigDecimal;

/**
 *
 * @author asupranovich
 */
public class AccountServiceImpl3 implements AccountService {

    private final AccountDao accountDao;

    public AccountServiceImpl3(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
    
    @Override
    public BigDecimal transfer(String donor, String acceptor, BigDecimal amount) {
        BigDecimal donorBalance = accountDao.getBalance(donor);
        donorBalance = donorBalance.subtract(amount);
        
        accountDao.setBalance(donor, donorBalance);
        
        return donorBalance;
    }

}
