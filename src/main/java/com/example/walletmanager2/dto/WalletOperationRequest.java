package com.example.walletmanager2.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public class WalletOperationRequest {
    @NotBlank(message = "walletId не должен быть пустым")
    @Pattern(
            regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
            message = "walletId имеет невалидный формат UUID"
    )
    private String walletId;

    @NotBlank(message = "operationType не должен быть пустым")
    @Pattern(
            regexp = "DEPOSIT|WITHDRAW",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "operationType должен быть DEPOSIT или WITHDRAW"
    )
    private String operationType;

    @NotNull(message = "amount не должен быть null")
    @DecimalMin(
            value = "0.01",
            message = "amount должен быть больше 0.01"
    )
    @Digits(
            integer = 10,
            fraction = 2,
            message = "amount может иметь не более 2 знаков после запятой"
    )
    private BigDecimal amount;

    public WalletOperationRequest() {
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
