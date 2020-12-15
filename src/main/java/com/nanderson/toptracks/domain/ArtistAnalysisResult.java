package com.nanderson.toptracks.domain;

public class ArtistAnalysisResult extends AnalysisResult {

    private Artist artist;

    public ArtistAnalysisResult() {
        super();
        this.type = AnalysisType.ARTIST;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "ArtistAnalysisResult [artist=" + artist + "]";
    }

}
