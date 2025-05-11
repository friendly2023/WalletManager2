package com.example.walletmanager2.service;

import com.example.walletmanager2.entity.Wallet;
import com.example.walletmanager2.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public void createWallet() {
        walletRepository.save(new Wallet());
    }
}
