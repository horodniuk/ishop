package com.jshop.service;

import com.jshop.model.SocialAccount;

import java.io.IOException;

public interface SocialService {
    String getAuthorizeUrl() throws IOException;

    SocialAccount getSocialAccount(String authToken) throws IOException;
}
