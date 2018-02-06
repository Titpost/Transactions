package edu.billing.service.implementations;

import edu.billing.dao.JdbcTemplateAccountDao;
import edu.billing.model.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceUnitTest {

    @Mock
    private JdbcTemplateAccountDao accountDao;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Before
    public void setUp() {
    }

    @Test
    public void saveAccount() throws Exception {
        Account account = Account.builder()
                .id("AccountId")
                .build();
        accountService.saveAccount(account);
        verify(accountDao, times(1)).save(account);
        verifyNoMoreInteractions(accountDao);
    }

    @Test
    public void getAccountCount() throws Exception {
        Long accountCount = 20L;
        when(accountDao.getAccountCount()).thenReturn(accountCount);
        Long resultAccountCount = accountService.getAccountCount();
        assertThat(resultAccountCount).isEqualTo(accountCount);

        verify(accountDao, times(1)).getAccountCount();
        verifyNoMoreInteractions(accountDao);
    }

    @Test
    public void deleteAccount() throws Exception {
        String id = "Id";
        accountService.deleteAccount(id);
        verify(accountDao, times(1)).delete(id);
        verifyNoMoreInteractions(accountDao);
    }
}