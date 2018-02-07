package edu.billing.service.implementations;


import edu.billing.dao.JdbcTemplateAccountDao;
import edu.billing.model.Account;
import edu.billing.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired(required = false)
    private JdbcTemplateAccountDao accountDao;

    @Override
    public void saveAccount(Account account) {
        accountDao.save(account);
    }

    @Override
    public Long getAccountCount() {
        return accountDao.getAccountCount();
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountDao.loadAll();
    }

    @Override
    public Account getAccountByNumber(String account) {
        return accountDao.findAccountByNumber(account);
    }

    @Override
    public boolean exists(Account person) {
        return accountDao.exists(person);
    }

    @Override
    public void deleteAccount(String id) {
        accountDao.delete(id);
    }
}
