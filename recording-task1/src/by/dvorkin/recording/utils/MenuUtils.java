package by.dvorkin.recording.utils;

import by.dvorkin.recording.entities.Disk;
import by.dvorkin.recording.entities.DiskList;

public class MenuUtils {

    public static void printAllExistingDisk() {
        System.out.println("\nTOTAL OPEN (CREATED) DISKS: " + DiskList.getInstance().getDiskList().size());
        for (int i = 1; i <= DiskList.getInstance().getDiskList().size(); i++) {
            System.out.println(i + ". " + DiskList.getInstance().getDiskList().get(i - 1).toString());
        }
    }

    public static void createNewDisk() {
        Disk disk = new Disk();
        DiskList.getInstance().setCurrentDisk(disk);
        DiskList.getInstance().addToDiskList(disk);
    }

    public static Boolean isAnyDiskOpened() {
        if (DiskList.getInstance().getCurrentDisk() == null) {
            System.out.print("\n\tFirst you need to generate or open any disk! \n");
            return false;
        } else {
            return true;
        }
    }

    public static Boolean isTracklistNotEmpty() {
        if (DiskList.getInstance().getCurrentDisk().getTracklist().getTracks().isEmpty()
                || DiskList.getInstance().getCurrentDisk().getTracklist().getTracks() == null) {
            System.out.println("\n\tThe current disk contains no songs!");
            return false;
        } else {
            return true;
        }
    }
}
