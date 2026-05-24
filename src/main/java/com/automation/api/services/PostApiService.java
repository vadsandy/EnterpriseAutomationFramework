package com.automation.api.services;

import com.automation.api.RestClient;
import com.automation.api.models.PostPayload;
import io.restassured.response.Response;

public class PostApiService {

    private RestClient restClient;
    private static final String BASE_URI = "https://jsonplaceholder.typicode.com";
    private static final String POSTS_ENDPOINT = "/posts";

    public PostApiService() {
        // Initialize the universal client with the base URI
        this.restClient = new RestClient(BASE_URI);
    }

    // Pass the POJO directly to the RestClient
    public Response createNewPost(PostPayload payload) {
        restClient.setBody(payload);
        return restClient.post(POSTS_ENDPOINT);
    }

    public Response getPostById(int postId) {
        return restClient.get(POSTS_ENDPOINT + "/" + postId);
    }
}