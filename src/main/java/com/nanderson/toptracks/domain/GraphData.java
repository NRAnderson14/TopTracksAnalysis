package com.nanderson.toptracks.domain;

import java.util.List;
import java.util.Map;

public class GraphData {
    private List<Map<String, Integer>> graphData;
    private List<String> allDataPoints;

    public List<Map<String, Integer>> getGraphData() {
        return graphData;
    }

    public void setGraphData(List<Map<String, Integer>> graphData) {
        this.graphData = graphData;
    }

    public List<String> getAllDataPoints() {
        return allDataPoints;
    }

    public void setAllDataPoints(List<String> allDataPoints) {
        this.allDataPoints = allDataPoints;
    }

}
