package org.junit.account;

public class Account {
    private boolean active;
    private String email;
    private Address defaultAddress;

    public Account(Address defaultAddress) {
        if (defaultAddress != null) {
            activate();
        } else {
            this.active = false;
        }
        this.defaultAddress = defaultAddress;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Address getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(Address defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public Account() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    public boolean isActive() {
        return active;
    }

    public void setEmail(String email) {
        if (email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("WrongEmail Format");
        }
    }


    public String getEmail() {
        return email;
    }
}
