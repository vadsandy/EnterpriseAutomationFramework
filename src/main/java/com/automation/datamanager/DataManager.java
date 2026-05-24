package com.automation.datamanager;

import com.automation.config.ConfigReader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;

// CHANGED: Ensuring File is imported correctly
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataManager {

    // 1. Fetch from JSON
    public static String getJsonData(String fileName, String jsonPath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // CHANGED: Capitalized 'File' here to fix the compilation error
            JsonNode root = mapper.readTree(new File("src/test/resources/testdata/" + fileName));
            return root.at(jsonPath).asText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON data: " + e.getMessage());
        }
    }

    // 2. Fetch from REST API
    public static String getApiData(String endpoint, String jsonPath) {
        Response response = RestAssured.get(endpoint);
        if (response.statusCode() != 200) {
            throw new RuntimeException("API call failed with status: " + response.statusCode());
        }
        return response.jsonPath().getString(jsonPath);
    }

    // 3. Fetch from SQL
    public static String getSqlData(String query, String columnName) {
        String dbUrl = ConfigReader.getDbUrl();
        String user = ConfigReader.getDbUser();
        String pass = ConfigReader.getDbPassword();

        try (Connection conn = DriverManager.getConnection(dbUrl, user, pass);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getString(columnName);
            }
            throw new RuntimeException("No data found for query: " + query);
        } catch (Exception e) {
            throw new RuntimeException("SQL Execution failed: " + e.getMessage());
        }
    }
}