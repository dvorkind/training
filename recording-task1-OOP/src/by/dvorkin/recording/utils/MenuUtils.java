package by.dvorkin.recording.utils;


import by.dvorkin.recording.entities.Disk;
import by.dvorkin.recording.entities.DiskList;

public class MenuUtils {

    public static void printAllExistingDisk() {
        System.out.println("\nTOTAL OPEN (CREATED) DISKS: " + DiskList.getInstance()
                .getDiskList()
                .size());
        for (int i = 1; i <= DiskList.getInstance()
                .getDiskList()
                .size(); i++) {
            System.out.println(i + ". " + DiskList.getInstance()
                    .getDiskList()
                    .get(i - 1)
                    .toString());
        }
    }

    public static Disk createNewDisk() {
        Disk disk = new Disk();
        DiskList.getInstance()
                .addToDiskList(disk);
        return disk;
    }

    public static Boolean isAnyDiskOpened() {
        return DiskList.getInstance()
                .getCurrentDisk() != null;
    }

    public static Boolean isTracklistNotEmpty() {
        return !DiskList.getInstance()
                .getCurrentDisk()
                .getTracklist()
                .getTracks()
                .isEmpty()
                && DiskList.getInstance()
                .getCurrentDisk()
                .getTracklist()
                .getTracks() != null;
    }
}
