package com.example.walletmanager2.error_handler;

import com.example.walletmanager2.controller.WalletController;
import com.example.walletmanager2.dto.WalletOperationRequest;
import com.example.walletmanager2.exception.InsufficientFundsException;
import com.example.walletmanager2.service.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WalletController.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private WalletService walletService;

    @Test
    void handleValidationExceptions_whenInvalidRequest() throws Exception {
        String jsonContent = """
                {
                    "walletId": "",
                    "operationType": "INVALID",
                    "amount": null
                }
                """;

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations.length()").value(4))
                .andExpect(jsonPath("$.violations[?(@.fieldName=='walletId' && @.message=='walletId не должен быть пустым')]").exists())
                .andExpect(jsonPath("$.violations[?(@.fieldName=='walletId' && @.message=='walletId имеет невалидный формат UUID')]").exists())
                .andExpect(jsonPath("$.violations[?(@.fieldName=='operationType' && @.message=='operationType должен быть DEPOSIT или WITHDRAW')]").exists())
                .andExpect(jsonPath("$.violations[?(@.fieldName=='amount' && @.message=='amount не должен быть null')]").exists());
    }

    @Test
    void handleWalletExceptions_thenViolations() throws Exception {

        String walletId = "f54e3b9f-1262-4a2f-9236-53d74da89344";
        String jsonContent = """
                {
                    "walletId": "f54e3b9f-1262-4a2f-9236-53d74da89344",
                    "operationType": "WITHDRAW",
                    "amount": 100
                }
                """;

        doThrow(new InsufficientFundsException(UUID.fromString(walletId)))
                .when(walletService).updateWallet(any(WalletOperationRequest.class));

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[0].fieldName").value("walletId"))
                .andExpect(jsonPath("$.violations[0].message").value("Недостаточно средств на кошельке '" + walletId + "' для операции."));
    }
}
