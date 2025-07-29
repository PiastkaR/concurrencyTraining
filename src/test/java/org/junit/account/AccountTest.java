package org.junit.account;

import org.junit.account.Account;
import org.junit.account.Address;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@Tag("Fries")
class AccountTest {

    @Test
    void newAccountShouldNotBeActive() {
        //given
        Account acc = new Account();
        //then
        assertFalse(acc.isActive());
        assertThat(acc.isActive(), equalTo(false));
        assertThat(acc.isActive(), is(false));
    }

    @Test
    void accountShouldBeActiveAfterActivation() {
        //given
        Account acc = new Account();
        //when
        acc.activate();
        //then
        assertTrue(acc.isActive());
    }

    @Test
    void newlyCreatedAccountShouldNotHaveDefaultDeliveryAddressSet() {
        //given
        Account acc = new Account();
        //when
        Address address = acc.getDefaultAddress();
        //then
        assertNull(address);
        assertThat(address, nullValue());
    }

    @Test
    void defaultDeliverYAddressShouldNotBeNullAfterBeingSet() {
        //given
        Address address = new Address("Krakowska", "67C");
        Account account = new Account();
        account.setDefaultAddress(address);
        //when
        Address defaultAddress = account.getDefaultAddress();
        //then
        assertNotNull(defaultAddress);
        assertThat(defaultAddress, is(notNullValue()));
    }

    @RepeatedTest(2)
    void newAccountWithNotNullAddressShouldBeActive() {
        //given
        Address address = new Address("Puławska", "6C");
        //when
        Account account = new Account(address);
        //then
        assumingThat(address != null, () -> {
            assertTrue(account.isActive());
        });
    }

    @Test
    void invalidEmailShouldThrowException(){
        //given
        Account account = new Account();
        //when
        //then
        assertThrows(IllegalArgumentException.class, ()-> account.setEmail("Wrong"));
    }

    @Test
    void validEmailShouldBeSet(){
        //given
        Account account = new Account();
        //when
        account.setEmail("kontakt@jakistamdev.pl");
        //then
        assertThat(account.getEmail(), is("kontakt@jakistamdev.pl"));
    }


}
