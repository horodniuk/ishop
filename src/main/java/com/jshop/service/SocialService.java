package com.jshop.service;

import com.jshop.model.SocialAccount;

public interface SocialService {
    String getAuthorizeUrl();

    SocialAccount getSocialAccount(String authToken);
}
