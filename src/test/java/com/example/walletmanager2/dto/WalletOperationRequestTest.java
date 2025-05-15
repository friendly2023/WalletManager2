package com.example.walletmanager2.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;

public class WalletOperationRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsValid_thenNoViolations() {
        WalletOperationRequest request = createValidRequest();

        Set<ConstraintViolation<WalletOperationRequest>> violations = validator.validate(request);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenUuidIsNull_thenViolation() {
        WalletOperationRequest request = createValidRequest();
        request.setWalletId(null);

        Set<ConstraintViolation<WalletOperationRequest>> violations = validator.validate(request);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("walletId"));
    }

    @Test
    void whenUuidInvalid_thenViolation() {
        WalletOperationRequest request = createValidRequest();
        request.setWalletId("invalid-uuid");

        Set<ConstraintViolation<WalletOperationRequest>> violations = validator.validate(request);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("walletId"));
    }


    @Test
    void whenOperationTypeIsNull_thenViolation() {
        WalletOperationRequest request = createValidRequest();
        request.setOperationType(null);

        Set<ConstraintViolation<WalletOperationRequest>> violations = validator.validate(request);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("operationType"));
    }

    @Test
    void whenOperationTypeInvalid_thenViolation() {
        WalletOperationRequest request = createValidRequest();
        request.setOperationType("SAVE");

        Set<ConstraintViolation<WalletOperationRequest>> violations = validator.validate(request);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("operationType"));
    }


    @Test
    void whenAmountIsNull_thenViolation() {
        WalletOperationRequest request = createValidRequest();
        request.setAmount(null);

        Set<ConstraintViolation<WalletOperationRequest>> violations = validator.validate(request);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("amount"));
    }

    @Test
    void whenAmountTooSmall_thenViolation() {
        WalletOperationRequest request = createValidRequest();
        request.setAmount(new BigDecimal("0.001"));

        Set<ConstraintViolation<WalletOperationRequest>> violations = validator.validate(request);
        assertThat(violations).anyMatch(v -> v.getMessage().contains("больше 0.01"));
    }

    @Test
    void whenAmountHasTooManyDecimals_thenViolation() {
        WalletOperationRequest request = createValidRequest();
        request.setAmount(new BigDecimal("1.123"));

        Set<ConstraintViolation<WalletOperationRequest>> violations = validator.validate(request);
        assertThat(violations).anyMatch(v -> v.getMessage().contains("не более 2 знаков"));
    }


    @Test
    void testGettersAndSetters() {
        assertThat(WalletOperationRequest.class, hasValidBeanConstructor());
        assertThat(WalletOperationRequest.class, hasValidGettersAndSetters());
    }


    private WalletOperationRequest createValidRequest() {
        WalletOperationRequest request = new WalletOperationRequest();
        request.setWalletId("f54e3b9f-1262-4a2f-9236-53d74da89344");
        request.setOperationType("DEPOSIT");
        request.setAmount(new BigDecimal("10.00"));
        return request;
    }

}
