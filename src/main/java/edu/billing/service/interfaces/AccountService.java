package edu.billing.service.interfaces;

import edu.billing.model.Account;

import java.util.List;

public interface AccountService {

    void saveAccount(Account account);

    Long getAccountCount();

    List<Account> getAllAccounts();

    Account getAccountByNumber(String account);

    boolean exists(Account person);

    void updateAmount(String id, long amount);

    boolean transactAmount(Account from, Account to, long amount);

    void deleteAccount(String id);
}
