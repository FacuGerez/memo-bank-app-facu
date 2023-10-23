package com.aninfo.service;

import com.aninfo.model.Transaction;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Optional<Transaction> serchTransactionById (Collection<Transaction> transactions, Long id){
        return transactions.stream().filter(t->t.getId().equals(id)).findFirst();
    }
    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

}
