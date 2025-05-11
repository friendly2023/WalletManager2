package com.example.walletmanager2.controller;

import com.example.walletmanager2.service.WalletService;
import org.springframework.web.bind.annotation.PostMapping;
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
}
