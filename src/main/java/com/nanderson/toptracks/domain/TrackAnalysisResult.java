package com.nanderson.toptracks.domain;

import java.util.Date;
import java.util.List;

public class TrackAnalysisResult {

    private Track track;
    private int occurrences;
    private Date firstAppearance; // change to object later
    private Date latestAppearance;
    private List<Date> appearances;
    private int averagePosition;

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }

    public Date getFirstAppearance() {
        return firstAppearance;
    }

    public void setFirstAppearance(Date firstAppearance) {
        this.firstAppearance = firstAppearance;
    }

    public Date getLatestAppearance() {
        return latestAppearance;
    }

    public void setLatestAppearance(Date latestAppearance) {
        this.latestAppearance = latestAppearance;
    }

    public List<Date> getAppearances() {
        return appearances;
    }

    public void setAppearances(List<Date> appearances) {
        this.appearances = appearances;
    }

    public int getAveragePosition() {
        return averagePosition;
    }

    public void setAveragePosition(int averagePosition) {
        this.averagePosition = averagePosition;
    }

    @Override
    public String toString() {
        return "TrackAnalysisResult [appearances=" + appearances + ", averagePosition=" + averagePosition
                + ", firstAppearance=" + firstAppearance + ", latestAppearance=" + latestAppearance + ", occurrences="
                + occurrences + ", track=" + track + "]";
    }

}
