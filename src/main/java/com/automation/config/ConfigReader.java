package com.automation.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    static {
        try {
            String path = "src/test/resources/config.properties";
            FileInputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);

        }catch (IOException e) {
            throw new RuntimeException("Configuration file not found at src/test/resources/config.properties");
        }
    }

    public static String getBaseUrl() {
        // Reads from Maven/Jenkins command line first (-Denv=qa), falls back to properties file
        String env = System.getProperty("env", properties.getProperty("default.env"));
        return properties.getProperty(env.toLowerCase()+ ".url");
    }

    // NEW CHANGE: Added method to fetch DB URL
    public static String getDbUrl() {
        return properties.getProperty("db.url");
    }

    // NEW CHANGE: Added method to fetch DB Username
    public static String getDbUser() {
        return properties.getProperty("db.user");
    }

    // NEW CHANGE: Added method to fetch DB Password
    public static String getDbPassword() {
        return properties.getProperty("db.password");
    }

    // NEW CHANGE: Fetch execution target (local vs remote)
    public static String getExecutionTarget() {
        return System.getProperty("execution.target", properties.getProperty("execution.target"));
    }

    // NEW CHANGE: Fetch the Selenium Grid URL
    public static String getGridUrl() {
        return System.getProperty("grid.url", properties.getProperty("grid.url"));
    }

}
