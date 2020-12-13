package com.nanderson.toptracks.domain;

import java.util.Date;
import java.util.List;

public class TrackAnalysisResult {

    private Track track;
    private int occurrences;
    private Date firstOccurrence; // change to object later
    private Date latestOccurrence;
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

    public Date getFirstOccurrence() {
        return firstOccurrence;
    }

    public void setFirstOccurrence(Date firstOccurrence) {
        this.firstOccurrence = firstOccurrence;
    }

    public Date getLatestOccurrence() {
        return latestOccurrence;
    }

    public void setLatestOccurrence(Date latestOccurrence) {
        this.latestOccurrence = latestOccurrence;
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
                + ", firstOccurrence=" + firstOccurrence + ", latestOccurrence=" + latestOccurrence + ", occurrences="
                + occurrences + ", track=" + track + "]";
    }

}
