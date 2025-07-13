package com.practice.itau.repository;

import com.practice.itau.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TransactionRepository {

    private final Map<String, Transaction> transactions = new ConcurrentHashMap<>();

    public Transaction save(Transaction transaction){
        String id = UUID.randomUUID().toString();
        transaction.setId(id);
        this.transactions.put(id, transaction);
        return transaction;
    }

    public void clear(){
        this.transactions.clear();
    }

    public List<Transaction> listAll(){
        return this.transactions.values().stream().toList();
    }
}
