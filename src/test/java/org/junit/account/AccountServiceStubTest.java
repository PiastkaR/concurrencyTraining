package org.junit.account;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

class AccountServiceStubTest {

    @Test
    void getAllActiveAccounts() {
        //given
        AccountService accountService = new AccountService(new AccountRepositoryStub());
        //when
        List<Account> accounts = accountService.getAllActiveAccounts();
        //then
        assertThat(accounts, hasSize(2));
    }

}