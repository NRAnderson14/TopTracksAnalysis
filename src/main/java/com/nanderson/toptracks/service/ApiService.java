package com.nanderson.toptracks.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.nanderson.toptracks.config.UrlConfiguration;
import com.nanderson.toptracks.domain.Playlist;
import com.nanderson.toptracks.domain.PlaylistDetail;
import com.nanderson.toptracks.domain.UserInfo;
import com.nanderson.toptracks.domain.UserPlaylists;
import com.nanderson.toptracks.exception.SpotifyAPIException;
import com.nanderson.toptracks.util.TokenUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);

    private TokenUtil tokenUtil;
    private RestTemplate restTemplate;
    private UrlConfiguration urlConfig;

    public ApiService(TokenUtil tokenUtil, RestTemplate restTemplate, UrlConfiguration urlConfig) {
        super();
        this.tokenUtil = tokenUtil;
        this.restTemplate = restTemplate;
        this.urlConfig = urlConfig;
    }

    public UserInfo getUserInfo() throws SpotifyAPIException {
        ResponseEntity<UserInfo> userInfoResponse = restTemplate.exchange(getUrlForEndpoint("me"), HttpMethod.GET,
                getEntity(), UserInfo.class);

        return extractBody(userInfoResponse);
    }

    public UserPlaylists getUserPlaylists() throws SpotifyAPIException {
        ResponseEntity<UserPlaylists> userPlaylistsResponse = restTemplate.exchange(getUrlForEndpoint("me/playlists"),
                HttpMethod.GET, getEntity(), UserPlaylists.class);

        UserPlaylists playlists = extractBody(userPlaylistsResponse);

        while (playlists.getNext() != null) {
            userPlaylistsResponse = restTemplate.exchange(playlists.getNext(), HttpMethod.GET, getEntity(),
                    UserPlaylists.class);
            UserPlaylists newBody = extractBody(userPlaylistsResponse);
            playlists.getItems().addAll(newBody.getItems());
            playlists.setOffset(newBody.getOffset());
            playlists.setPrevious(newBody.getPrevious());
            playlists.setNext(newBody.getNext());
        }

        return playlists;
    }

    public List<Playlist> getUserYearlyRecapPlaylists() throws SpotifyAPIException {
        UserPlaylists allPlaylists = getUserPlaylists();

        return allPlaylists.getItems().stream().filter(playlist -> playlist.getName().startsWith("Your Top Songs")
                || playlist.getName().endsWith("Top Tracks")).collect(Collectors.toList());
    }

    public PlaylistDetail getPlaylistDetail(Playlist playlist) throws SpotifyAPIException {
        ResponseEntity<PlaylistDetail> playlistDetailResponse = restTemplate.exchange(playlist.getTracks().getHref(),
                HttpMethod.GET, getEntity(), PlaylistDetail.class);

        PlaylistDetail playlistDetail = extractBody(playlistDetailResponse);
        // Setting a callback to the owning playlist for analysis purposes later
        playlistDetail.setBelongsTo(playlist.getId());
        playlistDetail.getItems().stream().forEach(item -> item.setBelongsTo(playlist.getId()));

        return playlistDetail;
    }

    public List<PlaylistDetail> getRecapPlaylistDetails() throws SpotifyAPIException {
        List<Playlist> recapPlaylists = getUserYearlyRecapPlaylists();

        List<PlaylistDetail> playlistDetails = new ArrayList<>();
        for (Playlist playlist : recapPlaylists) {
            playlistDetails.add(getPlaylistDetail(playlist));
        }

        return playlistDetails;
    }

    public String getUrlForLogin() {
        return "https://accounts.spotify.com/en/authorize?client_id=aa67e55ded024973b5b81a51abdbfe15&scope=playlist-read-private&response_type=code&redirect_uri=http%3A%2F%2Flocalhost%3A3000%2F";
    }

    public void postAuthorizeUser(String authCode, Optional<String> state, Optional<String> error)
            throws SpotifyAPIException {
        if (error.isPresent()) {
            logger.error("Error authenticating user: {}", error.get());
            throw new SpotifyAPIException(error.get());
        }

        logger.info("Authorization Code: {}", authCode);
        logger.info("State: {}", state.orElse("No State Provided"));

        tokenUtil.requestRefreshAndAccessTokensFromSpotify(authCode);
    }

    private String getUrlForEndpoint(String endpoint) {
        return String.format("%s%s", urlConfig.getBaseSpotifyUrl(), endpoint);
    }

    private HttpEntity<Void> getEntity() throws SpotifyAPIException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenUtil.getBearerToken());
        return new HttpEntity<>(headers);
    }

    private <T> T extractBody(ResponseEntity<T> receivedResponse) throws SpotifyAPIException {
        if (receivedResponse.getStatusCode().is2xxSuccessful()) {
            return receivedResponse.getBody();
        } else {
            logger.error("Received non-200 response from Spotify: {}", receivedResponse.getStatusCodeValue());
            throw new SpotifyAPIException(receivedResponse.getStatusCode().toString());
        }
    }

}
