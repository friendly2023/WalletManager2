package com.example.walletmanager2.controller;

import com.example.walletmanager2.dto.WalletOperationRequest;
import com.example.walletmanager2.service.WalletService;
import com.example.walletmanager2.service.validators.WalletOperationValidatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class WalletController {

    private WalletService walletService;
    private WalletOperationValidatorService walletOperationValidatorService;

    public WalletController(WalletService walletService, WalletOperationValidatorService walletOperationValidatorService) {
        this.walletService = walletService;
        this.walletOperationValidatorService = walletOperationValidatorService;
    }

    @PostMapping(value = "/newWallet")
    public void creatingNewWallet() {

        walletService.createWallet();
    }

    @PostMapping(value = "/wallet")
    public ResponseEntity<?> applyOperation(@RequestBody WalletOperationRequest walletOperationRequest,
                                                                    BindingResult bindingResult) {

        List<String> errors = walletOperationValidatorService.validate(walletOperationRequest, bindingResult);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("errors", errors));
        }

        walletService.updateWallet(walletOperationRequest);

        return null;
    }
}
