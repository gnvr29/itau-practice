package com.practice.itau.model;

import com.practice.itau.dto.TransactionDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Transaction {

    @NotNull
    private String id;

    @Min(0)
    @NotNull
    private BigDecimal valor;

    @PastOrPresent
    @NotNull
    private OffsetDateTime dataHora;

    public Transaction(BigDecimal valor, OffsetDateTime dataHora){
        this.valor = valor;
        this.dataHora = dataHora;
    }

    public Transaction(TransactionDto transactionDto){
        this.valor = transactionDto.valor();
        this.dataHora = transactionDto.dataHora();
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public OffsetDateTime getDataHora(){
        return this.dataHora;
    }

    public BigDecimal getValor(){
        return this.valor;
    }
}
