package com.example.walletmanager2.exception;

import java.util.UUID;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(UUID walletId) {
        super("Недостаточно средств на кошельке '" + walletId + "' для операции.");
    }
}
