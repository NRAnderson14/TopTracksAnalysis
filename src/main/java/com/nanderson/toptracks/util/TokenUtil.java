package com.nanderson.toptracks.util;

import java.time.LocalDateTime;
import java.util.Arrays;

import com.nanderson.toptracks.domain.Token;
import com.nanderson.toptracks.exception.SpotifyAPIException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    private static Token token = null;
    private static LocalDateTime receivedTime = null;

    private RestTemplate restTemplate;

    public TokenUtil(RestTemplate restTemplate) {
        super();
        this.restTemplate = restTemplate;
    }

    public String getBearerToken() throws SpotifyAPIException {
        if (tokenHasNotBeenReceived()) {
            throw new SpotifyAPIException("User is not authenticated yet!");
        }

        if (isTokenExpired()) {
            requestNewTokenFromSpotify();
        }

        StringBuilder tokenStringBuilder = new StringBuilder("Bearer ");
        tokenStringBuilder.append(token.getAccessToken());
        return tokenStringBuilder.toString();
    }

    private boolean tokenHasNotBeenReceived() {
        if (token == null || receivedTime == null) {
            return true;
        }

        return false;
    }

    private boolean isTokenExpired() {
        LocalDateTime tokenExpirationTime = receivedTime.plusSeconds(token.getExpiresIn());
        if (LocalDateTime.now().isAfter(tokenExpirationTime)) {
            return true;
        }

        return false;
    }

    private void requestNewTokenFromSpotify() throws SpotifyAPIException {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
        restTemplate.getMessageConverters().add(converter);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic <base-64 encoded client_id:client_secret>");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "refresh_token");
        body.add("refresh_token", token.getRefreshToken());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Token> tokenResponse = restTemplate.exchange("https://accounts.spotify.com/api/token",
                HttpMethod.POST, entity, Token.class);

        if (tokenResponse.getStatusCode().is2xxSuccessful()) {
            token = tokenResponse.getBody();
            receivedTime = LocalDateTime.now();
            logger.info("Successfully refreshed token. New token: {}", token);
        } else {
            logger.error("Received non-200 response from Spotify: {}", tokenResponse.getStatusCodeValue());
            throw new SpotifyAPIException(tokenResponse.getStatusCode().toString());
        }
    }

    public void requestRefreshAndAccessTokensFromSpotify(String authCode) throws SpotifyAPIException {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
        restTemplate.getMessageConverters().add(converter);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic <base-64 encoded client_id:client_secret>");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", authCode);
        // Pull this into config at some point. Also needs to match what was previously
        // supplied as the redirect uri on the previous step
        body.add("redirect_uri", "http://localhost:8080/receive_code/");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Token> tokenResponse = restTemplate.exchange("https://accounts.spotify.com/api/token",
                HttpMethod.POST, entity, Token.class);

        if (tokenResponse.getStatusCode().is2xxSuccessful()) {
            token = tokenResponse.getBody();
            receivedTime = LocalDateTime.now();
            logger.info("Loaded token: {}", token.toString());
        } else {
            logger.error("Received non-200 response from Spotify: {}", tokenResponse.getStatusCodeValue());
            throw new SpotifyAPIException(tokenResponse.getStatusCode().toString());
        }
    }

}
