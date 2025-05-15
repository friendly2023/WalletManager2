package com.example.walletmanager2.error_handler;

import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;

public class ViolationTest {

    @Test
    void testGettersAndSetters() {
        assertThat(Violation.class, hasValidGettersAndSetters());
    }
}
