package com.example.walletmanager2.service;

import com.example.walletmanager2.entity.Wallet;
import com.example.walletmanager2.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;
    @InjectMocks
    private WalletService walletService;

    @Test
    void createWalletTest() {

        walletService.createWallet();

        verify(walletRepository, times(1)).save(any(Wallet.class));
    }
}
