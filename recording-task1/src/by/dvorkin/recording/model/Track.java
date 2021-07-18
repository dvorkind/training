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
        StringBuilder sb = new StringBuilder();
        sb.append(singer).append(" - ").append(title).append("\n\tDuration: ")
                .append(TimeUnit.SECONDS.toMinutes(duration)).append(" min ")
                .append(TimeUnit.SECONDS.toSeconds(duration)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(duration)))
                .append(" sec ").append("\tGenre: ").append(genre);
        return sb.toString();
    }

}
