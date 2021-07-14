package by.dvorkin.recording.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Disk {
    private String name;
    private List<Track> tracklist;

    @Override
    public String toString() {
        return "DISK NAME [" + name + "], NUMBER OF SONGS [" + tracklist.size() + "], TOTAL DURATION ["
                + DiskUtils.getTotalDuration(tracklist) + "]";
    }

    public Disk() {
        this.tracklist = new ArrayList<>();
    }

    public List<Track> getTracklist() {
        return tracklist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isDiskEmpty() {
        return tracklist.isEmpty();
    }

    public List<Track> sortByGenre() {
        List<Track> sortedDisk = tracklist;
        sortedDisk.sort(Comparator.comparing(Track::getTrackGenre).thenComparing(Track::getTrackDuration));
        return sortedDisk;
    }

    public List<Track> sortByName() {
        List<Track> sortedDisk = tracklist;
        sortedDisk.sort(Comparator.comparing(Track::getTrackName).thenComparing(Track::getTrackGenre));
        return sortedDisk;
    }

    public List<Track> sortByDuration() {
        List<Track> sortedDisk = tracklist;
        sortedDisk.sort(Comparator.comparing(Track::getTrackDuration).thenComparing(Track::getTrackName));
        return sortedDisk;
    }
}
