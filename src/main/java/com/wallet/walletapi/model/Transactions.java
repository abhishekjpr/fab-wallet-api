package com.wallet.walletapi.model;

import java.util.Date;

public class Transactions {

    Integer id;
    Date date;
    String comments;

    public Transactions() {
    }

    public Transactions(String comments) {
        this.comments = comments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
