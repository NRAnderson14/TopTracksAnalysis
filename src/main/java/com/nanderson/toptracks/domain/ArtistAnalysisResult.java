package com.nanderson.toptracks.domain;

public class ArtistAnalysisResult extends AnalysisResult<Artist> {

    public ArtistAnalysisResult() {
        super();
        this.type = AnalysisType.ARTIST;
    }

}
