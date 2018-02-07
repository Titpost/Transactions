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

    // =========================================== Get All Accounts ==========================================

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Account>> getAll() {
        List<Account> accounts = service.getAllAccounts();

        if (accounts == null || accounts.isEmpty()) {
            return new ResponseEntity<>(responseHeaders, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);
    }

    // =========================================== Get Account By ID =========================================

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Account> get(@PathVariable("id") String id) {
        Account account = service.getAccountByNumber(id);

        if (account == null) {
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(account, responseHeaders, HttpStatus.OK);
    }

    // =========================================== Create New Account ========================================

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

//    // =========================================== Update Existing Account ===================================
//
//    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
//    public ResponseEntity<Account> update(@PathVariable int id,
//                                         @RequestBody Account account) {
//        LOG.info("updating account: {}", account);
//        Account currentUser = service.getAccountById(id);
//
//        if (currentUser == null) {
//            LOG.info("Account with id {} not found", id);
//            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
//        }
//
//        currentUser.setId(account.getId());
//        currentUser.setId(account.getId());
//
//        service.updateName(1, account.getId());
//        return new ResponseEntity<>(currentUser, responseHeaders, HttpStatus.OK);
//    }
//
//    // =========================================== Delete Account ============================================
//
//    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
//    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
//        LOG.info("deleting account with id: {}", id);
//        Account account = service.getAccountById(id);
//
//        if (account == null) {
//            LOG.info("Unable to delete. Account with id {} not found", id);
//            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
//        }
//
//        service.deleteAccount(id);
//        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
//    }
}
