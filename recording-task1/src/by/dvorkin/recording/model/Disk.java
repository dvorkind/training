package by.dvorkin.recording.model;

import java.util.ArrayList;
import java.util.Comparator;

public class Disk {
    private String name;
    private ArrayList<Track> tracklist;

    @Override
    public String toString() {
        return "DISK NAME [" + name + "], NUMBER OF SONGS [" + tracklist.size() + "], TOTAL DURATION ["
                + DiskUtils.getTotalDuration(tracklist) + "]";
    }

    public Disk() {
        this.tracklist = new ArrayList<>();
    }

    public ArrayList<Track> getTracklist() {
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

    public ArrayList<Track> sortByGenre() {
        ArrayList<Track> sortedDisk = tracklist;
        sortedDisk.sort(Comparator.comparing(Track::getTrackGenre).thenComparing(Track::getTrackDuration));
        return sortedDisk;
    }

    public ArrayList<Track> sortByName() {
        ArrayList<Track> sortedDisk = tracklist;
        sortedDisk.sort(Comparator.comparing(Track::getTrackName).thenComparing(Track::getTrackGenre));
        return sortedDisk;
    }

    public ArrayList<Track> sortByDuration() {
        ArrayList<Track> sortedDisk = tracklist;
        sortedDisk.sort(Comparator.comparing(Track::getTrackDuration).thenComparing(Track::getTrackName));
        return sortedDisk;
    }
}
