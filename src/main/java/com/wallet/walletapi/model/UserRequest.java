package com.wallet.walletapi.model;

import java.io.Serializable;
import java.util.Date;

public class UserRequest implements Serializable {

    Integer id;
    String name;
    Float balance;
    Date createdAt;

    public UserRequest() {
    }

    public UserRequest(String name, Float balance) {
        this.name = name;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
