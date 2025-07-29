package org.junit.account;

import java.util.Arrays;
import java.util.List;

public class AccountRepositoryStub implements AccountRepository {
    @Override
    public List<Account> getAllAccounts() {
        Account account = new Account(new Address("Krakowska", "12"));
        Account account2 = new Account(new Address("Kwiatowa", "33/5"));
        Account account3 = new Account();


        return Arrays.asList(account, account2, account3);
    }

    @Override
    public List<String> getByName(String name) {
        return null;
    }
}
