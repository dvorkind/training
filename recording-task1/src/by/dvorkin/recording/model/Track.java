package by.dvorkin.recording.model;

import java.util.concurrent.TimeUnit;

public class Track {
    private final String singer;
    private final String title;
    private final int duration;
    private final String genre;

    public Track(String singer, String title, int duration, String genre) {
        this.singer = singer;
        this.title = title;
        this.duration = duration;
        this.genre = genre;
    }

    public String getSinger() {
        return singer;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return singer + " - " + title + "\n\tDuration: " + TimeUnit.SECONDS.toMinutes(duration) + " min "
                + (TimeUnit.SECONDS.toSeconds(duration)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(duration)))
                + " sec " + "\tGenre: " + genre;
    }

}
