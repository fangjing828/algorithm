package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayEqualsTest {
    @Test
    void testArrayEquals() {
        int[] arr1 = new int [] {1, 2, 3, 4};
        int[] arr2 = new int [] {1, 2, 3, 4};
        int[] arr3 = new int [] {1, 2, 4, 3};
        assertTrue(Arrays.equals(arr1, arr2));
        assertFalse(Arrays.equals(arr1, arr3));
    }
}