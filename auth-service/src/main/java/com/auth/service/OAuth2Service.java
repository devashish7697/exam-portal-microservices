package com.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OAuth2Service {

    private final WebClient webClient;

    @Value("${oauth.google.client-id:}")
    private String googleClientId;
    @Value("${oauth.google.client-secret:}")
    private String googleClientSecret;
    @Value("${oauth.google.token-uri:}")
    private String googleTokenUri;
    @Value("${oauth.google.user-info-uri:}")
    private String googleUserInfoUri;

    @Value("${oauth.github.client-id:}")
    private String githubClientId;
    @Value("${oauth.github.client-secret:}")
    private String githubClientSecret;
    @Value("${oauth.github.token-uri:}")
    private String githubTokenUri;
    @Value("${oauth.github.user-api-uri:}")
    private String githubUserApiUri;
    @Value("${oauth.github.user-emails-api-uri:}")
    private String githubUserEmailsApiUri;

    public OAuth2Service(WebClient webClient) {
        this.webClient = webClient;
    }

    //Exchange code for access token and fetch Google user info.

}
