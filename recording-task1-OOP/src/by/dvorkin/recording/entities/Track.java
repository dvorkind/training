package by.dvorkin.recording.entities;

import java.util.concurrent.TimeUnit;

public class Track {
    private String singer;
    private String title;
    private int duration;
    private String genre;

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
        sb.append(singer)
                .append(" - ")
                .append(title)
                .append("\n\tDuration: ")
                .append(TimeUnit.SECONDS.toMinutes(duration))
                .append(" min ")
                .append(TimeUnit.SECONDS.toSeconds(duration)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(duration)))
                .append(" sec ")
                .append("\tGenre: ")
                .append(genre);
        return sb.toString();
    }

}
