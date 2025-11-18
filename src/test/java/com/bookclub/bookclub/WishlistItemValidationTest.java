package com.bookclub.bookclub;

import com.bookclub.model.WishlistItem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WishlistItemValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // Java Test #1: valid object passes validation
    @Test
    void validWishlistItem_hasNoViolations() {
        WishlistItem item = new WishlistItem();
        item.setId("xyz789");
        item.setIsbn("9780307474278");
        item.setTitle("The Girl with the Dragon Tattoo");
        item.setUsername("testuser01");

        Set<ConstraintViolation<WishlistItem>> violations = validator.validate(item);
        assertTrue(violations.isEmpty(), "Expected no validation errors");
    }

    // Java Test #2: missing required fields triggers @NotEmpty/@NotNull
    @Test
    void invalidWishlistItem_hasViolationsForIsbnAndTitle() {
        WishlistItem item = new WishlistItem();
        item.setUsername("testuser01");
        // leave isbn/title null/empty

        Set<ConstraintViolation<WishlistItem>> violations = validator.validate(item);
        assertFalse(violations.isEmpty(), "Expected validation errors");

        // quick sanity checks that at least those fields were flagged
        boolean isbnError = violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("isbn"));
        boolean titleError = violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title"));
        assertTrue(isbnError, "ISBN should be required");
        assertTrue(titleError, "Title should be required");
    }
}

