package com.nanderson.toptracks.domain;

import java.util.Date;
import java.util.List;

public abstract class AnalysisResult<T> {

    protected int occurrences;
    protected Date firstAppearance; // change to object later
    protected Date latestAppearance;
    protected List<Date> appearances;
    protected int averagePosition;
    protected AnalysisType type;
    protected T analysisItem;

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

    public AnalysisType getType() {
        return type;
    }

    public void setType(AnalysisType type) {
        this.type = type;
    }

    public T getAnalysisItem() {
        return analysisItem;
    }

    public void setAnalysisItem(T analysisItem) {
        this.analysisItem = analysisItem;
    }

    @Override
    public String toString() {
        return "AnalysisResult [analysisItem=" + analysisItem + ", appearances=" + appearances + ", averagePosition="
                + averagePosition + ", firstAppearance=" + firstAppearance + ", latestAppearance=" + latestAppearance
                + ", occurrences=" + occurrences + ", type=" + type + "]";
    }

}
