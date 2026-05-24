package com.automation.api.models;

public class PostPayload {
    private String title;
    private String body;
    private int userId;

    // Default constructor for Deserialization
    public PostPayload() {}

    // Constructor for Serialization
    public PostPayload(String title, String body, int userId) {
        this.title = title;
        this.body = body;
        this.userId = userId;
    }

    public String getTitle() { return title; }
    public String getBody() { return body; }
    public int getUserId() { return userId; }
}