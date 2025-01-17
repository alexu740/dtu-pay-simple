package com.rest.start.Model;

import java.util.List;

public class Customer {
    private String firstName;
    private String lastName;
    private String cpr;
    private String bankAccount;
    private List<String> tokens;

    public Customer(String firstName, String lastName, String cpr, String bankAccount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpr = cpr;
        this.bankAccount = bankAccount;
    }
 
    public String getFirstName() {
        return this.firstName;
    }
    
    public String getLastName() {
        return this.lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }
        
    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }
}