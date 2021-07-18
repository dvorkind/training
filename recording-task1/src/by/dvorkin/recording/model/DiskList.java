package by.dvorkin.recording.model;

import java.util.ArrayList;
import java.util.List;

public class DiskList {
    private static Disk currentDisk;
    private static final List<Disk> diskList = new ArrayList<>();

    public static Disk getCurrentDisk() {
        return currentDisk;
    }

    public static void setCurrentDisk(Disk currentDisk) {
        DiskList.currentDisk = currentDisk;
    }

    public static List<Disk> getDiskList() {
        return diskList;
    }

    public static void addToDiskList(Disk disk) {
        diskList.add(disk);
    }

}
