package edu.billing.service.implementations;


import edu.billing.dao.JdbcTemplateAccountDao;
import edu.billing.model.Account;
import edu.billing.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void updateAmount(String id, long amount) {
        accountDao.updateAmount(id, amount);
    }

    @Override
    @Transactional
    public boolean transactAmount(Account from, Account to, long amount) {

        if (from.getId().equals(to.getId()) ||
            from.getAmount() < amount) {

            return false;
        }

        accountDao.updateAmount(from.getId(), from.getAmount() - amount);
        accountDao.updateAmount(to.getId(), to.getAmount() + amount);
        return true;
    }

    @Override
    public void deleteAccount(String id) {
        accountDao.delete(id);
    }
}
