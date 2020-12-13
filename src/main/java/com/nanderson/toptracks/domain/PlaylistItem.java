package com.nanderson.toptracks.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaylistItem {
    
    private Date addedAt;
    // private ? addedBy/
    private boolean isLocal;
    private Track track;

    @JsonIgnore
    private String belongsTo;

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public String getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(String belongsTo) {
        this.belongsTo = belongsTo;
    }

    @Override
    public String toString() {
        return "PlaylistItem [addedAt=" + addedAt + ", belongsTo=" + belongsTo + ", isLocal=" + isLocal + ", track="
                + track + "]";
    }

}
