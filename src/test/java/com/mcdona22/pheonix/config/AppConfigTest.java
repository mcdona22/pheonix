package com.mcdona22.pheonix.config;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AppConfigTest {
    @Test
    @DisplayName("Successive calls should yield different id values")
    public void testSuccessiveCallsForIdCreation() {
        AppConfig appConfig = new AppConfig();
        var idSupplier = appConfig.entityIdSupplier();
        
        final var id1 = idSupplier.get();
        final var id2 = idSupplier.get();

        assertNotNull(id1);
        assertNotNull(id2);
        assertNotEquals(id1, id2, "These should not be equal");

    }
}
