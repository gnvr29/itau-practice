package com.practice.itau.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record StatisticsDto(
        @NotNull
        @Min(0)
        Integer count,
        @NotNull
        @Min(0)
        BigDecimal sum,
        @NotNull
        @Min(0)
        BigDecimal avg,
        @NotNull
        @Min(0)
        BigDecimal min,
        @NotNull
        @Min(0)
        BigDecimal max

) {
}
