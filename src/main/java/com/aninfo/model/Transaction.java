package com.aninfo.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double amount;

    private LocalDateTime where;

    @ManyToOne
    private Account account;

    public Transaction(Double amount, Account account) {
        this.amount = amount;
        this.account = account;
        where = LocalDateTime.now();
    }
    public Transaction(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getWhere() {
        return where;
    }

    public void setWhere(LocalDateTime where) {
        this.where = where;
    }

}
