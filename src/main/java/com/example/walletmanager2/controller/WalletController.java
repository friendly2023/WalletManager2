package com.example.walletmanager2.controller;

import com.example.walletmanager2.dto.WalletIdRequest;
import com.example.walletmanager2.dto.WalletOperationRequest;
import com.example.walletmanager2.entity.Wallet;
import com.example.walletmanager2.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class WalletController {

    private WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping(value = "/newWallet")
    public void creatingNewWallet() {

        walletService.createWallet();
    }

    @PostMapping(value = "/wallet")
    public void applyOperation(@Valid @RequestBody WalletOperationRequest walletOperationRequest) {

        walletService.updateWallet(walletOperationRequest);
    }

    @GetMapping(value = "/wallets/{walletId}")
    public Wallet getWallet(@Valid WalletIdRequest request) {

        return walletService.getWalletByUUID(request.getWalletId());
    }
}
