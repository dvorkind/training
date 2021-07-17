package by.dvorkin.recording.console.menu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import by.dvorkin.recording.console.Runner;
import by.dvorkin.recording.model.Disk;
import by.dvorkin.recording.model.DiskUtils;
import by.dvorkin.recording.model.SortBy;
import by.dvorkin.recording.model.Track;

public class MenuUtils {
    public static void printTracklist(List<Track> tracklist) {
        int num = 0;
        for (Track track : tracklist) {
            num++;
            System.out.println(num + ". " + track.toString());
        }
        System.out.println("TOTAL DURATION: " + DiskUtils.getTotalDuration(tracklist));
    }

    public static List<Track> sortSongsBy(Disk disk, SortBy sortby) {
        List<Track> sortedTracklist = new ArrayList<Track>(disk.getTracklist());
        if (!disk.isTracklistEmpty() && disk.getTracklist() != null) {
            switch (sortby) {
            case GENRE:
                sortedTracklist.sort(Comparator.comparing(Track::getTrackGenre).thenComparing(Track::getTrackDuration));
                break;
            case NAME:
                sortedTracklist.sort(Comparator.comparing(Track::getTrackName).thenComparing(Track::getTrackGenre));
                break;
            case DURATION:
                sortedTracklist.sort(Comparator.comparing(Track::getTrackDuration).thenComparing(Track::getTrackName));
                break;
            }
        } else {
            return null;
        }
        return sortedTracklist;
    }

    public static void printAllExistingDisk() {
        int num = 0;
        System.out.println("\nTOTAL OPEN (CREATED) DISKS: " + Runner.getDiskList().size());
        for (Disk disk : Runner.getDiskList()) {
            num++;
            System.out.println(num + ". " + disk.toString());
        }
    }

    public static void createNewDisk() {
        Disk disk = new Disk();
        Runner.setCurrentDisk(disk);
        Runner.addToDiskList(disk);
    }

    public static Boolean isAnyDisk() {
        if (Runner.getCurrentDisk() == null) {
            System.out.print("\n\tFirst you need to generate or open any disk! \n");
            return false;
        } else {
            return true;
        }
    }

    public static void findTrackByRange(int from, int to) {
        List<Track> foundTracklist = new ArrayList<>();
        for (int i = 0; i < Runner.getCurrentDisk().getTracklist().size(); i++) {
            Track track = Runner.getCurrentDisk().getTracklist().get(i);
            if (track.getTrackDuration() >= from && track.getTrackDuration() <= to) {
                foundTracklist.add(track);
            }
        }
        System.out.println("\nLIST OF SONGS MATCHING THE CONDITION: ");
        MenuUtils.printTracklist(foundTracklist);
    }
}
