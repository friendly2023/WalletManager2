package com.example.walletmanager2.dto;

import jakarta.validation.constraints.NotBlank;

public class WalletOperationRequest {
    @NotBlank(message = "walletId пуст")
    private String walletId;
    @NotBlank(message = "operationType пуст")
    private String operationType;
    @NotBlank(message = "amount пуст")
    private String amount;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
