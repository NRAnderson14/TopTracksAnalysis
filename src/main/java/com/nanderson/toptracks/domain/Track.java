package com.nanderson.toptracks.domain;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Track {

    private Album album;
    private List<Artist> artists;
    private List<String> availableMarkets;
    private int discNumber;
    private long durationMs;
    private boolean explicit;
    private Map<String, String> externalIds;
    private String href;
    private String id;
    private String name;
    private int popularity;
    private String previewUrl;
    private int trackNumber;
    private String type;
    private String uri;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public List<String> getAvailableMarkets() {
        return availableMarkets;
    }

    public void setAvailableMarkets(List<String> availableMarkets) {
        this.availableMarkets = availableMarkets;
    }

    public int getDiscNumber() {
        return discNumber;
    }

    public void setDiscNumber(int discNumber) {
        this.discNumber = discNumber;
    }

    public long getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(long durationMs) {
        this.durationMs = durationMs;
    }

    public boolean isExplicit() {
        return explicit;
    }

    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }

    public Map<String, String> getExternalIds() {
        return externalIds;
    }

    public void setExternalIds(Map<String, String> externalIds) {
        this.externalIds = externalIds;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "Track [album=" + album + ", artists=" + artists + ", availableMarkets=" + availableMarkets
                + ", discNumber=" + discNumber + ", durationMs=" + durationMs + ", explicit=" + explicit
                + ", externalIds=" + externalIds + ", href=" + href + ", id=" + id + ", name=" + name + ", popularity="
                + popularity + ", previewUrl=" + previewUrl + ", trackNumber=" + trackNumber + ", type=" + type
                + ", uri=" + uri + "]";
    }

}
