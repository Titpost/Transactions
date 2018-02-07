package edu.billing.controller;


import edu.billing.model.Account;
import edu.testing.controller.ApiControllerIntegrationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;

import static junit.framework.TestCase.fail;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApiControllerIntegrationTest.class})
public class AccountControllerIntegrationTest extends ControllerIntegrationTest {

    /**
     * Get All the Accounts
     */
    @Test
    public void get_all_success() {
        ResponseEntity<Account[]> response = template.getForEntity(BASE_URI, Account[].class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        validateCORSHttpHeaders(response.getHeaders());
    }

    /**
     * Get Account By ID
     */
    @Test
    public void get_by_id_success() {
        ResponseEntity<Account> response = template.getForEntity(BASE_URI + "/" + KNOWN_ID, Account.class);
        Account amount = response.getBody();
        assertThat(amount.getId(), is(KNOWN_ID));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        validateCORSHttpHeaders(response.getHeaders());
    }

    /**
     * Fail on getting non-existing account
     */
    @Test
    public void get_by_id_failure_not_found() {
        try {
            ResponseEntity<Account> response = template.getForEntity(BASE_URI + "/" + UNKNOWN_ID, Account.class);
            fail("should return 404 not found");
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
            validateCORSHttpHeaders(e.getResponseHeaders());
        }
    }

    /**
     * Create New Account
     */
    @Test
    public void create_new_account_success() {
        Account newAccount = Account.builder().id("888")
                .id("new amount" + Math.random())
                .build();
        URI location = template.postForLocation(BASE_URI, newAccount, Account.class);
        assertThat(location, notNullValue());
    }

    /**
     * Fail on creating a duplicate account
     */
    @Test
    public void create_new_account_fail_exists() {
        Account existingAccount = Account.builder().id("1")
                .id("new amount" + Math.random())
                .build();
        try {
            template.postForLocation(BASE_URI, existingAccount, Account.class);
            fail("should return 409 conflict");
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode(), is(HttpStatus.CONFLICT));
            validateCORSHttpHeaders(e.getResponseHeaders());
        }
    }

    /**
     * Update Existing Account
     */
    @Test
    public void update_account_success() {
        Account existingAccount = Account.builder().id("2")
                .id("AccountName4")
                .build();
        template.put(BASE_URI + "/" + existingAccount.getId(), existingAccount);
    }

    /**
     * Fail on updating a non-existing account
     */
    @Test
    public void update_account_fail() {
        Account existingAccount = Account.builder().id(UNKNOWN_ID)
                .id("update")
                .build();
        try {
            template.put(BASE_URI + "/" + existingAccount.getId(), existingAccount);
            fail("should return 404 not found");
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
            validateCORSHttpHeaders(e.getResponseHeaders());
        }
    }

    /**
     * Delete an existing Account
     */
    @Test
    public void delete_account_success() {
        template.delete(BASE_URI + "/" + getLastAccount().getId());
    }

    /**
     * Fail on deleting a non-existing account
     */
    @Test
    public void delete_account_fail() {
        try {
            template.delete(BASE_URI + "/" + UNKNOWN_ID);
            fail("should return 404 not found");
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
            validateCORSHttpHeaders(e.getResponseHeaders());
        }
    }

    private Account getLastAccount() {
        ResponseEntity<Account[]> response = template.getForEntity(BASE_URI, Account[].class);
        Account[] accounts = response.getBody();
        return accounts[accounts.length - 1];
    }
}
