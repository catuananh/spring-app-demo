package com.appsdeveloperblog.app.ws.shared.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UtilsTest extends Object {

    @Autowired
    Utils utils;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testHasTokenNotExpired() {

        String token = utils.generateEmailVerificationToken("Tf0XTxKBbQS62kC1qfNAiNsawYyB9m");
        assertNotNull(token);

        boolean hasTokenExpired = utils.hasTokenExpired(token);
        assertFalse(hasTokenExpired);
    }

    @Test
    void testHasTokenExpired() {

        String expriredToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJUZjBYVHhLQmJRUzYya0MxcWZOQWlOc2F3WXlCOW0iLCJleHAiOjE2MzU0MTI4MTh9.-VFrj31Mn8c25xmas53uQjSLs6b2d6jA1Ti46mp4XCGY-dyhUI6acyShRT99Wr3bqSjMbXvGQ8cA_-5Yb0f9MA";
        boolean hasTokenExpired = utils.hasTokenExpired(expriredToken);

        assertTrue(hasTokenExpired);
    }

    @Test
    void testGenerateUserId() {
        String userId1 = utils.generateUserId(30);
        String userId2 = utils.generateUserId(30);

        assertNotNull(userId1);
        assertNotNull(userId2);

        assertTrue(userId1.length()==30);
        assertTrue(!userId1.equalsIgnoreCase(userId2));
    }
}