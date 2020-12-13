package com.nanderson.toptracks.domain;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Playlist {

    private boolean collaborative;
    private Map<String, String> externalUrls;
    private String href;
    private String id;
    private String name;
    // private String owner; // don't need this right now
    @JsonProperty("public")
    private boolean isPublic;
    private String shapshotId;
    private PlaylistTrackInfo tracks;
    private String type;
    private String uri;

    public boolean isCollaborative() {
        return collaborative;
    }

    public void setCollaborative(boolean collaborative) {
        this.collaborative = collaborative;
    }

    public Map<String, String> getExternalUrls() {
        return externalUrls;
    }

    public void setExternalUrls(Map<String, String> externalUrls) {
        this.externalUrls = externalUrls;
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

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getShapshotId() {
        return shapshotId;
    }

    public void setShapshotId(String shapshotId) {
        this.shapshotId = shapshotId;
    }

    public PlaylistTrackInfo getTracks() {
        return tracks;
    }

    public void setTracks(PlaylistTrackInfo tracks) {
        this.tracks = tracks;
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
        return "Playlist [collaborative=" + collaborative + ", externalUrls=" + externalUrls + ", href=" + href
                + ", id=" + id + ", isPublic=" + isPublic + ", name=" + name + ", shapshotId=" + shapshotId
                + ", tracks=" + tracks + ", type=" + type + ", uri=" + uri + "]";
    }

}
