package itechart.tdd.account.service;

import itechart.tdd.account.dao.AccountDao;
import java.math.BigDecimal;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author asupranovich
 */
public class AccountServiceImplTest {

    @Test
    public void testDonorBalanceReducedByTransferAmount() {
        AccountDao accountDao = EasyMock.createNiceMock(AccountDao.class);
        EasyMock.expect(accountDao.getBalance("donor")).andReturn(BigDecimal.valueOf(1000.00));
        EasyMock.expect(accountDao.getBalance("acceptor")).andReturn(BigDecimal.ZERO);
        
        EasyMock.replay(accountDao);
        
        AccountService accountService = new AccountServiceImpl(accountDao) ;
        BigDecimal donorBalance = accountService.transfer("donor", "acceptor", BigDecimal.valueOf(100.00));
        Assert.assertEquals(BigDecimal.valueOf(900.00), donorBalance);
        
        EasyMock.verify(accountDao);
    }
    
    @Test
    public void testAcceptorBalanceIncrementedByTransferAmount() {
        AccountDao accountDao = EasyMock.createNiceMock(AccountDao.class);
        EasyMock.expect(accountDao.getBalance("donor")).andReturn(BigDecimal.valueOf(1000.00));
        EasyMock.expect(accountDao.getBalance("acceptor")).andReturn(BigDecimal.ZERO);
        
        accountDao.setBalance("acceptor", BigDecimal.valueOf(100.00));
        EasyMock.expectLastCall();
        
        EasyMock.replay(accountDao);
        
        AccountService accountService = new AccountServiceImpl(accountDao) ;
        accountService.transfer("donor", "acceptor", BigDecimal.valueOf(100.00));
        
        EasyMock.verify(accountDao);
    }
    
    @Test
    public void testDonorBalanceUpdatedOnTransfer() {
        AccountDao accountDao = EasyMock.createNiceMock(AccountDao.class);
        EasyMock.expect(accountDao.getBalance("donor")).andReturn(BigDecimal.valueOf(1000.00));
        
        accountDao.setBalance("donor", BigDecimal.valueOf(900.00));
        EasyMock.expectLastCall();
        
        EasyMock.expect(accountDao.getBalance("acceptor")).andReturn(BigDecimal.ZERO);
        
        EasyMock.replay(accountDao);
        
        AccountService accountService = new AccountServiceImpl(accountDao) ;
        accountService.transfer("donor", "acceptor", BigDecimal.valueOf(100.00));
        
        EasyMock.verify(accountDao);
    }
    
    @Test (expected = AccountNotFoundException.class)
    public void shouldThrowExceptionWhenDonorIsNotFound() {
        AccountDao accountDao = EasyMock.createMock(AccountDao.class);
        EasyMock.expect(accountDao.getBalance("donor")).andReturn(null);
        
        EasyMock.replay(accountDao);
        
        AccountService accountService = new AccountServiceImpl(accountDao) ;
        accountService.transfer("donor", "acceptor", BigDecimal.valueOf(100.00));
        
        EasyMock.verify(accountDao);        
    }
    
    @Test (expected = AccountNotFoundException.class)
    public void shouldThrowExceptionWhenAcceptorIsNotFound() {
        AccountDao accountDao = EasyMock.createMock(AccountDao.class);
        EasyMock.expect(accountDao.getBalance("donor")).andReturn(BigDecimal.ONE);
        EasyMock.expect(accountDao.getBalance("acceptor")).andReturn(null);
        
        EasyMock.replay(accountDao);
        
        AccountService accountService = new AccountServiceImpl(accountDao) ;
        accountService.transfer("donor", "acceptor", BigDecimal.valueOf(100.00));
        
        EasyMock.verify(accountDao);        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgExceptionWhenParamsInvalid() {
        AccountDao accountDao = EasyMock.createMock(AccountDao.class);
        EasyMock.replay(accountDao);
        
        AccountService accountService = new AccountServiceImpl(accountDao) ;
        accountService.transfer(null, "acceptor", BigDecimal.valueOf(100.00));
        
        EasyMock.verify(accountDao); 
    }
    
}
