package com.example.walletmanager2.exception;

import java.util.UUID;

public class WalletNotFoundException extends RuntimeException {

    public WalletNotFoundException(UUID walletId) {
        super("Кошелек с идентификатором '" + walletId + "' не найден.");
    }
}
