package edu.billing.controller;

import edu.billing.config.Settings;
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
@RequestMapping(Settings.CONTROLLER_API)
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
        final List<Account> accounts = service.getAllAccounts();

        if (null == accounts || accounts.isEmpty()) {
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
        LOG.info("getting existing account: {}", id);
        final Account account = service.getAccountByNumber(id);

        if (null == account) {
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

        final HttpHeaders headers = new HttpHeaders();
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
        final Account currentAccount = service.getAccountByNumber(id);

        if (null == currentAccount) {
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
        final Account account = service.getAccountByNumber(id);

        if (null == account) {
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        }

        service.deleteAccount(id);
        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
    }

    /**
     * Make a transfer from one account to another
     *
     * @param from source account id
     * @param to recipient account id
     * @return entity with HTTP headers only
     */
    @RequestMapping(value = "transact", method = RequestMethod.GET)
    public ResponseEntity<List<Account>> transact(@RequestParam("from") String from,
                                                  @RequestParam("to") String to,
                                                  @RequestParam("amount") long amount) {
        LOG.info("transacting {}: from {} to {}", amount, from, to);
        final Account accountFrom = service.getAccountByNumber(from);
        final Account accountTo = service.getAccountByNumber(to);

        if (null == accountFrom || null == accountTo ) {
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        }

        if (!service.transactAmount(accountFrom, accountTo, amount)) {
            return new ResponseEntity<>(responseHeaders, HttpStatus.PRECONDITION_FAILED);
        }

        return getAll();
    }
}
