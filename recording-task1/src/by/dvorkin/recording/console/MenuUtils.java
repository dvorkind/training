package by.dvorkin.recording.console;

import java.util.List;

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
        if (!disk.isDiskEmpty()) {
            switch (sortby) {
            case GENRE:
                return disk.sortByGenre();
            case NAME:
                disk.sortByName();
                break;
            case DURATION:
                disk.sortByDuration();
                break;
            }
        } else {
            return null;
        }
        return null;
    }

    public static void printAllExistingDisk() {
        int num = 0;
        System.out.println("\nTOTAL OPEN (CREATED) DISKS: " + Runner.getDiskList().size());
        for (Disk disk : Runner.getDiskList()) {
            num++;
            System.out.println(num + ". " + disk.toString());
        }
    }

    public static void createAndAddNewDiskToList() {
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
}
