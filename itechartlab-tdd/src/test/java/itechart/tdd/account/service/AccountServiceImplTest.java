package itechart.tdd.account.service;

import itechart.tdd.account.dao.AccountDao;
import itechart.tdd.account.dao.AccountDaoImpl;
import itechart.tdd.account.service.impl.AccountServiceImpl2;
import itechart.tdd.account.service.impl.AccountServiceImpl3;
import itechart.tdd.account.service.impl.AccountServiceImpl4;
import itechart.tdd.account.service.impl.AccountServiceImpl5;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author asupranovich
 */
public class AccountServiceImplTest {

    @Test
    public void testDonorBalanceIsReducedByTransferAmount() {
        
        AccountDao accountDao = EasyMock.createNiceMock(AccountDao.class);
        EasyMock.expect(accountDao.getBalance("donor")).andReturn(BigDecimal.valueOf(1000.00));
        
        EasyMock.replay(accountDao);
        
        AccountService accountService = new AccountServiceImpl3(accountDao);
        BigDecimal donorBalance = accountService.transfer("donor", "acceptor", BigDecimal.valueOf(100.00));
        
        Assert.assertEquals("Donor balance is invalid", BigDecimal.valueOf(900.00), donorBalance);
        
        EasyMock.verify(accountDao);
    }
    
    @Test
    public void testUpdatedDonorBalanceIsPersisted() {
        
        AccountDao accountDao = EasyMock.createNiceMock(AccountDao.class);
        
        EasyMock.expect(accountDao.getBalance("donor")).andReturn(BigDecimal.valueOf(1000.00));
        
        accountDao.setBalance("donor", BigDecimal.valueOf(900.00));
        EasyMock.expectLastCall();
        
        EasyMock.replay(accountDao);
        
        AccountService accountService = new AccountServiceImpl3(accountDao);
        accountService.transfer("donor", "acceptor", BigDecimal.valueOf(100.00));
        
        EasyMock.verify(accountDao);
    }
    
    @Test
    public void testUpdatedAcceptorBalanceIsPersisted() {
        
        AccountDao accountDao = EasyMock.createMock(AccountDao.class);
        
        EasyMock.expect(accountDao.getBalance("donor")).andReturn(BigDecimal.valueOf(1000.00));
        EasyMock.expect(accountDao.getBalance("acceptor")).andReturn(BigDecimal.ZERO);
        
        accountDao.setBalance("donor", BigDecimal.valueOf(900.00));
        EasyMock.expectLastCall();
        
        accountDao.setBalance("acceptor", BigDecimal.valueOf(100.00));
        EasyMock.expectLastCall();
        
        EasyMock.replay(accountDao);
        
        AccountService accountService = new AccountServiceImpl4(accountDao);
        accountService.transfer("donor", "acceptor", BigDecimal.valueOf(100.00));
        
        EasyMock.verify(accountDao);
    }
    
    @Test(expected = AccountNotFoundException.class)
    public void shouldThrowExceptionWhenNoDonorAccountFound() {
        
        AccountDao accountDao = EasyMock.createMock(AccountDao.class);
        
        EasyMock.expect(accountDao.getBalance("donor")).andReturn(null);
        
        EasyMock.replay(accountDao);
        
        AccountService accountService = new AccountServiceImpl5(accountDao);
        accountService.transfer("donor", "acceptor", BigDecimal.valueOf(100.00));
        
        EasyMock.verify(accountDao);        
    }
    
}
