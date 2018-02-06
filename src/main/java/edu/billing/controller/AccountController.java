package edu.billing.controller;

import edu.billing.model.Account;
import edu.billing.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // =========================================== Get Person By ID =========================================

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Account> get(@PathVariable("id") String id) {
        Account account = service.getAccountByNumber(id);

        if (account == null) {
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(account, responseHeaders, HttpStatus.OK);
    }

    // =========================================== Create New Person ========================================

//    @RequestMapping(method = RequestMethod.POST)
//    public ResponseEntity<Void> create(@RequestBody Person person,
//                                       UriComponentsBuilder ucBuilder) {
//        LOG.info("creating new person: {}", person);
//
//        if (service.exists(person)) {
//            LOG.info("a person with id " + person.getId() + " already exists");
//            return new ResponseEntity<>(responseHeaders, HttpStatus.CONFLICT);
//        }
//
//        service.savePerson(person);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(ucBuilder.path("/person/{id}")
//                .buildAndExpand(person.getId())
//                .toUri());
//        return new ResponseEntity<>(headers, HttpStatus.CREATED);
//    }
//
//    // =========================================== Update Existing Person ===================================
//
//    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
//    public ResponseEntity<Person> update(@PathVariable int id,
//                                         @RequestBody Person person) {
//        LOG.info("updating person: {}", person);
//        Person currentUser = service.getPersonById(id);
//
//        if (currentUser == null) {
//            LOG.info("Person with id {} not found", id);
//            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
//        }
//
//        currentUser.setId(person.getId());
//        currentUser.setId(person.getId());
//
//        service.updateName(1, person.getId());
//        return new ResponseEntity<>(currentUser, responseHeaders, HttpStatus.OK);
//    }
//
//    // =========================================== Delete Person ============================================
//
//    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
//    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
//        LOG.info("deleting person with id: {}", id);
//        Person person = service.getPersonById(id);
//
//        if (person == null) {
//            LOG.info("Unable to delete. Person with id {} not found", id);
//            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
//        }
//
//        service.deletePerson(id);
//        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
//    }
}
