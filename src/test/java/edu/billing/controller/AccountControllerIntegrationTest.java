package edu.billing.controller;


import edu.billing.controller.base.ControllerIntegrationBase;
import edu.billing.model.Account;
import edu.testing.config.ApiControllerIntegrationConfig;
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
@ContextConfiguration(classes = {ApiControllerIntegrationConfig.class})
public class AccountControllerIntegrationTest extends ControllerIntegrationBase {

    /**
     * Get all the accounts
     */
    @Test
    public void get_all_success() {
        ResponseEntity<Account[]> response = template.getForEntity(BASE_URI, Account[].class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        validateCORSHttpHeaders(response.getHeaders());
    }

    /**
     * Get account by id
     */
    @Test
    public void get_by_id_success() {
        ResponseEntity<Account> response = template.getForEntity(BASE_URI + '/' + KNOWN_ID, Account.class);
        assertThat(response.getBody().getId(), is(KNOWN_ID));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        validateCORSHttpHeaders(response.getHeaders());
    }

    /**
     * Fail on getting non-existing account
     */
    @Test
    public void get_by_id_failure_not_found() {
        try {
            template.getForEntity(BASE_URI + '/' + UNKNOWN_ID, Account.class);
            fail("should return 404 not found");
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
            validateCORSHttpHeaders(e.getResponseHeaders());
        }
    }

    /**
     * Create new account
     */
    @Test
    public void create_new_account_then_delete_success() {
        Account newAccount = Account.builder().id(NEW_ID)
                .amount(1)
                .build();
        URI location = template.postForLocation(BASE_URI, newAccount, Account.class);
        assertThat(location, notNullValue());
        template.delete(BASE_URI + '/' + newAccount.getId());
        try {
            template.getForEntity(BASE_URI + '/' + NEW_ID, Account.class);
            fail("should return 404 not found");
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
        }
    }

    /**
     * Fail on creating a duplicate account
     */
    @Test
    public void create_new_account_fail_exists() {
        Account existingAccount = Account.builder().id(KNOWN_ID)
                .amount(1)
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
     * Update existing account
     */
    @Test
    public void update_account_success() {
        final long amount = 777;
        Account existingAccount = Account.builder().id(KNOWN_ID)
                .amount(amount)
                .build();
        template.put(BASE_URI + '/' + existingAccount.getId(), existingAccount);
        assertThat(
                template.getForEntity(BASE_URI + '/' + KNOWN_ID, Account.class).getBody().getAmount(),
                is(amount)
        );
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
            template.put(BASE_URI + '/' + existingAccount.getId(), existingAccount);
            fail("should return 404 not found");
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
            validateCORSHttpHeaders(e.getResponseHeaders());
        }
    }

    /**
     * Fail on deleting a non-existing account
     */
    @Test
    public void delete_account_fail() {
        try {
            template.delete(BASE_URI + '/' + UNKNOWN_ID);
            fail("should return 404 not found");
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
            validateCORSHttpHeaders(e.getResponseHeaders());
        }
    }
}
