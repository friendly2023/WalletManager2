package com.example.walletmanager2.service;

import com.example.walletmanager2.dto.WalletOperationRequest;
import com.example.walletmanager2.entity.Wallet;
import com.example.walletmanager2.enums.OperationType;
import com.example.walletmanager2.exception.InsufficientFundsException;
import com.example.walletmanager2.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletService {

    private WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public void createWallet() {
        walletRepository.save(new Wallet());
    }

    public void updateWallet(WalletOperationRequest walletOperationRequest) {

        UUID walletId = UUID.fromString(walletOperationRequest.getWalletId());
        OperationType operationType = OperationType.valueOf(walletOperationRequest.getOperationType());
        BigDecimal amount = walletOperationRequest.getAmount();

        Wallet wallet = walletRepository.getWalletByUUID(walletId);

        BigDecimal newBalance = updateWalletBalance(amount, wallet, operationType);

        wallet.setBalance(newBalance);
        walletRepository.save(wallet);
    }

    private BigDecimal updateWalletBalance(BigDecimal amount, Wallet wallet, OperationType operationType) {
        BigDecimal balance = wallet.getBalance();

        if (operationType == OperationType.DEPOSIT) {
            return balance.add(amount);
        } else {
            if (balance.subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
                throw new InsufficientFundsException(wallet.getId());
            }
            return balance.subtract(amount);
        }
    }
}
