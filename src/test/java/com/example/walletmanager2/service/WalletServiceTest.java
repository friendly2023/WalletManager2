package com.example.walletmanager2.service;

import com.example.walletmanager2.dto.WalletOperationRequest;
import com.example.walletmanager2.entity.Wallet;
import com.example.walletmanager2.exception.InsufficientFundsException;
import com.example.walletmanager2.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static com.example.walletmanager2.enums.OperationType.WITHDRAW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    void updateWalletDEPOSIT_thenNoViolations() {
        WalletOperationRequest request = new WalletOperationRequest(
                "f54e3b9f-1262-4a2f-9236-53d74da89344",
                "DEPOSIT",
                new BigDecimal("10.00")
        );
        UUID walletId = UUID.fromString(request.walletId());

        Wallet wallet1 = createWallet();

        Wallet wallet2 = createWallet();
        wallet2.setBalance(BigDecimal.valueOf(10.00));

        when(walletRepository.getWalletByUUID(walletId)).thenReturn(wallet1);

        walletService.updateWallet(request);

        assertEquals(0, wallet1.getBalance().compareTo(wallet2.getBalance()));
    }

    @Test
    void getWalletByUUIDTest() {
        WalletOperationRequest request = new WalletOperationRequest(
                "f54e3b9f-1262-4a2f-9236-53d74da89344",
                "DEPOSIT",
                new BigDecimal("10.00")
        );
        String walletId = request.walletId();

        walletService.getWalletByUUID(walletId);

        verify(walletRepository, times(1)).getWalletByUUID(UUID.fromString(walletId));
    }

    @Test
    void updateWalletWITHDRAW_thenNoViolations() {
        WalletOperationRequest request = new WalletOperationRequest(
                "f54e3b9f-1262-4a2f-9236-53d74da89344",
                String.valueOf(WITHDRAW),
                new BigDecimal("10.00")
        );
        UUID walletId = UUID.fromString(request.walletId());

        Wallet wallet1 = createWallet();
        wallet1.setBalance(BigDecimal.valueOf(100));

        Wallet wallet2 = createWallet();
        wallet2.setBalance(BigDecimal.valueOf(90));

        when(walletRepository.getWalletByUUID(walletId)).thenReturn(wallet1);

        walletService.updateWallet(request);

        assertEquals(0, wallet1.getBalance().compareTo(wallet2.getBalance()));
    }

    @Test
    void updateWallet_thenViolations() {
        WalletOperationRequest request = new WalletOperationRequest(
                "f54e3b9f-1262-4a2f-9236-53d74da89344",
                String.valueOf(WITHDRAW),
                new BigDecimal("10.00")
        );
        UUID walletId = UUID.fromString(request.walletId());

        Wallet wallet = createWallet();

        when(walletRepository.getWalletByUUID(walletId)).thenReturn(wallet);

        assertThrows(InsufficientFundsException.class,
                () -> walletService.updateWallet(request));
    }

    private Wallet createWallet() {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(0));
        return wallet;
    }
}
