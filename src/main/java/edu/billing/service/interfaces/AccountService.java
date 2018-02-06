package edu.billing.service.interfaces;

import edu.billing.model.Account;

import java.util.List;

public interface AccountService {

    void saveAccount(Account account);

    Long getAccountCount();

    List<Account> getAllAccounts();

    Account getAccountByNumber(String account);

    void deleteAccount(String id);
}
