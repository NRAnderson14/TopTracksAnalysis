package com.nanderson.toptracks.domain;

public class TrackAnalysisResult extends AnalysisResult {

    private Track track;

    public TrackAnalysisResult() {
        super();
        this.type = AnalysisType.TRACK;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    @Override
    public String toString() {
        return "TrackAnalysisResult [track=" + track + "]";
    }

}
