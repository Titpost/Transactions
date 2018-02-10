package edu.billing.controller;

import edu.billing.model.Account;
import edu.billing.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController extends Controller {

    @Autowired
    private AccountService service;

    /**
     * Get All Accounts
     *
     * @return list of accounts
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Account>> getAll() {
        List<Account> accounts = service.getAllAccounts();

        if (accounts == null || accounts.isEmpty()) {
            return new ResponseEntity<>(responseHeaders, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);
    }


    /**
     * Get Account By ID
     *
     * @param id account number
     * @return account details
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Account> get(@PathVariable("id") String id) {
        Account account = service.getAccountByNumber(id);

        if (account == null) {
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(account, responseHeaders, HttpStatus.OK);
    }


    /**
     * Create New Account
     *
     * @param account details
     * @param ucBuilder URI details
     * @return entity with HTTP headers only
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody Account account,
                                       UriComponentsBuilder ucBuilder) {
        if (service.exists(account)) {
            return new ResponseEntity<>(responseHeaders, HttpStatus.CONFLICT);
        }

        service.saveAccount(account);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/account/{id}")
                .buildAndExpand(account.getId())
                .toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Update Existing Account
     *
     * @param id account number
     * @param account account details
     * @return updated account
     */
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Account> update(@PathVariable String id,
                                          @RequestBody Account account) {
        Account currentAccount = service.getAccountByNumber(id);

        if (currentAccount == null) {
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        }

        service.updateAmount(account.getId(), account.getAmount());
        return new ResponseEntity<>(currentAccount, responseHeaders, HttpStatus.OK);
    }

    /**
     * Delete Account
     *
     * @param id account number
     * @return entity with HTTP headers only
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        Account account = service.getAccountByNumber(id);

        if (account == null) {
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        }

        service.deleteAccount(id);
        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
    }
}
