package com.practice.itau.service;

import com.practice.itau.dto.StatisticsDto;
import com.practice.itau.dto.TransactionDto;
import com.practice.itau.model.Transaction;
import com.practice.itau.repository.TransactionRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    public Transaction creteTransaction(@Valid TransactionDto transaction){
        Transaction newTransaction = new Transaction(transaction);
        return this.transactionRepository.save(newTransaction);
    }

    public StatisticsDto generateStatistics(@Min(0) long secondsAgo){
        List<Transaction> transactions = this.transactionRepository.listAll();

        OffsetDateTime baseTime = OffsetDateTime.now().minusSeconds(secondsAgo);

        List<Transaction> filteredTransactions = transactions.stream()
                .filter(t -> t.getDataHora().isAfter(baseTime) || t.getDataHora().isEqual(baseTime))
                .toList();

        int count = filteredTransactions.size();

        Optional<BigDecimal> maxAmount = filteredTransactions.stream()
                .map(Transaction::getValor)
                .max(BigDecimal::compareTo);
        BigDecimal max = maxAmount.orElse(BigDecimal.valueOf(0));

        Optional<BigDecimal> minAmount = filteredTransactions.stream()
                .map(Transaction::getValor)
                .min(BigDecimal::compareTo);
        BigDecimal min = minAmount.orElse(BigDecimal.valueOf(0));

        BigDecimal sum = filteredTransactions.stream()
                .map(Transaction::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal avg = BigDecimal.valueOf(0);
        if(count > 0) {
            avg = sum.divide(BigDecimal.valueOf(count), RoundingMode.HALF_DOWN);
        }

        return new StatisticsDto(count, sum, avg, min, max);
    }

    public List<Transaction> listAll(){
        return this.transactionRepository.listAll();
    }

    public void deleteTransactions(){
        this.transactionRepository.clear();
    }
}
