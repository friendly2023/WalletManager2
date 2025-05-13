package com.example.walletmanager2.service.validators;

import com.example.walletmanager2.dto.WalletOperationRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WalletOperationValidatorService {

    public List<String> validate(WalletOperationRequest request, BindingResult bindingResult){
        List<String> errors = new ArrayList<>();

        if (bindingResult.hasErrors()) {
            errors.addAll(
                    bindingResult.getFieldErrors().stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .toList()
            );
        }

//работает
        if (request.getWalletId() != null || !request.getWalletId().trim().isEmpty()) {
            try {
                UUID.fromString(request.getWalletId());
            } catch (IllegalArgumentException e) {
                errors.add("walletId имеет невалидный вид UUID");
            }
        }

//работает
        if (request.getOperationType() != null ||
                !(request.getOperationType().equalsIgnoreCase("DEPOSIT") ||
                        request.getOperationType().equalsIgnoreCase("WITHDRAW"))) {
            errors.add("operationType имеет невалидный вид, варианты: DEPOSIT, WITHDRAW");
        }



        return errors;
    }
}
