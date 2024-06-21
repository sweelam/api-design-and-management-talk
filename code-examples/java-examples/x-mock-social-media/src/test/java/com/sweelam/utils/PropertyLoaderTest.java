package com.sweelam.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PropertyLoaderTest {
    @Test
    void testLoad() {
        var property = new PropertyLoader();
        String string = property.load("spring.data.mongodb.uri");
        assertNotNull(string);
        assertTrue(string.length() != 0);
    }
}
