package by.dvorkin.recording.entities;

import by.dvorkin.recording.interfaces.DiskList;

import java.util.ArrayList;
import java.util.List;

public class DiskListImpl implements DiskList {
    private static DiskListImpl instance;
    private Disk currentDisk;
    private final List<Disk> diskList;

    private DiskListImpl() {
        this.diskList = new ArrayList<>();
    }

    public static DiskListImpl getInstance() {
        if (instance == null) {
            instance = new DiskListImpl();
        }
        return instance;
    }

    @Override
    public Disk getCurrentDisk() {
        return currentDisk;
    }

    @Override
    public void setCurrentDisk(Disk currentDisk) {
        this.currentDisk = currentDisk;
    }

    @Override
    public List<Disk> getDiskList() {
        return this.diskList;
    }

    @Override
    public void addToDiskList(Disk disk) {
        diskList.add(disk);
    }

    @Override
    public Boolean isTracklistNotEmpty() {
        return !currentDisk
                .getTracklist()
                .getTracks()
                .isEmpty()
                && currentDisk
                .getTracklist()
                .getTracks() != null;
    }

    @Override
    public void deleteCurrentDisk() {
        diskList.remove(currentDisk);
        if (diskList.size() > 0) {
            currentDisk = diskList.get(diskList.size() - 1);
        } else {
            currentDisk = null;
        }
    }
}
