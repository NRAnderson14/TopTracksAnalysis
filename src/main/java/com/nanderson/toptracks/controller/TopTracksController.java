package com.nanderson.toptracks.controller;

import java.util.List;
import java.util.Optional;

import com.nanderson.toptracks.domain.AnalysisResult;
import com.nanderson.toptracks.domain.AnalysisType;
import com.nanderson.toptracks.domain.Artist;
import com.nanderson.toptracks.domain.Playlist;
import com.nanderson.toptracks.domain.PlaylistDetail;
import com.nanderson.toptracks.domain.TrackAnalysisResult;
import com.nanderson.toptracks.domain.UserInfo;
import com.nanderson.toptracks.domain.UserPlaylists;
import com.nanderson.toptracks.exception.SpotifyAPIException;
import com.nanderson.toptracks.service.AnalysisService;
import com.nanderson.toptracks.service.ApiService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class TopTracksController {

    private static final Logger logger = LoggerFactory.getLogger(TopTracksController.class);

    private ApiService apiService;
    private AnalysisService analysisService;

    public TopTracksController(ApiService apiService, AnalysisService analysisService) {
        super();
        this.apiService = apiService;
        this.analysisService = analysisService;
    }

    @GetMapping(path = "/artist/{id}")
    public ResponseEntity<Artist> getArtist(@PathVariable("id") String id) {
        ResponseEntity<Artist> response;

        try {
            response = ResponseEntity.ok(apiService.getArtistInfo(id));
        } catch (Exception e) {
            logger.error("Error retrieving artist info from Spotify", e);
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
        // return createResponse(() -> artistService.getArtistInfo(id));
    }

    @GetMapping("/user/info")
    public ResponseEntity<UserInfo> getUserInfo() {
        ResponseEntity<UserInfo> response;

        try {
            response = ResponseEntity.ok(apiService.getUserInfo());
        } catch (Exception e) {
            logger.error("Error getting current user info", e);
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @GetMapping("/user/playlists")
    public ResponseEntity<UserPlaylists> getUserPlaylists() {
        ResponseEntity<UserPlaylists> response;

        try {
            response = ResponseEntity.ok(apiService.getUserPlaylists());
        } catch (Exception e) {
            logger.error("Error getting current user playlists", e);
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @GetMapping("user/playlists/recaps")
    public ResponseEntity<List<Playlist>> getUserRecapPlaylists() {
        ResponseEntity<List<Playlist>> response;

        try {
            response = ResponseEntity.ok(apiService.getUserYearlyRecapPlaylists());
        } catch (Exception e) {
            logger.error("Error getting current user playlists", e);
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @GetMapping("user/playlists/recaps_with_tracks")
    public ResponseEntity<List<PlaylistDetail>> getUserRecapPlaylistsWithTracks() {
        ResponseEntity<List<PlaylistDetail>> response;

        try {
            response = ResponseEntity.ok(apiService.getRecapPlaylistDetails());
        } catch (Exception e) {
            logger.error("Error getting current user playlists", e);
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @GetMapping("user/playlists/recaps/top_track")
    public ResponseEntity<TrackAnalysisResult> getUserRecapsTopTrack() {
        ResponseEntity<TrackAnalysisResult> response;

        try {
            List<PlaylistDetail> recapPlaylists = apiService.getRecapPlaylistDetails();
            response = ResponseEntity.ok(analysisService.getMostPopularTrack(recapPlaylists));
        } catch (Exception e) {
            logger.error("Error getting current user playlists", e);
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @GetMapping("user/playlists/recaps/multi_occurrences")
    public ResponseEntity<List<? extends AnalysisResult>> getUserRecapsMultiOccurrences(
            @RequestParam("type") String type) {
        ResponseEntity<List<? extends AnalysisResult>> response;

        try {
            List<PlaylistDetail> recapPlaylists = apiService.getRecapPlaylistDetails();
            AnalysisType analysisType = AnalysisType.getFromDescription(type);
            switch (analysisType) {
                case TRACK:
                    response = ResponseEntity.ok(analysisService.getSortedMultiOccurrenceTracks(recapPlaylists));
                    break;
                case ARTIST:
                    response = ResponseEntity.ok(analysisService.getSortedMultiOccurrenceArtists(recapPlaylists));
                    break;
                case ALBUM:
                    response = ResponseEntity.ok(analysisService.getSortedMultiOccurrenceAlbums(recapPlaylists));
                    break;
                default:
                    response = ResponseEntity.badRequest().build();
                    break;
            }
        } catch (Exception e) {
            logger.error("Error getting current user playlists", e);
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    // private <T> ResponseEntity<T> createResponse(Supplier<T> handlingMethod) {
    // ResponseEntity<T> response;

    // try {
    // response = ResponseEntity.ok(handlingMethod.get());
    // } catch (Exception e) {
    // logger.error("Error encountered", e);
    // response = new ResponseEntity<T>(HttpStatus.INTERNAL_SERVER_ERROR);
    // }

    // return response;
    // }

    @GetMapping(path = "/login")
    public RedirectView login() {
        return new RedirectView(apiService.getUrlForLogin());
    }

    @GetMapping(path = "/receive_code")
    public RedirectView receiveCode(@RequestParam("code") String authCode,
            @RequestParam(name = "state") Optional<String> state,
            @RequestParam(name = "error") Optional<String> error) {
        try {
            apiService.postAuthorizeUser(authCode, state, error);
            return new RedirectView("http://localhost:8080/user/info");
        } catch (SpotifyAPIException e) {
            return new RedirectView("http://localhost:8080/error");
        }
    }

    @GetMapping(path = "/home")
    public String home() {
        return "success";
    }

    @GetMapping(path = "/error")
    public String error() {
        return "error";
    }
}
