package com.practice.itau.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransactionDto(
        @NotNull
        @Min(0)
        BigDecimal valor,
        @PastOrPresent
        @NotNull
        OffsetDateTime dataHora
) {
}
