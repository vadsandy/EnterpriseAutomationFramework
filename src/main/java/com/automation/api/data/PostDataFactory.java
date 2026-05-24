package com.automation.api.data;

import com.automation.api.models.PostPayload;
import net.datafaker.Faker;

public class PostDataFactory {

    public static PostPayload createDynamicPost() {
        Faker faker = new Faker();

        // Generate random, realistic data
        String randomTitle = faker.book().title();
        String randomBody = faker.lorem().paragraph();
        int randomUserId = faker.number().numberBetween(1, 100);

        return new PostPayload(randomTitle, randomBody, randomUserId);
    }
}