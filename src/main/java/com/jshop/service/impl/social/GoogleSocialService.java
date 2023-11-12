package com.jshop.service.impl.social;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.jshop.model.SocialAccount;
import com.jshop.service.SocialService;
import com.jshop.service.impl.ServiceManager;

import java.io.IOException;
import java.util.Arrays;

public class GoogleSocialService implements SocialService {
    private final String idClient;
    private final String secret;
    private final String redirectUrl;

    public GoogleSocialService(ServiceManager serviceManager) {
        idClient = serviceManager.getApplicationProperty("social.google.clientId");
        secret = serviceManager.getApplicationProperty("social.google.clientSecret");
        redirectUrl = serviceManager.getApplicationProperty("app.host") + "/login/oauth2/code/google";
    }

    @Override
    public String getAuthorizeUrl() {
        GoogleAuthorizationCodeRequestUrl urlBuilder = new GoogleAuthorizationCodeRequestUrl(
                 idClient, redirectUrl, Arrays.asList("openid", "email", "profile"))
                .setAccessType("offline")
                .setApprovalPrompt("force");
        return urlBuilder.build();
    }

    @Override
    public SocialAccount getSocialAccount(String authToken) {
        try {
            GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    idClient,
                    secret,
                    authToken,
                    redirectUrl
            ).execute();

            // Assuming the ID token is available in the response
            String idTokenString = tokenResponse.getIdToken();
            GoogleIdToken idToken = GoogleIdToken.parse(JacksonFactory.getDefaultInstance(), idTokenString);

            // Extract user information from the ID token
            String email = idToken.getPayload().getEmail();
            String name = (String) idToken.getPayload().get("name");

            return new SocialAccount(name, email);

        } catch (IOException e) {
            // Handle exceptions appropriately
            e.printStackTrace(); // Logging or throw a custom exception
            return null;
        }
    }
}

