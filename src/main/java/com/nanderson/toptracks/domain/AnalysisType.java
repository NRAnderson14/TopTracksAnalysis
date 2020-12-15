package com.nanderson.toptracks.domain;

import com.nanderson.toptracks.exception.AnalysisException;

public enum AnalysisType {

    TRACK("Track"), ARTIST("Artist"), ALBUM("Album");

    private final String type;

    private AnalysisType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return type;
    }

    public static AnalysisType getFromDescription(String desc) throws AnalysisException {
        switch (desc.toLowerCase()) {
            case "track":
                return AnalysisType.TRACK;
            case "artist":
                return AnalysisType.ARTIST;
            case "album":
                return AnalysisType.ALBUM;
            default:
                throw new AnalysisException("Unknown analysis type: " + desc);
        }
    }
}
