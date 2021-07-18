package by.dvorkin.recording.console.menu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import by.dvorkin.recording.model.Disk;
import by.dvorkin.recording.model.DiskList;
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
        List<Track> sortedTracklist = new ArrayList<>(disk.getTracklist());
        switch (sortby) {
        case GENRE:
            sortedTracklist.sort(Comparator.comparing(Track::getGenre).thenComparing(Track::getDuration));
            break;
        case NAME:
            sortedTracklist.sort(Comparator.comparing(Track::getSinger).thenComparing(Track::getTitle));
            break;
        case DURATION:
            sortedTracklist.sort(Comparator.comparing(Track::getDuration).thenComparing(Track::getSinger));
            break;
        }
        return sortedTracklist;
    }

    public static void printAllExistingDisk() {
        int num = 0;
        System.out.println("\nTOTAL OPEN (CREATED) DISKS: " + DiskList.getDiskList().size());
        for (Disk disk : DiskList.getDiskList()) {
            num++;
            System.out.println(num + ". " + disk.toString());
        }
    }

    public static void createNewDisk() {
        Disk disk = new Disk();
        DiskList.setCurrentDisk(disk);
        DiskList.addToDiskList(disk);
    }

    public static Boolean isAnyDisk() {
        if (DiskList.getCurrentDisk() == null) {
            System.out.print("\n\tFirst you need to generate or open any disk! \n");
            return false;
        } else {
            return true;
        }
    }

    public static Boolean isTracklistNotEmpty() {
        if (DiskList.getCurrentDisk().getTracklist().isEmpty() || DiskList.getCurrentDisk().getTracklist() == null) {
            System.out.println("\n\tThe current disk contains no songs!");
            return false;
        } else {
            return true;
        }
    }

    public static void findTrackByRange(int from, int to) {
        List<Track> foundTracklist = new ArrayList<>();
        for (int i = 0; i < DiskList.getCurrentDisk().getTracklist().size(); i++) {
            Track track = DiskList.getCurrentDisk().getTracklist().get(i);
            if (track.getDuration() >= from && track.getDuration() <= to) {
                foundTracklist.add(track);
            }
        }
        System.out.println("\nLIST OF SONGS MATCHING THE CONDITION: ");
        MenuUtils.printTracklist(foundTracklist);
    }
}
