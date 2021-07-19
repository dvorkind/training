package by.dvorkin.recording.entities;

import java.util.ArrayList;
import java.util.List;

public class TrackList {
    private List<Track> tracks;

    public List<Track> getTracks() {
        return tracks;
    }

    public TrackList() {
        this.tracks = new ArrayList<>();
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= tracks.size(); i++) {
            sb.append(i).append(". ").append(tracks.get(i - 1).toString()).append('\n');
        }
        return sb.toString();
    }
}
