package com.enterprise.gateway;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtAuthFilterTest {

    @Test
    void config_shouldBeInstantiable() {
        JwtAuthFilter.Config config = new JwtAuthFilter.Config();
        assertNotNull(config);
    }

    @Test
    void filter_shouldCreateInstance() {
        JwtAuthFilter filter = new JwtAuthFilter();
        assertNotNull(filter);
        assertEquals(JwtAuthFilter.Config.class, filter.getConfigClass());
    }
}
