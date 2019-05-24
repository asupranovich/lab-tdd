package itechart.tdd.account.service.impl;

import itechart.tdd.account.dao.AccountDao;
import itechart.tdd.account.service.AccountService;
import java.math.BigDecimal;

/**
 *
 * @author asupranovich
 */
public class AccountServiceImpl4 implements AccountService {

    private final AccountDao accountDao;

    public AccountServiceImpl4(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
    
    @Override
    public BigDecimal transfer(String donor, String acceptor, BigDecimal amount) {
        
        BigDecimal donorBalance = accountDao.getBalance(donor);
        donorBalance = donorBalance.subtract(amount);
        
        BigDecimal acceptorBalance = accountDao.getBalance(acceptor);
        acceptorBalance = acceptorBalance.add(amount);
        
        accountDao.setBalance(donor, donorBalance);
        accountDao.setBalance(acceptor, acceptorBalance);
        
        return donorBalance;
    }

}
