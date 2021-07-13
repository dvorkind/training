package by.dvorkin.recording.model;

public class Track {
    private String trackName;
    private int trackDuration;
    private String trackGenre;

    public Track(String trackName, int trackDuration, String trackGenre) {
        this.trackName = trackName;
        this.trackDuration = trackDuration;
        this.trackGenre = trackGenre;
    }

    public String getTrackName() {
        return trackName;
    }

    public int getTrackDuration() {
        return trackDuration;
    }

    public String getTrackGenre() {
        return trackGenre;
    }

    @Override
    public String toString() {
        return trackName + "\n\tDuration: " + trackDuration / 60 + " min " + trackDuration % 60 + " sec " + "\tGenre: "
                + trackGenre;
    }

}
