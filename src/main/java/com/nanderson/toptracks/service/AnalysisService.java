package com.nanderson.toptracks.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.nanderson.toptracks.domain.PlaylistDetail;
import com.nanderson.toptracks.domain.PlaylistItem;
import com.nanderson.toptracks.domain.TrackAnalysisResult;
import com.nanderson.toptracks.exception.AnalysisException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(AnalysisService.class);

    public AnalysisService() {
        super();
    }

    public TrackAnalysisResult getMostPopularTrack(List<PlaylistDetail> playlistsToAnalyze) throws AnalysisException {
        List<PlaylistItem> itemsAggregated = aggregatePlaylists(playlistsToAnalyze);

        Map<String, Long> trackIdFrequencies = countTrackIdOccurrences(itemsAggregated);

        Entry<String, Long> maxOccurrences = trackIdFrequencies.entrySet().stream().max(Entry.comparingByValue())
                .orElseThrow(() -> new AnalysisException("Unable to find a single most popular track."));
        String trackId = maxOccurrences.getKey();

        List<PlaylistItem> allTrackOccurrences = itemsAggregated.stream()
                .filter(item -> trackId.equals(item.getTrack().getId())).collect(Collectors.toList());

        TrackAnalysisResult result = new TrackAnalysisResult();
        result.setTrack(allTrackOccurrences.get(0).getTrack());
        result.setOccurrences(maxOccurrences.getValue().intValue());
        result.setFirstOccurrence(
                allTrackOccurrences.stream().min(Comparator.comparing(PlaylistItem::getAddedAt)).get().getAddedAt());
        result.setLatestOccurrence(
                allTrackOccurrences.stream().max(Comparator.comparing(PlaylistItem::getAddedAt)).get().getAddedAt());

        return result;
    }

    public List<TrackAnalysisResult> getSortedMultiOccurrenceTracks(List<PlaylistDetail> playlistsToAnalyze)
            throws AnalysisException {
        List<PlaylistItem> itemsAggregated = aggregatePlaylists(playlistsToAnalyze);
        Map<String, Long> trackIdFrequencies = countTrackIdOccurrences(itemsAggregated);
        Map<String, Long> multiOccurrenceTrackFrequencies = trackIdFrequencies.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Set<String> multiOccurrenceTrackIds = multiOccurrenceTrackFrequencies.keySet();
        List<PlaylistItem> multiOccurenceItems = itemsAggregated.stream()
                .filter(item -> multiOccurrenceTrackIds.contains(item.getTrack().getId())).collect(Collectors.toList());
        Map<String, List<PlaylistItem>> itemsGroupedById = multiOccurenceItems.stream()
                .collect(Collectors.groupingBy((PlaylistItem item) -> item.getTrack().getId()));

        List<TrackAnalysisResult> results = new ArrayList<>();
        TrackAnalysisResult result;
        for (List<PlaylistItem> entry : itemsGroupedById.values()) {
            result = new TrackAnalysisResult();
            result.setTrack(entry.get(0).getTrack());
            result.setOccurrences(entry.size());
            result.setFirstOccurrence(
                    entry.stream().min(Comparator.comparing(PlaylistItem::getAddedAt)).get().getAddedAt());
            result.setLatestOccurrence(
                    entry.stream().max(Comparator.comparing(PlaylistItem::getAddedAt)).get().getAddedAt());
            result.setAppearances(entry.stream().map(item -> item.getAddedAt()).collect(Collectors.toList()));

            List<Integer> positions = new ArrayList<>();
            for (PlaylistItem item : entry) {
                PlaylistDetail owningPlaylist = playlistsToAnalyze.stream()
                        .filter(playlistDetail -> item.getBelongsTo().equals(playlistDetail.getBelongsTo()))
                        .collect(Collectors.toList()).get(0);
                positions.add(getTrackPosition(item.getTrack().getId(), owningPlaylist));
            }

            result.setAveragePosition(
                    positions.stream().collect(Collectors.averagingInt(Integer::intValue)).intValue());

            results.add(result);
        }

        results.sort(Comparator.comparing(TrackAnalysisResult::getOccurrences));
        return results;
    }

    private List<PlaylistItem> aggregatePlaylists(List<PlaylistDetail> playlistsToAggregate) {
        return playlistsToAggregate.stream().map(PlaylistDetail::getItems).flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private Map<String, Long> countTrackIdOccurrences(List<PlaylistItem> playlistItems) {
        return playlistItems.stream()
                .collect(Collectors.groupingBy((PlaylistItem item) -> item.getTrack().getId(), Collectors.counting()));
    }

    private int getTrackPosition(String trackId, PlaylistDetail playlistDetail) {
        int position = -1;

        List<PlaylistItem> playlistItems = playlistDetail.getItems();
        for (int i = 0; i < playlistItems.size(); i++) {
            if (trackId.equals(playlistItems.get(i).getTrack().getId())) {
                position = i + 1;
                break;
            }
        }

        return position;
    }
}
