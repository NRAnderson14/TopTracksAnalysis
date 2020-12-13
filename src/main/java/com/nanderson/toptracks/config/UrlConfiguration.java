package com.nanderson.toptracks.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("url-configuration")
public class UrlConfiguration {

    private String baseSpotifyUrl;
    private String spotifyAuthUrl;
    private String baseApplicationUrl;

    public String getBaseSpotifyUrl() {
        return baseSpotifyUrl;
    }

    public void setBaseSpotifyUrl(String baseSpotifyUrl) {
        this.baseSpotifyUrl = baseSpotifyUrl;
    }

    public String getSpotifyAuthUrl() {
        return spotifyAuthUrl;
    }

    public void setSpotifyAuthUrl(String spotifyAuthUrl) {
        this.spotifyAuthUrl = spotifyAuthUrl;
    }

    public String getBaseApplicationUrl() {
        return baseApplicationUrl;
    }

    public void setBaseApplicationUrl(String baseApplicationUrl) {
        this.baseApplicationUrl = baseApplicationUrl;
    }

}
