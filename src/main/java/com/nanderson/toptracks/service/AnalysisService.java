package com.nanderson.toptracks.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.nanderson.toptracks.domain.Album;
import com.nanderson.toptracks.domain.AlbumAnalysisResult;
import com.nanderson.toptracks.domain.AnalysisResult;
import com.nanderson.toptracks.domain.Artist;
import com.nanderson.toptracks.domain.ArtistAnalysisResult;
import com.nanderson.toptracks.domain.PlaylistDetail;
import com.nanderson.toptracks.domain.PlaylistItem;
import com.nanderson.toptracks.domain.Track;
import com.nanderson.toptracks.domain.TrackAnalysisResult;
import com.nanderson.toptracks.exception.AnalysisException;

import org.springframework.stereotype.Service;

@Service
public class AnalysisService {

    public AnalysisService() {
        super();
    }

    public TrackAnalysisResult getMostPopularTrack(List<PlaylistDetail> playlistsToAnalyze) throws AnalysisException {
        List<PlaylistItem> itemsAggregated = aggregatePlaylists(playlistsToAnalyze);
        Function<PlaylistItem, String> trackIdMapper = (PlaylistItem item) -> item.getTrack().getId();
        Map<String, Long> trackIdFrequencies = countIdOccurrences(itemsAggregated, trackIdMapper);

        Entry<String, Long> maxOccurrences = trackIdFrequencies.entrySet().stream().max(Entry.comparingByValue())
                .orElseThrow(() -> new AnalysisException("Unable to find a single most popular track."));
        String trackId = maxOccurrences.getKey();

        List<PlaylistItem> allTrackOccurrences = itemsAggregated.stream()
                .filter(item -> trackId.equals(item.getTrack().getId())).collect(Collectors.toList());

        TrackAnalysisResult result = new TrackAnalysisResult();
        result.setAnalysisItem(allTrackOccurrences.get(0).getTrack());
        result.setOccurrences(maxOccurrences.getValue().intValue());
        result.setFirstAppearance(
                allTrackOccurrences.stream().min(Comparator.comparing(PlaylistItem::getAddedAt)).get().getAddedAt());
        result.setLatestAppearance(
                allTrackOccurrences.stream().max(Comparator.comparing(PlaylistItem::getAddedAt)).get().getAddedAt());

        return result;
    }

    @SuppressWarnings(value = "unchecked")
    public List<TrackAnalysisResult> getSortedMultiOccurrenceTracks(List<PlaylistDetail> playlistsToAnalyze)
            throws AnalysisException {
        Function<PlaylistItem, String> trackIdMapper = (PlaylistItem item) -> item.getTrack().getName();
        Function<List<PlaylistItem>, Track> trackMapper = (List<PlaylistItem> items) -> items.get(0).getTrack();
        Supplier<TrackAnalysisResult> trackAnalysisResultSupplier = () -> new TrackAnalysisResult();

        return (List<TrackAnalysisResult>) getSortedMultiOccurrences(playlistsToAnalyze, trackIdMapper, trackMapper,
                trackAnalysisResultSupplier);
    }

    @SuppressWarnings(value = "unchecked")
    public List<ArtistAnalysisResult> getSortedMultiOccurrenceArtists(List<PlaylistDetail> playlistsToAnalyze)
            throws AnalysisException {
        Function<PlaylistItem, String> artistIdMapper = (PlaylistItem item) -> item.getTrack().getArtists().get(0)
                .getName();
        Function<List<PlaylistItem>, Artist> artistMapper = (List<PlaylistItem> items) -> items.get(0).getTrack()
                .getArtists().get(0);
        Supplier<ArtistAnalysisResult> artistAnalysisResultSupplier = () -> new ArtistAnalysisResult();

        return (List<ArtistAnalysisResult>) getSortedMultiOccurrences(playlistsToAnalyze, artistIdMapper, artistMapper,
                artistAnalysisResultSupplier);
    }

    @SuppressWarnings(value = "unchecked")
    public List<AlbumAnalysisResult> getSortedMultiOccurrenceAlbums(List<PlaylistDetail> playlistsToAnalyze)
            throws AnalysisException {
        Function<PlaylistItem, String> albumIdMapper = (PlaylistItem item) -> item.getTrack().getAlbum().getName();
        Function<List<PlaylistItem>, Album> albumMapper = (List<PlaylistItem> items) -> items.get(0).getTrack()
                .getAlbum();
        Supplier<AlbumAnalysisResult> albumAnalysisResultSupplier = () -> new AlbumAnalysisResult();

        return (List<AlbumAnalysisResult>) getSortedMultiOccurrences(playlistsToAnalyze, albumIdMapper, albumMapper,
                albumAnalysisResultSupplier);
    }

    private <T> List<? extends AnalysisResult<T>> getSortedMultiOccurrences(List<PlaylistDetail> playlistsToAnalyze,
            Function<PlaylistItem, String> idMapper, Function<List<PlaylistItem>, T> itemMapper,
            Supplier<? extends AnalysisResult<T>> classSupplier) throws AnalysisException {

        List<PlaylistItem> itemsAggregated = aggregatePlaylists(playlistsToAnalyze);
        Map<String, Long> idFrequencies = countIdOccurrences(itemsAggregated, idMapper);
        Map<String, Long> multiOccurrenceFrequencies = idFrequencies.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Set<String> multiOccurrenceIds = multiOccurrenceFrequencies.keySet();
        List<PlaylistItem> multiOccurenceItems = itemsAggregated.stream()
                .filter(item -> multiOccurrenceIds.contains(idMapper.apply(item))).collect(Collectors.toList());
        Map<String, List<PlaylistItem>> itemsGroupedById = multiOccurenceItems.stream()
                .collect(Collectors.groupingBy(idMapper));

        List<AnalysisResult<T>> results = new ArrayList<>();
        AnalysisResult<T> result;
        for (List<PlaylistItem> entry : itemsGroupedById.values()) {
            result = classSupplier.get();
            result.setAnalysisItem(itemMapper.apply(entry));
            result.setOccurrences(entry.size());
            result.setFirstAppearance(
                    entry.stream().min(Comparator.comparing(PlaylistItem::getAddedAt)).get().getAddedAt());
            result.setLatestAppearance(
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

        results.sort(Comparator.comparing(AnalysisResult<T>::getOccurrences).reversed()
                .thenComparing(AnalysisResult<T>::getAveragePosition));
        return results;
    }

    private List<PlaylistItem> aggregatePlaylists(List<PlaylistDetail> playlistsToAggregate) {
        return playlistsToAggregate.stream().map(PlaylistDetail::getItems).flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private Map<String, Long> countIdOccurrences(List<PlaylistItem> playlistItems,
            Function<PlaylistItem, String> idMapper) {
        return playlistItems.stream().collect(Collectors.groupingBy(idMapper, Collectors.counting()));
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
