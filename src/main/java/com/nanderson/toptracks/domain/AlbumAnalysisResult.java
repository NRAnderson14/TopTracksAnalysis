package com.nanderson.toptracks.domain;

public class AlbumAnalysisResult extends AnalysisResult {

    private Album album;

    public AlbumAnalysisResult() {
        super();
        this.type = AnalysisType.ALBUM;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "AlbumAnalysisResult [album=" + album + "]";
    }

}
