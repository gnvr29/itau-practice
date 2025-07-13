package com.practice.itau.controller;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.practice.itau.dto.StatisticsDto;
import com.practice.itau.dto.TransactionDto;
import com.practice.itau.model.Transaction;
import com.practice.itau.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class TransactionController {

    private static final int STATISTICS_TIMESPAN = 60;

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping(value = "/transacao", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> createTransaction(@Valid @RequestBody TransactionDto transaction){
        Transaction newTransaction = this.transactionService.creteTransaction(transaction);

        URI transactionURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newTransaction.getId())
                .toUri();

        return ResponseEntity.created(transactionURI).build();
    }

    @GetMapping(value = "/transacao", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Transaction> fetchAllTransactions(){
        return this.transactionService.listAll();
    }

    @DeleteMapping(value = "/transacao")
    public ResponseEntity<HttpStatus> deleteTransactions(){
        this.transactionService.deleteTransactions();
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/estatistica", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatisticsDto> fetchStatistics(){
        StatisticsDto statistics = this.transactionService.generateStatistics(STATISTICS_TIMESPAN);
        return ResponseEntity.ok(statistics);
    }
}
