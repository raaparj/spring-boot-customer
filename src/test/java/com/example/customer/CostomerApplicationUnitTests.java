package com.example.customer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CostomerApplicationUnitTests {

    @Test
    public void ifGivenAnInvalidEmailAddresses_thenReturnFalse() {
        assertFalse(Customer.isValidEmailAddress("notAnEmailAddress"));
    }

    @Test
    public void ifGivenValidEmailAddresses_thenReturnTrue() {
        assertTrue(Customer.isValidEmailAddress("valid@email.address.com"));
    }

    @Test
    public void ifGivenAnInvalidStringAsNumber_thenReturnFalse() {
        assertFalse(Customer.isNumeric("notNumber"));
    }

    @Test
    public void ifGivenAnValidStringAsNumber_thenReturnTrue() {
        assertTrue(Customer.isNumeric("12345"));
    }

}
