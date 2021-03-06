package by.dvorkin.recording.menu;

import by.dvorkin.recording.entities.Disk;
import by.dvorkin.recording.interfaces.DiskList;

import java.util.List;
import java.util.Scanner;

public abstract class AbstractMenu {
    private DiskList diskList;
    public Scanner scanner;

    public AbstractMenu(DiskList diskList, Scanner scanner) {
        this.diskList = diskList;
        this.scanner = scanner;
    }

    public Disk getCurrentDisk() {
        return diskList.getCurrentDisk();
    }

    public void setCurrentDisk(Disk currentDisk) {
        diskList.setCurrentDisk(currentDisk);
    }

    public List<Disk> getDiskLibrary() {
        return diskList.getDiskLibrary();
    }

    public void addToDiskLibrary(Disk disk) {
        diskList.addToDiskLibrary(disk);
    }

    public void printAllExistingDisk() {
        System.out.println("\nTOTAL OPEN (CREATED) DISKS: " + getDiskLibrary()
                .size());
        for (int i = 1; i <= getDiskLibrary()
                .size(); i++) {
            System.out.println(i + ". " + getDiskLibrary()
                    .get(i - 1)
                    .toString());
        }
    }

    public Boolean isTracklistNotEmpty() {
        return diskList.isTracklistNotEmpty();
    }

    public void deleteCurrentDisk() {
        diskList.deleteCurrentDisk();
    }

    abstract public void printMenu();
}
