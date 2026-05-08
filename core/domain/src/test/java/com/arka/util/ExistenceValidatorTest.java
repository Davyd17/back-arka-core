package com.arka.util;

import com.arka.exceptions.AlreadyExistsException;
import com.arka.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExistenceValidatorTest {

    record Item(Long id) {}

    private List<Item> items;

    @BeforeEach
    void setUp() {
        items = new ArrayList<>(List.of(new Item(1L), new Item(2L)));
    }

    // --- validateNoDuplicate ---

    @Test
    void shouldNotThrowWhenNoDuplicate() {
        assertDoesNotThrow(() ->
                ExistenceValidator.validateNoDuplicate(items, Item::id, 3L, Item.class));
    }

    @Test
    void shouldThrowWhenDuplicateExists() {
        assertThrows(AlreadyExistsException.class, () ->
                ExistenceValidator.validateNoDuplicate(items, Item::id, 1L, Item.class));
    }

    // --- validateExists ---

    @Test
    void shouldNotThrowWhenEntityExists() {
        assertDoesNotThrow(() ->
                ExistenceValidator.validateExists(items, Item::id, 1L, Item.class));
    }

    @Test
    void shouldThrowWhenEntityDoesNotExist() {
        assertThrows(NotFoundException.class, () ->
                ExistenceValidator.validateExists(items, Item::id, 99L, Item.class));
    }
}