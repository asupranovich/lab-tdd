package itechart.tdd.account.service;

import itechart.tdd.account.dao.AccountDao;
import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author asupranovich
 */
public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao;
    
    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
    
    @Override
    public BigDecimal transfer(String donor, String acceptor, BigDecimal amount) {
        
        if (StringUtils.isEmpty(donor)) {
            throw new IllegalArgumentException();
        }
        
        BigDecimal donorBalance = accountDao.getBalance(donor);
        if (donorBalance == null) {
            throw new AccountNotFoundException();
        }
        
        BigDecimal acceptorBalance = accountDao.getBalance(acceptor);
        if (acceptorBalance == null) {
            throw new AccountNotFoundException();
        }
        
        donorBalance = donorBalance.subtract(amount);
        accountDao.setBalance(donor, donorBalance);
        
        accountDao.setBalance(acceptor, acceptorBalance.add(amount));
        
        return donorBalance;
    }

}
