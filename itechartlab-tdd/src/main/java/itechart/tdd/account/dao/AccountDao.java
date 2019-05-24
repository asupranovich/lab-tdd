package itechart.tdd.account.dao;

import java.math.BigDecimal;

/**
 *
 * @author asupranovich
 */
public interface AccountDao {

    BigDecimal getBalance(String account);
    
    void setBalance(String account, BigDecimal balance);
    
}
