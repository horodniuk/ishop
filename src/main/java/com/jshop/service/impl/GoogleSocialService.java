package com.jshop.service.impl;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.jshop.model.SocialAccount;
import com.jshop.service.SocialService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class GoogleSocialService implements SocialService {

    @Value("${social.google.clientId}")
    private String idClient;

    @Value("${social.google.clientSecret}")
    private String secret;

    @Value("${app.host}")
    private String host;

    private String getRedirectUrl(){
        return host + "/login/oauth2/code/google";
    }

    @Override
    public String getAuthorizeUrl() {
        GoogleAuthorizationCodeRequestUrl urlBuilder = new GoogleAuthorizationCodeRequestUrl(
                 idClient, getRedirectUrl(), Arrays.asList("openid", "email", "profile"))
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
                    getRedirectUrl()
            ).execute();

            String idTokenString = tokenResponse.getIdToken();
            GoogleIdToken idToken = GoogleIdToken.parse(JacksonFactory.getDefaultInstance(), idTokenString);

            String email = idToken.getPayload().getEmail();
            String name = (String) idToken.getPayload().get("name");

            return new SocialAccount(name, email);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

