package by.dvorkin.recording.model;

public class Composition {
    private String trackName;
    private int trackDuration;
    private int trackGenre;

    public Composition(String trackName, int trackDuration, int trackGenre) {
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

    public int getTrackGenre() {
        return trackGenre;
    }

    @Override
    public String toString() {
        return trackName + "\n\tDuration: " + trackDuration / 60 + " min " + trackDuration % 60
                + " sec " + "\tGenre: " + Styles.values()[trackGenre].toString();
    }

}
