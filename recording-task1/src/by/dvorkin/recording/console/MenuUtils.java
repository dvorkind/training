package by.dvorkin.recording.console;

import by.dvorkin.recording.model.Disk;
import by.dvorkin.recording.model.DiskUtils;
import by.dvorkin.recording.model.Track;

public class MenuUtils {
    public static void printTracklist(Disk disk) {
        int num = 0;
        for (Track track : disk.getDisk()) {
            num++;
            System.out.println(num + ". " + track.toString());
        }
        System.out.println("TOTAL DURATION: " + DiskUtils.getTotalDuration(disk));
    }

    public static void sortSongsBy(Disk disk, SortBy sortby) {
        if (!disk.diskIsEmpty()) {
            switch (sortby) {
            case GENRE:
                disk.sortByGenre();
                break;
            case NAME:
                disk.sortByName();
                break;
            case DURATION:
                disk.sortByDuration();
                break;
            }
        } else {
            System.out.print("\n\tFirst you need to generate or open the disk! \n");
        }
    }

    public static void printAllExistingDisk() {
        int num = 0;
        for (Disk disk : Runner.getDiskList()) {
            num++;
            System.out.println(num + ". " + disk.toString());
        }
    }

    public static void createAndaddNewDiskToList() {
        Disk disk = new Disk();
        Runner.setCurrentDisk(disk);
        Runner.addToDiskList(disk);
    }
}
