package by.dvorkin.recording.model;

import java.util.concurrent.TimeUnit;

public class Track {
    private final String trackName;
    private final int trackDuration;
    private final String trackGenre;

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
        return trackName + "\n\tDuration: " + TimeUnit.SECONDS.toMinutes(trackDuration) + " min "
                + (TimeUnit.SECONDS.toSeconds(trackDuration)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(trackDuration)))
                + " sec " + "\tGenre: " + trackGenre;
    }

}
