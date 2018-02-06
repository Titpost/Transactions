package edu.billing.dao;

import edu.billing.model.Account;

import java.util.List;

public interface AccountDao extends Dao<Account, String> {

    Account findAccountByNumber(String account);

    long getAccountCount();

    List<Account> loadAll();

    void delete(String id);
}
