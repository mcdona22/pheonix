package com.mcdona22.pheonix.common;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RequiredArgsConstructor
@Component
public class TestDatabaseSetup {
    @Autowired
    private final DataSource dataSource;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public void deleteAllUsers() {
        final String sql = "DELETE FROM APP_USER";
        logger.info("Executing sql: " + sql);
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to delete test users via JDBC: " + e.getMessage());
            throw new RuntimeException("Database cleanup failure.", e);
        }
    }

    public void createAppUser(String id, String displayName, String email, String photoURL) {
        final String sql =
                "INSERT INTO APP_USER (ID, DISPLAY_NAME, EMAIL, PHOTOURL) VALUES (?, ?, ?, ?)";
        logger.info("Executing sql: {}", sql);

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.setString(2, displayName);
            ps.setString(3, email);
            ps.setString(4, photoURL != null ? photoURL : "");

            ps.executeUpdate();
        } catch (SQLException e) {
            // Log the error for visibility in the test environment
            System.err.println("Failed to insert test user via JDBC: " + e.getMessage());
            throw new RuntimeException("Database setup failure.", e);
        }
    }


}
