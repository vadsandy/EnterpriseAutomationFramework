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

}
