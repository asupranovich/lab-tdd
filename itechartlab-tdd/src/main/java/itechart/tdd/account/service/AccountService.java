package itechart.tdd.account.service;

import java.math.BigDecimal;

/**
 *
 * @author asupranovich
 */
public interface AccountService {

    /**
     * Transfers amount from one account to another. 
     * @Throws {@link AccountNotFoundException} when donor or acceptor account is not found.
     * 
     * @param donor - from account
     * @param acceptor - to account
     * @param amount - amount to transfer
     * @return remaining balance of donor account 
     */
    BigDecimal transfer(String donor, String acceptor, BigDecimal amount);
    
}
