package com.example.walletmanager2.controller;

import com.example.walletmanager2.dto.WalletOperationRequest;
import com.example.walletmanager2.service.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(WalletController.class)
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private WalletService walletService;

    @Test
    void creatingNewWalletTest() throws Exception {
        mockMvc.perform(post("/api/v1/newWallet"))
                .andExpect(status().isOk());

        verify(walletService, times(1)).createWallet();
    }

    @Test
    void applyOperationTest() throws Exception {
        WalletOperationRequest request = new WalletOperationRequest();
        request.setWalletId("f54e3b9f-1262-4a2f-9236-53d74da89344");
        request.setOperationType("DEPOSIT");
        request.setAmount(new BigDecimal("10.00"));

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "walletId": "f54e3b9f-1262-4a2f-9236-53d74da89344",
                                        "operationType": "DEPOSIT",
                                        "amount": 10.00
                                    }
                                """))
                .andExpect(status().isOk());

        verify(walletService, times(1)).updateWallet(any(WalletOperationRequest.class));
    }

}
