package com.aninfo.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cbu;

    private Double balance;

    @OneToMany
    private Collection<Transaction> transactionList = new HashSet<>();

    public Account(){
    }

    public Account(Double balance) {
        this.balance = balance;
    }

    public Long getCbu() {
        return cbu;
    }

    public void setCbu(Long cbu) {
        this.cbu = cbu;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void addTransaccion(Transaction transaction) {
        if (transaction != null){
            transactionList.add(transaction);
        }
    }

    public void removeTransaccion(Long id) {
        transactionList.removeIf(t->t.getId().equals(id));
    }

    public Collection<Transaction> getTransaccions() {
        return transactionList;
    }

}
