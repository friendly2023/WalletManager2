package com.example.walletmanager2.controller;

import com.example.walletmanager2.dto.WalletOperationRequest;
import com.example.walletmanager2.entity.Wallet;
import com.example.walletmanager2.service.WalletService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class WalletController {

    private final WalletService walletService;

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
    public Wallet getWallet(
            @PathVariable
            @Valid
            @NotBlank(message = "walletId не должен быть пустым")
            @Pattern(
                    regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
                    message = "walletId имеет невалидный формат UUID"
            )
            String walletId) {

        return walletService.getWalletByUUID(walletId);
    }
}
