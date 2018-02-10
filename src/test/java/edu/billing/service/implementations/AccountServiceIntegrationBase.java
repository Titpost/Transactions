package edu.billing.service.implementations;

import edu.testing.config.AccountServiceIntegrationTestConfig;
import edu.billing.controller.ControllerIntegrationTest;
import edu.billing.model.Account;
import edu.billing.service.interfaces.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Integral test for Account Service.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AccountServiceIntegrationTestConfig.class)
public class AccountServiceIntegrationBase extends ServiceIntegrationBase {

    @Autowired
    private AccountService accountService;

    /**
     * Database SetUp
     */
    @Before
    public void setUpDB() {
        assertNotNull(accountService);
        dataBase = db;
    }

    /**
     * Get accounts count
     */
    @Test
    public void getAll() {
        assertEquals(getCount(), accountService.getAllAccounts().size());
    }

    /**
     * Get account
     */
    @Test
    public void getOne() {
        final Account account = accountService.getAccountByNumber(ControllerIntegrationTest.KNOWN_ID);
        assertEquals(700000, account.getAmount());
    }

    /**
     * Create new account
     */
    @Test
    public void createNew() {

        final long prevCount = getCount();

        // create new account with hardId and some amount
        final int amount1 = 1;
        Account account = Account.builder().id(hardId).amount(amount1).build();
        accountService.saveAccount(account);

        // create new account with hardId2 and some amount
        account = Account.builder().id(hardId + "_2").amount(amount1).build();
        accountService.saveAccount(account);

        // create new account with hardId1 and some amount
        final int amount2 = 2;
        account = Account.builder().id(hardId).amount(amount2).build();
        accountService.saveAccount(account);

        // create new account with hardId2 and some amount
        account = Account.builder().id(hardId +"_2").amount(amount2).build();
        accountService.saveAccount(account);

        // check row count
        assertEquals(prevCount + 2, getCount());
    }

    /**
     * Create 2 non unique accounts
     */
    @Test
    public void createNotUniques() {

        long initialCount = getCount();

        // create new account
        Account account = Account.builder().id(hardId + "_nonUnique").amount(1).build();
        accountService.saveAccount(account);

        // must be +1
        assertEquals(initialCount + 1, getCount());

        // create new account with same KNOWN_ID and amount
        accountService.saveAccount(account);

        // must be + 1 (not + 2)
        assertEquals(initialCount + 1, getCount());
    }

    /**
     * Try to find not existing account by wrong ID (KNOWN_ID)
     */
    @Test
    public void findNotExisting() {

        // find account by its id
        Account account = accountService.getAccountByNumber(hardId);
        assertNull(account);
    }

    /**
     * Delete account
     */
    @Test
    public void deleteById() {

        // create new account
        final long amount = 777;
        final String accountId = "toDelete";
        Account account = Account.builder().id(accountId).amount(amount).build();
        accountService.saveAccount(account);
        assertNotNull(accountService.getAccountByNumber(accountId));
        final int count = accountService.getAllAccounts().size();

        // delete just created account
        accountService.deleteAccount(accountId);
        assertNull(accountService.getAccountByNumber(accountId));

        // check if table's row count decremented
        assertEquals(count - 1, accountService.getAllAccounts().size());
    }

    private long getCount() {
        return accountService.getAccountCount();
    }
}