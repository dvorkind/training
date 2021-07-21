package by.dvorkin.recording.menu;

import by.dvorkin.recording.entities.Disk;
import by.dvorkin.recording.entities.DiskList;
import by.dvorkin.recording.main.Runner;

import java.util.List;
import java.util.Scanner;

public abstract class AbstractMenu {
    public Scanner getMenuScanner() {
        return Runner.getMenuScanner();
    }

    public Disk getCurrentDisk() {
        return DiskList.getInstance()
                .getCurrentDisk();
    }

    public void setCurrentDisk(Disk currentDisk) {
        DiskList.getInstance()
                .setCurrentDisk(currentDisk);
    }

    public List<Disk> getDiskList() {
        return DiskList.getInstance()
                .getDiskList();
    }

    abstract public void printMenu();
}
