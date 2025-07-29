package org.junit.account;

import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)//lepsze logowanie bledow
class AccountServiceTest {

    @Test
    void getAllActiveAccounts() {
        //given
        List<Account> prepareAccountData = prepareAccountData();
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
//        given(accountRepository.getAllAccounts()).willReturn(prepareAccountData); inna opcja na bdd
        //when
        when(accountRepository.getAllAccounts()).thenReturn(prepareAccountData);
        List<Account> accounts = accountService.getAllActiveAccounts();
        //then
        assertThat(accounts, hasSize(2));
    }

    @Test
    void getNoActiveAccounts() {
        //given
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
        //when
        when(accountRepository.getAllAccounts()).thenReturn(List.of());
        List<Account> accounts = accountService.getAllActiveAccounts();
        //then
        assertThat(accounts, hasSize(0));
    }

    @Test
    void getASccByName() {
        //given
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
        //when
        when(accountRepository.getByName("Rafl")).thenReturn(List.of("Piast"));
        List<String> accountNames = accountService.findByName("Rafal");
        //then
        assertThat(accountNames, contains("Piast"));
    }


    private List<Account> prepareAccountData() {
        Account account = new Account(new Address("Krakowska", "12"));
        Account account2 = new Account(new Address("Kwiatowa", "33/5"));
        Account account3 = new Account();


        return Arrays.asList(account, account2, account3);
    }

}