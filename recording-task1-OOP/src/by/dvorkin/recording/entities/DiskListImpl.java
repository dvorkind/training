package by.dvorkin.recording.entities;

import by.dvorkin.recording.interfaces.DiskList;

import java.util.ArrayList;
import java.util.List;

public class DiskListImpl implements DiskList {
    private static DiskListImpl instance;
    private Disk currentDisk;
    private List<Disk> diskLibrary;

    private DiskListImpl() {
        this.diskLibrary = new ArrayList<>();
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
    public List<Disk> getDiskLibrary() {
        return this.diskLibrary;
    }

    @Override
    public void addToDiskLibrary(Disk disk) {
        diskLibrary.add(disk);
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
        diskLibrary.remove(currentDisk);
        if (diskLibrary.size() > 0) {
            currentDisk = diskLibrary.get(diskLibrary.size() - 1);
        } else {
            currentDisk = null;
        }
    }
}
