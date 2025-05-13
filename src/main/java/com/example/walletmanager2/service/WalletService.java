package com.example.walletmanager2.service;

import com.example.walletmanager2.dto.WalletOperationRequest;
import com.example.walletmanager2.entity.Wallet;
import com.example.walletmanager2.enums.OperationType;
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

        //преобразование в нужный формат
        UUID walletId = UUID.fromString(walletOperationRequest.getWalletId());
        OperationType operationType = OperationType.valueOf(walletOperationRequest.getOperationType());
        BigDecimal amount = new BigDecimal(walletOperationRequest.getAmount());

        Wallet wallet = walletRepository. getWalletByUUID(walletId);

        //операции
        BigDecimal newBalance = updateWalletBalance(amount, wallet.getBalance(), operationType);

        //сохранение
        wallet.setBalance(newBalance);
        walletRepository.save(wallet);
    }


    private BigDecimal updateWalletBalance(BigDecimal amount, BigDecimal balance, OperationType operationType) {

        switch (operationType) {
            case DEPOSIT -> balance = balance.add(amount);
            case WITHDRAW -> balance = balance.subtract(amount);
        }
        return balance;
    }
}
