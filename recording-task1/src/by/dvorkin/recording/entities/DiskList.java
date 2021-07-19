package by.dvorkin.recording.entities;

import java.util.ArrayList;
import java.util.List;

public class DiskList {
    private static DiskList instance;
    private Disk currentDisk;
    private List<Disk> diskList;

    private DiskList() {
        this.diskList = new ArrayList<>();
    }

    public static DiskList getInstance() {
        if (instance == null) {
            instance = new DiskList();
        }
        return instance;
    }

    public Disk getCurrentDisk() {
        return currentDisk;
    }

    public void setCurrentDisk(Disk currentDisk) {
        this.currentDisk = currentDisk;
    }

    public List<Disk> getDiskList() {
        return this.diskList;
    }

    public void addToDiskList(Disk disk) {
        diskList.add(disk);
    }

}
