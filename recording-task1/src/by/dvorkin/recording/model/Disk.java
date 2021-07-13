package by.dvorkin.recording.model;

import java.util.ArrayList;
import java.util.Comparator;

public class Disk {
    private String name;
    private ArrayList<Track> disk;

    @Override
    public String toString() {
        return "Disk [name=" + name + "]";
    }

    public Disk() {
        this.disk = new ArrayList<Track>();
    }

    public ArrayList<Track> getDisk() {
        return disk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean diskIsEmpty() {
        return disk.isEmpty();
    }

    public void sortByGenre() {
        disk.sort(Comparator.comparing(Track::getTrackGenre).reversed().thenComparing(Track::getTrackDuration)
                .reversed());
    }

    public void sortByName() {
        disk.sort(Comparator.comparing(Track::getTrackName).reversed().thenComparing(Track::getTrackGenre).reversed());
    }

    public void sortByDuration() {
        disk.sort(Comparator.comparing(Track::getTrackDuration).thenComparing(Track::getTrackName).reversed());
    }
}
