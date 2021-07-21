package by.dvorkin.recording.entities;

import by.dvorkin.recording.utils.TrackListUtils;

public class Disk {
    private String name;
    private TrackList tracklist;

    @Override
    public String toString() {
        return "DISK NAME [" + name + "], NUMBER OF SONGS [" + tracklist.getTracks()
                .size() + "], TOTAL DURATION ["
                + TrackListUtils.getTotalDuration(tracklist.getTracks()) + "]";
    }

    public Disk() {
        this.tracklist = new TrackList();
    }

    public TrackList getTracklist() {
        return tracklist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}