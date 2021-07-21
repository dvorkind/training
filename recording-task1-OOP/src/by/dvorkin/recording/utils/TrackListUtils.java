package by.dvorkin.recording.utils;

import by.dvorkin.recording.constants.Constants;
import by.dvorkin.recording.entities.Disk;
import by.dvorkin.recording.entities.Track;
import by.dvorkin.recording.entities.TrackList;
import by.dvorkin.recording.enums.Genres;
import by.dvorkin.recording.enums.SortBy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TrackListUtils {

    public static TrackList sortSongsBy(TrackList tracklist, SortBy sortby) {
        TrackList sortedTracklist = tracklist;
        switch (sortby) {
            case GENRE:
                sortedTracklist.getTracks()
                        .sort(Comparator.comparing(Track::getGenre)
                                .thenComparing(Track::getDuration));
                break;
            case NAME:
                sortedTracklist.getTracks()
                        .sort(Comparator.comparing(Track::getSinger)
                                .thenComparing(Track::getTitle));
                break;
            case DURATION:
                sortedTracklist.getTracks()
                        .sort(Comparator.comparing(Track::getDuration)
                                .thenComparing(Track::getSinger));
                break;
        }
        return sortedTracklist;
    }

    public static void printTracklist(TrackList tracklist) {
        StringBuilder sb = new StringBuilder();
        sb.append(tracklist.toString())
                .append("TOTAL DURATION: ")
                .append(getTotalDuration(tracklist.getTracks()));
        System.out.println(sb);
    }

    public static TrackList findTrackByRange(Disk disk, int from, int to) {
        TrackList foundTracklist = new TrackList();
        for (int i = 0; i < disk.getTracklist()
                .getTracks()
                .size(); i++) {
            Track track = disk.getTracklist()
                    .getTracks()
                    .get(i);
            if (track.getDuration() >= from && track.getDuration() <= to) {
                foundTracklist.getTracks()
                        .add(track);
            }
        }
        return foundTracklist;
    }

    public static void saveFile(List<Track> tracklist, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (Track track : tracklist) {
                writer.write(track.getSinger() + "," + track.getTitle() + "," + track.getDuration() + ","
                        + track.getGenre() + "\n");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void generateTracksByCount(List<Track> tracklist, int count) {
        int countGenres = Genres.values().length;
        for (int i = 1; i <= count; i++) {
            tracklist.add(new Track("Singer " + i, "Some song " + (int) (Math.random() * 10),
                    (int) (Constants.MIN_SONG_DURATION + Math.random() * Constants.MIN_SONG_DURATION),
                    Genres.values()[(int) (Math.random() * countGenres)].toString()));
        }
    }

    public static void generateTracksByDiskDuration(List<Track> tracklist, int duration) {
        int currentDuration = 0;
        int countGenres = Genres.values().length;
        int tempDuration;
        while (currentDuration <= duration && (duration - currentDuration) > Constants.MIN_SONG_DURATION) {
            tempDuration = (int) (Constants.MIN_SONG_DURATION + Math.random() * Constants.MIN_SONG_DURATION);
            if ((currentDuration + tempDuration) <= duration) {
                tracklist.add(
                        new Track("Singer " + (int) (Math.random() * 10), "Some song " + (int) (Math.random() * 10),
                                tempDuration, Genres.values()[(int) (Math.random() * countGenres)].toString()));
                currentDuration += tempDuration;
            }
        }
    }

    public static String getTotalDuration(List<Track> tracklist) {
        int total = 0;
        for (Track track : tracklist) {
            total += track.getDuration();
        }
        long hours = TimeUnit.SECONDS.toHours(total);
        total -= TimeUnit.HOURS.toSeconds(hours);
        long minutes = TimeUnit.SECONDS.toMinutes(total);
        total -= TimeUnit.MINUTES.toSeconds(minutes);
        long seconds = TimeUnit.SECONDS.toSeconds(total);
        if (hours > 0) {
            return hours + " hours " + minutes + " min " + seconds + " sec";
        } else {
            return minutes + " min " + seconds + " sec";
        }
    }
}