package edu.billing.controller;

import edu.billing.filter.CORSFilter;
import edu.billing.model.Account;
import edu.billing.service.interfaces.AccountService;
import edu.testing.listeners.AllureAttachmentListener;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.Listeners;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Listeners(AllureAttachmentListener.class)
@Features({"Allure Reports Suite"})
@Stories({"Unit tests"})
public class AccountControllerUnitBase extends ControllerUnitBase {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(accountController)
                .addFilters(new CORSFilter())
                .build();
    }

    /**
     * Get All Accounts
     * @throws Exception in MockMvc.perform
     */
    @Test
    public void get_all_success() throws Exception {
        final String id1 = "TestOne";
        final String id2 = "TestTwo";
        Account account1 = Account.builder()
                .id(id1)
                .build();
        Account account2 = Account.builder()
                .id(id2)
                .build();
        List<Account> accounts = Arrays.asList(
                account1,
                account2);

        when(accountService.getAllAccounts()).thenReturn(accounts);

        mockMvc.perform(get(URL_BASE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("TestOne")))
                .andExpect(jsonPath("$[1].id", is("TestTwo")));

        verify(accountService, times(1)).getAllAccounts();
        verifyNoMoreInteractions(accountService);
    }

    @Test
    public void cors_headers() throws Exception {
        super.test_cors_headers();
    }
}
