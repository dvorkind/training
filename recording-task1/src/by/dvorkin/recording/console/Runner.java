package by.dvorkin.recording.console;

import java.util.ArrayList;
import java.util.List;

import by.dvorkin.recording.console.menu.MainMenu;
import by.dvorkin.recording.model.Disk;

public class Runner {

    private static Disk currentDisk;
    private static List<Disk> diskList = new ArrayList<>();

    public static Disk getCurrentDisk() {
        return currentDisk;
    }

    public static void setCurrentDisk(Disk currentDisk) {
        Runner.currentDisk = currentDisk;
    }

    public static List<Disk> getDiskList() {
        return diskList;
    }

    public static void addToDiskList(Disk disk) {
        diskList.add(disk);
    }

    public static void main(String[] args) {
        MainMenu.printMenu();
        MainMenu.userInput();
    }
}
